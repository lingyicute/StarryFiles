/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.sftp

import java8.nio.file.Path
import org.lyi.android.files.provider.sftp.client.Authority

fun Authority.createSftpRootPath(): Path =
    SftpFileSystemProvider.getOrNewFileSystem(this).rootDirectory
