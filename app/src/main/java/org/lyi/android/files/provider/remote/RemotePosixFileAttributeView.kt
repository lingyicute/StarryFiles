/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.remote

import java8.nio.file.attribute.FileTime
import org.lyi.android.files.provider.common.ByteString
import org.lyi.android.files.provider.common.PosixFileAttributeView
import org.lyi.android.files.provider.common.PosixFileAttributes
import org.lyi.android.files.provider.common.PosixFileModeBit
import org.lyi.android.files.provider.common.PosixGroup
import org.lyi.android.files.provider.common.PosixUser
import org.lyi.android.files.provider.common.toParcelable
import java.io.IOException

abstract class RemotePosixFileAttributeView(
    private val remoteInterface: RemoteInterface<IRemotePosixFileAttributeView>
) : PosixFileAttributeView {
    @Throws(IOException::class)
    override fun readAttributes(): PosixFileAttributes =
        remoteInterface.get().call { exception -> readAttributes(exception) }.value()

    @Throws(IOException::class)
    override fun setTimes(
        lastModifiedTime: FileTime?,
        lastAccessTime: FileTime?,
        createTime: FileTime?
    ) {
        remoteInterface.get().call { exception ->
            setTimes(
                lastModifiedTime?.toParcelable(), lastAccessTime?.toParcelable(),
                createTime?.toParcelable(), exception
            )
        }
    }

    @Throws(IOException::class)
    override fun setOwner(owner: PosixUser) {
        remoteInterface.get().call { exception -> setOwner(owner, exception) }
    }

    @Throws(IOException::class)
    override fun setGroup(group: PosixGroup) {
        remoteInterface.get().call { exception -> setGroup(group, exception) }
    }

    @Throws(IOException::class)
    override fun setMode(mode: Set<PosixFileModeBit>) {
        remoteInterface.get().call { exception -> setMode(mode.toParcelable(), exception) }
    }

    @Throws(IOException::class)
    override fun setSeLinuxContext(context: ByteString) {
        remoteInterface.get().call { exception ->
            setSeLinuxContext(context.toParcelable(), exception)
        }
    }

    @Throws(IOException::class)
    override fun restoreSeLinuxContext() {
        remoteInterface.get().call { exception -> restoreSeLinuxContext(exception) }
    }
}
