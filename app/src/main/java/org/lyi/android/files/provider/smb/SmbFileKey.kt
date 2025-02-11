/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.smb

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.lyi.android.files.util.hash

@Parcelize
internal class SmbFileKey(
    private val path: SmbPath,
    private val fileId: Long
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (javaClass != other?.javaClass) {
            return false
        }
        other as SmbFileKey
        return if (fileId != 0L || other.fileId != 0L) {
            path.authority == other.path.authority
                && path.sharePath!!.name == other.path.sharePath!!.name
                && fileId == other.fileId
        } else {
            path == other.path
        }
    }

    override fun hashCode(): Int {
        return if (fileId != 0L) {
            hash(path.authority, path.sharePath!!.name, fileId)
        } else {
            path.hashCode()
        }
    }
}
