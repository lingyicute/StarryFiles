/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.common

import java8.nio.file.attribute.BasicFileAttributes

interface EncryptedFileAttributes {
    fun isEncrypted(): Boolean
}

fun BasicFileAttributes.isEncrypted(): Boolean =
    if (this is EncryptedFileAttributes) isEncrypted() else false
