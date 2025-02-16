/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.linux

import android.os.Parcelable
import java.time.Instant
import java8.nio.file.attribute.FileTime
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.WriteWith
import org.lyi.android.files.provider.common.AbstractPosixFileAttributes
import org.lyi.android.files.provider.common.ByteString
import org.lyi.android.files.provider.common.FileTimeParceler
import org.lyi.android.files.provider.common.PosixFileMode
import org.lyi.android.files.provider.common.PosixFileModeBit
import org.lyi.android.files.provider.common.PosixFileType
import org.lyi.android.files.provider.common.PosixGroup
import org.lyi.android.files.provider.common.PosixUser
import org.lyi.android.files.provider.linux.syscall.StructStat

@Parcelize
internal class LinuxFileAttributes(
    override val lastModifiedTime: @WriteWith<FileTimeParceler> FileTime,
    override val lastAccessTime: @WriteWith<FileTimeParceler> FileTime,
    override val creationTime: @WriteWith<FileTimeParceler> FileTime,
    override val type: PosixFileType,
    override val size: Long,
    override val fileKey: Parcelable,
    override val owner: PosixUser?,
    override val group: PosixGroup?,
    override val mode: Set<PosixFileModeBit>?,
    override val seLinuxContext: ByteString?
) : AbstractPosixFileAttributes() {
    companion object {
        fun from(
            stat: StructStat,
            owner: PosixUser,
            group: PosixGroup,
            seLinuxContext: ByteString?
        ): LinuxFileAttributes {
            val lastModifiedTime =
                FileTime.from(Instant.ofEpochSecond(stat.st_mtim.tv_sec, stat.st_mtim.tv_nsec))
            val lastAccessTime =
                FileTime.from(Instant.ofEpochSecond(stat.st_atim.tv_sec, stat.st_atim.tv_nsec))
            val creationTime = lastModifiedTime
            val type = PosixFileType.fromMode(stat.st_mode)
            val size = stat.st_size
            val fileKey = LinuxFileKey(stat.st_dev, stat.st_ino)
            val mode = PosixFileMode.fromInt(stat.st_mode)
            return LinuxFileAttributes(
                lastModifiedTime, lastAccessTime, creationTime, type, size, fileKey, owner, group,
                mode, seLinuxContext
            )
        }
    }
}
