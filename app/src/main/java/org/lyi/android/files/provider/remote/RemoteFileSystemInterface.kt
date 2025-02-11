/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.remote

import java8.nio.file.FileSystem

class RemoteFileSystemInterface(private val fileSystem: FileSystem) : IRemoteFileSystem.Stub() {
    override fun close(exception: ParcelableException) {
        tryRun(exception) { fileSystem.close() }
    }
}
