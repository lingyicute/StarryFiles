#!/usr/bin/env python3
"""Subset Nebulove for StarryFiles web/index.html
用法：
  pip install fonttools brotli
  python scripts/subset_font.py  # 增量，命中缓存则跳过
  python scripts/subset_font.py --force   # 强制重新下载并生成
  NEBULOVE_FONT=/path/to/Nebulove.ttf python scripts/subset_font.py  # 离线

输出：
  web/fonts/nebulove-subset.woff2 
"""
import hashlib
from html.parser import HTMLParser
import io
import json
import os
from pathlib import Path
import re
import sys
import urllib.request

ROOT = Path(__file__).resolve().parents[1]
HTML_PATH = ROOT / "web/index.html"
FONT_URL = "https://raw.githubusercontent.com/lingyicute/Nebulove/main/Nebulove.ttf"
CACHE_PATH = ROOT / "scripts/.font-subset-cache.json"

OUTPUT_NEBULOVE = ROOT / "web/fonts/nebulove-subset.woff2"
# 仅 Nebulove；family 需与 HTML 中 @font-face 的 font-family 完全一致
OUTPUTS = {
    "Nebulove": OUTPUT_NEBULOVE,
}


class VisibleText(HTMLParser):
    def __init__(self):
        super().__init__(convert_charrefs=True)
        self.ignored = 0
        self.chars = set()

    def handle_starttag(self, tag, attrs):
        if tag in {"head", "script", "style"}:
            self.ignored += 1
        if not self.ignored:
            for name, value in attrs:
                if name in {"alt", "title", "aria-label", "placeholder"} and value:
                    self.chars.update(value)

    def handle_endtag(self, tag):
        if tag in {"head", "script", "style"}:
            self.ignored = max(0, self.ignored - 1)

    def handle_data(self, text):
        if not self.ignored:
            self.chars.update(text)


def collect_chars():
    parser = VisibleText()
    html_text = HTML_PATH.read_text(encoding="utf-8")
    parser.feed(html_text)
    chars = parser.chars

    # 内联 <style>：去除注释后提取 content:"..."（避免把代码注释/SVG路径当可见字符）
    styles = re.findall(r"<style[^>]*>(.*?)</style>", html_text, flags=re.S | re.I)
    css_combined = "\n".join(styles)
    # 若未来抽离为外部 CSS，也兼容 web/style.css
    extra_css = ROOT / "web/style.css"
    if extra_css.exists():
        css_combined += "\n" + extra_css.read_text(encoding="utf-8")
    css = re.sub(r"/\*.*?\*/", "", css_combined, flags=re.S)
    for content in re.findall(r'content:\s*[\'"]([^\'"]*)[\'"]', css):
        chars.update(content)

    chars.update(chr(c) for c in range(32, 127))  # 覆盖动态数字/英文、访问量等
    chars.update("：，。！？；“”‘’（）【】—…·《》×＝÷＋－\u200d\ufe0e\ufe0f")
    return chars, html_text, css_combined


def digest(path: Path):
    return hashlib.sha256(path.read_bytes()).hexdigest()


def subset(font, chars):
    from fontTools.subset import Options, Subsetter

    subsetter = Subsetter(options=Options())
    subsetter.populate(unicodes=sorted(ord(c) for c in chars))
    subsetter.subset(font)
    font.flavor = "woff2"
    font.recalcTimestamp = False
    output = io.BytesIO()
    font.save(output)
    return output.getvalue()


def _replace_font_face(html: str, family: str, filename: str, relative_prefix: str = "fonts"):
    """替换 HTML 内联 @font-face 中对应 family 的 src。
    relative_prefix 相对于 web/index.html 的字体目录，StarryFiles 为 'fonts'。
    """
    pattern = re.compile(r"@font-face\s*\{[^}]*\}", re.S)

    def _repl(match):
        block = match.group(0)
        if not re.search(r'font-family\s*:\s*["\']?' + re.escape(family) + r'["\']?\s*;', block):
            return block
        # 原 HTML 已有两空格缩进，匹配的块不含前导空白，故新块不额外加缩进
        new_block = f'''@font-face {{
    font-family: '{family}';
    src: url('./{relative_prefix}/{filename}') format('woff2');
    font-weight: normal;
    font-style: normal;
    font-display: swap;
  }}'''
        return new_block

    new_html = pattern.sub(_repl, html)
    if family not in new_html:
        raise ValueError(f"未在 HTML 中找到 @font-face: {family}")
    return new_html


def _ensure_preload(html: str, filename: str, relative_prefix: str = "fonts"):
    href = f"./{relative_prefix}/{filename}"
    if re.search(r'<link[^>]+rel=["\']preload["\'][^>]*' + re.escape(filename), html):
        return html
    if href in html and 'rel="preload"' in html and filename in html:
        return html
    preload = f'  <link rel="preload" href="{href}" as="font" type="font/woff2" crossorigin>'
    if "<style" in html:
        return html.replace("<style", preload + "\n  <style", 1)
    if "</head>" in html:
        return html.replace("</head>", preload + "\n</head>", 1)
    return preload + "\n" + html


def _ensure_fallback(html: str, family: str = "Nebulove"):
    """为减少 CLS，注入 fallback font-face（与 Me 的 b-fallback 一致）。
    size-adjust 76.3% 是基于 Nebulove 与 Arial 的 x-height 比值实测。
    已存在则跳过；同时将 body 回退链补上 fallback，emoji 仍走系统。
    """
    fallback_family = f"{family}-fallback"
    if fallback_family in html:
        return html
    pattern = re.compile(r"(@font-face\s*\{[^}]*font-family\s*:\s*['\"]?" + re.escape(family) + r"['\"]?[^}]*\})", re.S)
    m = pattern.search(html)
    if m:
        fallback_block = f'''
  @font-face {{
    font-family: '{fallback_family}';
    src: local('Arial'), local('Liberation Sans');
    size-adjust: 76.3%;
  }}'''
        html = html[:m.end()] + fallback_block + html[m.end():]
    html = re.sub(
        r"font-family:\s*['\"]" + re.escape(family) + r"['\"]\s*,\s*sans-serif\s*;",
        f"font-family: '{family}', '{fallback_family}', sans-serif;",
        html
    )
    return html


def main():
    from fontTools.ttLib import TTFont

    chars, html_text, css_combined = collect_chars()
    local_font = os.environ.get("NEBULOVE_FONT")
    source_key = digest(Path(local_font)) if local_font else FONT_URL
    # 指纹：上游字体 + 脚本自身 + 字符集（不含 emoji，emoji 走系统）
    hasher = hashlib.sha256()
    hasher.update(source_key.encode())
    hasher.update(digest(Path(__file__)).encode())
    hasher.update(json.dumps(sorted(ord(c) for c in chars)).encode())
    fingerprint = hasher.hexdigest()

    if "--force" not in sys.argv and CACHE_PATH.exists():
        try:
            cached = json.loads(CACHE_PATH.read_text(encoding="utf-8"))
            if cached.get("fingerprint") == fingerprint:
                outputs_ok = True
                for path in OUTPUTS.values():
                    if not path.exists():
                        outputs_ok = False
                        break
                    if path.name not in html_text:
                        outputs_ok = False
                        break
                    cached_digest = cached.get("outputs", {}).get(path.name)
                    if cached_digest and cached_digest != digest(path):
                        outputs_ok = False
                        break
                if outputs_ok:
                    print("可见字符集未变化；WOFF2 子集已是最新，跳过生成。")
                    print(f"  字符数: {len(chars)}  指纹: {fingerprint[:12]}...")
                    return
        except Exception as e:
            print(f"缓存读取失败，将重新生成: {e}")

    if local_font:
        print(f"使用本地字体: {local_font}")
        nebulove = TTFont(local_font)
    else:
        print("下载 Nebulove 源字体 (TTF)...")
        print(f"  -> {FONT_URL}")
        with urllib.request.urlopen(FONT_URL, timeout=90) as resp:
            nebulove = TTFont(io.BytesIO(resp.read()))

    # 仅子集 Nebulove，emoji 不处理，交由系统字体
    fonts_bytes = {}
    fonts_bytes["Nebulove"] = subset(nebulove, chars)
    print(f"Nebulove 子集字符数: {len(chars)}")

    new_html = html_text
    for family, path in OUTPUTS.items():
        new_html = _replace_font_face(new_html, family, path.name, relative_prefix="fonts")
        new_html = _ensure_preload(new_html, path.name, relative_prefix="fonts")

    new_html = _ensure_fallback(new_html, "Nebulove")

    for family, path in OUTPUTS.items():
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_bytes(fonts_bytes[family])
        print(f"{path.relative_to(ROOT)}: {len(fonts_bytes[family]):,} bytes  (源 1.3 MB -> 节省 ~{100 - len(fonts_bytes[family]) * 100 // 1275924}%)")

    if new_html != html_text:
        HTML_PATH.write_text(new_html, encoding="utf-8")
        print(f"已更新 {HTML_PATH.relative_to(ROOT)} 中的 @font-face 指向本地子集")
    else:
        print("HTML 无需更新")

    CACHE_PATH.parent.mkdir(parents=True, exist_ok=True)
    CACHE_PATH.write_text(json.dumps({
        "fingerprint": fingerprint,
        "source": FONT_URL,
        "html": str(HTML_PATH.relative_to(ROOT)),
        "charset_count": len(chars),
        "charset_preview": "".join(sorted(chars))[:200],
        "outputs": {p.name: digest(p) for p in OUTPUTS.values()},
    }, indent=2, ensure_ascii=False) + "\n", encoding="utf-8")
    print("已更新缓存指纹；未将字体数据内联于阻塞渲染的 CSS 中，可被浏览器长期缓存。")


if __name__ == "__main__":
    main()
