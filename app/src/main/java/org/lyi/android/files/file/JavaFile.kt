/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.file

import java.io.File

object JavaFile {
    fun isDirectory(path: String): Boolean = File(path).isDirectory

    fun getFreeSpace(path: String): Long = File(path).freeSpace

    fun getTotalSpace(path: String): Long = File(path).totalSpace
}
