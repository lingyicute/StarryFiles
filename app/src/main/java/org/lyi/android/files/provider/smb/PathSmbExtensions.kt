/*
 * Copyright (c) 2020 Hai Zhang <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.smb

import java8.nio.file.Path
import org.lyi.android.files.provider.smb.client.Authority

fun Authority.createSmbRootPath(): Path =
    SmbFileSystemProvider.getOrNewFileSystem(this).rootDirectory
