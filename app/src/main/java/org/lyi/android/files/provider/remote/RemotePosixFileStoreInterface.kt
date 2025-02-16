/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.remote

import org.lyi.android.files.provider.common.PosixFileStore

class RemotePosixFileStoreInterface(
    private val fileStore: PosixFileStore
) : IRemotePosixFileStore.Stub() {
    override fun setReadOnly(readOnly: Boolean, exception: ParcelableException) {
        tryRun(exception) { fileStore.isReadOnly = readOnly }
    }

    override fun getTotalSpace(exception: ParcelableException): Long =
        tryRun(exception) { fileStore.totalSpace } ?: 0

    override fun getUsableSpace(exception: ParcelableException): Long =
        tryRun(exception) { fileStore.usableSpace } ?: 0

    override fun getUnallocatedSpace(exception: ParcelableException): Long =
        tryRun(exception) { fileStore.unallocatedSpace } ?: 0
}
