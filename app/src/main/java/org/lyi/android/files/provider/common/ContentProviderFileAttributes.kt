/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.common

import android.os.Parcelable
import java8.nio.file.attribute.BasicFileAttributes
import java8.nio.file.attribute.FileTime
import org.lyi.android.files.file.MimeType

interface ContentProviderFileAttributes : BasicFileAttributes {
    override fun lastAccessTime(): FileTime = lastModifiedTime()

    override fun creationTime(): FileTime = lastModifiedTime()

    fun mimeType(): String?

    override fun isRegularFile(): Boolean = !isDirectory

    override fun isDirectory(): Boolean = mimeType() == MimeType.DIRECTORY.value

    override fun isSymbolicLink(): Boolean = false

    override fun isOther(): Boolean = false

    override fun fileKey(): Parcelable
}
