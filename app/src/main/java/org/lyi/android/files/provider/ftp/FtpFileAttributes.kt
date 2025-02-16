/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.ftp

import android.os.Parcelable
import java.time.Instant
import java8.nio.file.attribute.FileTime
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.WriteWith
import org.lyi.android.files.provider.common.AbstractBasicFileAttributes
import org.lyi.android.files.provider.common.BasicFileType
import org.lyi.android.files.provider.common.FileTimeParceler
import org.apache.commons.net.ftp.FTPFile

@Parcelize
internal data class FtpFileAttributes(
    override val lastModifiedTime: @WriteWith<FileTimeParceler> FileTime,
    override val lastAccessTime: @WriteWith<FileTimeParceler> FileTime,
    override val creationTime: @WriteWith<FileTimeParceler> FileTime,
    override val type: BasicFileType,
    override val size: Long,
    override val fileKey: Parcelable,
) : AbstractBasicFileAttributes() {
    companion object {
        fun from(file: FTPFile, path: FtpPath): FtpFileAttributes {
            val lastModifiedTime = FileTime.from(file.timestamp?.toInstant() ?: Instant.EPOCH)
            val lastAccessTime = lastModifiedTime
            val creationTime = lastModifiedTime
            val type = when {
                file.isDirectory -> BasicFileType.DIRECTORY
                file.isFile -> BasicFileType.REGULAR_FILE
                file.isSymbolicLink -> BasicFileType.SYMBOLIC_LINK
                else -> BasicFileType.OTHER
            }
            val size = file.size.let { if (it != -1L) it else 0 }
            val fileKey = path
            return FtpFileAttributes(
                lastModifiedTime, lastAccessTime, creationTime, type, size, fileKey
            )
        }
    }
}
