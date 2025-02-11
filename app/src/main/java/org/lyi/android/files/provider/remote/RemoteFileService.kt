/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.remote

import java8.nio.file.FileSystem
import org.lyi.android.files.provider.common.PosixFileAttributeView
import org.lyi.android.files.provider.common.PosixFileStore

abstract class RemoteFileService(private val remoteInterface: RemoteInterface<IRemoteFileService>) {
    @Throws(RemoteFileSystemException::class)
    fun getRemoteFileSystemProviderInterface(scheme: String): IRemoteFileSystemProvider =
        remoteInterface.get().call { getRemoteFileSystemProviderInterface(scheme) }

    @Throws(RemoteFileSystemException::class)
    fun getRemoteFileSystemInterface(fileSystem: FileSystem): IRemoteFileSystem =
        remoteInterface.get().call { getRemoteFileSystemInterface(fileSystem.toParcelable()) }

    @Throws(RemoteFileSystemException::class)
    fun getRemotePosixFileStoreInterface(fileStore: PosixFileStore): IRemotePosixFileStore =
        remoteInterface.get().call { getRemotePosixFileStoreInterface(fileStore.toParcelable()) }

    @Throws(RemoteFileSystemException::class)
    fun getRemotePosixFileAttributeViewInterface(
        attributeView: PosixFileAttributeView
    ): IRemotePosixFileAttributeView =
        remoteInterface.get().call {
            getRemotePosixFileAttributeViewInterface(attributeView.toParcelable())
        }
}
