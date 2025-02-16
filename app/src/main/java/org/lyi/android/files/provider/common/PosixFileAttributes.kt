/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.common

import android.os.Parcelable
import java8.nio.file.attribute.PosixFileAttributes
import java8.nio.file.attribute.PosixFilePermission

interface PosixFileAttributes : PosixFileAttributes {
    fun type(): PosixFileType

    override fun isRegularFile(): Boolean = type() == PosixFileType.REGULAR_FILE

    override fun isDirectory(): Boolean = type() == PosixFileType.DIRECTORY

    override fun isSymbolicLink(): Boolean = type() == PosixFileType.SYMBOLIC_LINK

    override fun isOther(): Boolean = !isRegularFile && !isDirectory && !isSymbolicLink

    override fun fileKey(): Parcelable

    override fun owner(): PosixUser?

    override fun group(): PosixGroup?

    fun mode(): Set<PosixFileModeBit>?

    override fun permissions(): Set<PosixFilePermission>? = mode()?.toPermissions()

    fun seLinuxContext(): ByteString?
}
