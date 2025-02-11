/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.webdav

import java8.nio.file.Path
import org.lyi.android.files.provider.webdav.client.Authority

fun Authority.createWebDavRootPath(): Path =
    WebDavFileSystemProvider.getOrNewFileSystem(this).rootDirectory
