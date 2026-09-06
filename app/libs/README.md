# app/libs

Vendored third-party jars, picked up by `implementation fileTree(dir: 'libs', include: ['*.jar'])`
in `app/build.gradle`.

## dav4jvm-c1bc1434883.jar

| | |
|---|---|
| Project | [bitfireAT/dav4jvm](https://github.com/bitfireAT/dav4jvm) |
| License | Mozilla Public License 2.0 |
| Upstream commit | `c1bc14348831bcdb00f3a6eec4859b81c7dc3728` ("Simplify UrlUtils.equals(), move to extension (#54)", 2024-08-08) |
| Source | `https://jitpack.io/com/github/bitfireAT/dav4jvm/c1bc1434883/dav4jvm-c1bc1434883.jar` |
| SHA-256 | `2f7bd194e287ed0192c2c6bdb229a4fb26fb807af9908ba5402556472d526f98` |
| Size | 269374 bytes (190 entries) |

### Why is this a local jar instead of a Gradle coordinate?

The build used to declare `com.github.bitfireAT:dav4jvm:c1bc143` from JitPack.
JitPack still reports that version as `"status": "ok"`, but it dropped the cached
artifacts, so every request 404s with `File not found. Build ok` and the build fails with:

```
Could not find com.github.bitfireAT:dav4jvm:c1bc143.
```

JitPack builds artifacts lazily and evicts them at will, so any short-commit
coordinate can vanish again without warning. Checking the jar in makes the build
reproducible and independent of JitPack uptime.

### Transitive dependencies

The jar carries no POM, so dav4jvm's dependencies are declared explicitly in
`app/build.gradle`:

- `com.squareup.okhttp3:okhttp:4.12.0` — added explicitly (was transitive before)
- `org.jetbrains.kotlin:kotlin-stdlib` — already provided by the project
- `org.ogce:xpp3` — deliberately **not** added; Android ships an XmlPullParser
  (this matches the old `exclude group: 'org.ogce', module: 'xpp3'`)

### How to refresh / verify

```bash
curl -sSL -o app/libs/dav4jvm-c1bc1434883.jar \
  https://jitpack.io/com/github/bitfireAT/dav4jvm/c1bc1434883/dav4jvm-c1bc1434883.jar
sha256sum app/libs/dav4jvm-c1bc1434883.jar
```

Do not upgrade to dav4jvm 3.x/4.x without real work: those releases switched from
OkHttp to Ktor and moved the API into `at.bitfire.dav4jvm.ktor.*`. `DavResource`
no longer exists there, which would break every `at.bitfire.*` import in this app
as well as `app/src/main/java/at/bitfire/dav4jvm/DavResourceAccessor.java`, which
reaches into the internal `DavResource.followRedirects$build`.
