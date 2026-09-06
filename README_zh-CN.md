###  ‎

###  ‎

<p align="center">
  <img src="./app/src/main/res/mipmap-xxxhdpi/ic_launcher.png" alt="StarryFiles Logo" width="96" height="96" onerror="this.style.display='none'"/>
</p>
<h1 align="center">StarryFiles</h1>
<h3 align="center">Just another file manager, simple yet powerful.</h3>‎‎

<p align="center">一款 Material Design 文件管理器。轻量、干净、安全，由 Linux libc 原生调用驱动，懂你心意。 </p>
<p align="center">Made with ❤️ by <a href="https://github.com/lingyicute">lingyicute</a>.</p>

###  ‎

<p align="center">
  <a href="README.md">🇺🇸 English</a> • [🇨🇳 中文] • 
  <a href="https://sf.92li.uk/">🌐 官方网站</a> •
  <a href="https://github.com/lingyicute/StarryFiles/releases">📦 下载 APK</a> •
  <a href="https://github.com/lingyicute/StarryFiles/issues">🐛 报告问题</a>
</p>

<p align="center">
  <a href="https://github.com/lingyicute/StarryFiles/releases"><img src="https://img.shields.io/github/v/release/lingyicute/StarryFiles?color=blue&label=Release" alt="Latest Release"></a>
  <a href="https://developer.android.com/about/versions/lollipop"><img src="https://img.shields.io/badge/Android-5.0%2B%20(API%2021%2B)-success" alt="Android Version"></a>
  <a href="LICENSE"><img src="https://img.shields.io/badge/License-GPL--3.0-orange.svg" alt="License: GPL-3.0"></a>
  <a href="https://sf.92li.uk/"><img src="https://img.shields.io/badge/Ads%20%26%20Trackers-Zero-brightgreen" alt="No Ads No Tracking"></a>
  <a href="https://github.com/lingyicute/StarryFiles"><img src="https://img.shields.io/github/stars/lingyicute/StarryFiles?style=flat&color=yellow" alt="GitHub Stars"></a>
</p>

---

## 📖 项目简介

市面上多数文件管理器常处于两极：要么充斥着繁杂的广告与隐私追踪，要么界面粗糙、底层依赖脆弱的 `ls` 输出解析。

**StarryFiles** 旨在打破这种现状。底层以 **Java NIO2 File API** 为骨架，通过 **JNI 直达 Linux libc 系统调用**；上层严格遵循 **Material Design / Material You** 设计规范，在提供桌面级管理能力（符号链接、权限、SELinux 上下文、NAS 协议支持）的同时，保持界面的克制、纯粹与优雅。

---

## ✨ 核心特性

- **🎨 精致克制的 Material Design**
  - 布局、对齐、留白、图标与排版严谨考究，拒绝妥协与小瑕疵。
  - **Material You 动态取色**：支持根据壁纸种子色实时生成整套应用配色。
  - 支持浅色、深色模式与专为 OLED 屏幕优化的**纯黑（True Black）主题**。

- **🔒 100% 开源与隐私安全**
  - 基于 **GPL-3.0** 协议开源。
  - **零广告、零追踪、零分析 SDK、无多余后台驻留**。
  - Root 权限操作完全透明可审计，给自己的设备最安心的保障。

- **🧭 交互式面包屑导航**
  - 路径层级清晰直观，在复杂的目录树中一键快速跳转与轻松回溯。

- **📦 全功能压缩包管理**
  - 无需第三方工具，直接查看、解压与创建常见的压缩文件格式（ZIP、TAR、GZ、7z 等）。

- **🌐 丰富的网络存储与 NAS 支持**
  - 像浏览本地文件夹一样轻松管理远程服务器与网络共享：
    - **SMB / Samba**（Windows 共享文件）
    - **SFTP**（SSH 安全文件传输）
    - **FTP / FTPS**
    - **WebDAV**（Nextcloud、坚果云等）

- **⚡ Linux 级感知与 Root 权限支持**
  - 具备类似桌面端 Nautilus 的完整 Linux 感知能力。
  - 完美支持符号链接（Symlink）、POSIX 文件权限与归属（chmod/chown）以及 SELinux 上下文。
  - 正确处理非法 UTF-8 编码的特殊文件名，杜绝常见文件管理器出现的乱码与崩溃。

- **🚀 快速、健壮的技术架构**
  - **拒绝解析 `ls`**：通过 C/C++ JNI 直连 Linux syscall，避免系统版本升级带来的解析失效。
  - 基于 Android 现代架构组件（**ViewModel + LiveData**）打造，优雅处理屏幕旋转、文件操作冲突与前后台状态切换。

---

## 📱 应用预览

<p align="center">
  <img src="https://raw.githubusercontent.com/lingyicute/StarryStoreStatics/main/0/repo/org.lyi.android.files/en-US/phoneScreenshots/01.jpg" width="31%" alt="截图 1"/>
  <img src="https://raw.githubusercontent.com/lingyicute/StarryStoreStatics/main/0/repo/org.lyi.android.files/en-US/phoneScreenshots/02.jpg" width="31%" alt="截图 2"/>
  <img src="https://raw.githubusercontent.com/lingyicute/StarryStoreStatics/main/0/repo/org.lyi.android.files/en-US/phoneScreenshots/03.jpg" width="31%" alt="截图 3"/>
</p>

<p align="center">
  <img src="https://raw.githubusercontent.com/lingyicute/StarryStoreStatics/main/0/repo/org.lyi.android.files/en-US/phoneScreenshots/04.jpg" width="31%" alt="截图 4"/>
  <img src="https://raw.githubusercontent.com/lingyicute/StarryStoreStatics/main/0/repo/org.lyi.android.files/en-US/phoneScreenshots/05.jpg" width="31%" alt="截图 5"/>
  <img src="https://raw.githubusercontent.com/lingyicute/StarryStoreStatics/main/0/repo/org.lyi.android.files/en-US/phoneScreenshots/06.jpg" width="31%" alt="截图 6"/>
</p>

---

## 🛠️ 为什么选择 StarryFiles？（底层设计解析）

### 1. 正确的后端架构（Java NIO.2）
StarryFiles 摒弃了传统文件管理器中[将 UI 状态与文件操作杂糅的模型](https://github.com/TeamAmaze/AmazeFileManager/blob/master/app/src/main/java/com/amaze/filemanager/filesystem/HybridFile.java)，完整实现了 **Java NIO2 File API** 作为基础后端。清晰的解耦意味着更少的偶发 Bug，并且未来扩展新文件协议时更加轻盈。

### 2. 直达内核系统调用（Linux Syscall）
陈旧的 `java.io.File` 无法正确识别符号链接与权限，许多同类工具因此选择解析终端 `ls` 命令输出，但这既缓慢又极易在不同 ROM 或系统版本上发生解析崩溃。StarryFiles 采用 JNI 方式直接绑定 Linux 标准 libc 系统调用，带来极致流畅、稳定以及精准的原生文件操作体验。

### 3. 健壮的前端状态管理
基于 **ViewModel** 与 **LiveData** 构建，天然支持横竖屏无缝旋转，并周全考虑了文件覆盖/跳过冲突、复制剪切中断重试以及后台服务状态同步。

---

## 📥 下载与安装

- **[GitHub Releases 下载页](https://github.com/lingyicute/StarryFiles/releases)**

### 运行环境
- **系统要求**：Android 5.0（Lollipop，API Level 21）及以上。
- **Root 权限**：可选（仅在管理 `/system`、`/data` 等受保护系统分区时需要）。

---

## 🔨 从源码构建

### 环境要求
- **JDK 17** 或更高版本
- **Android SDK**（建议 Android 14 / API 34+）
- **Android NDK** 与 **CMake**（用于编译 JNI 原生模块）

### 构建步骤

1. **克隆仓库**：
   ```bash
   git clone https://github.com/lingyicute/StarryFiles.git
   cd StarryFiles
   ```

2. **编译 Debug 调试包**：
   ```bash
   ./gradlew assembleDebug
   ```

3. **编译 Release 正式包**：
   ```bash
   ./gradlew assembleRelease
   ```

编译生成的 APK 文件位于 `app/build/outputs/apk/` 目录下。

---

## 💡 定制 ROM 预装与集成建议

如果您希望将 StarryFiles 预装集成到您的定制 ROM 中，非常感谢您的支持！为了保证最终用户的最佳体验，请注意以下几点：

1. **请勿替换 AOSP `DocumentsUI`**：StarryFiles 并非设计用来替代系统的存储访问框架（SAF）选择器。相反，本应用依赖系统的 DocumentsUI 来获取外置 SD 卡或特定目录的授权。
2. **允许用户卸载或停用**：请确保预装时未将应用锁定为不可卸载，允许不需要的用户自主移除。
3. **避免签名冲突**：如果使用 ROM 私有签名密钥重新打包，建议更改 Application ID 包名，以避免用户未来通过官方渠道升级时发生签名冲突。

---

## 🤗 参与贡献

欢迎随时参与完善 StarryFiles！
- **问题反馈与需求建议**：在 [GitHub Issues](https://github.com/lingyicute/StarryFiles/issues) 中提交。
- **提交代码**：欢迎提交 Pull Request，提交前请确保代码格式整洁并通过构建测试。
- **多语言翻译**：欢迎参与界面的本地化与多语言翻译。

---

## 📄 许可

```text
Copyright (C) 2025-2026 lingyicute <li@92li.uk>

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program. If not, see <https://www.gnu.org/licenses/>.
```
