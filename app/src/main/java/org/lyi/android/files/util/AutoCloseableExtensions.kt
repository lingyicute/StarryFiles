/*
 * Copyright (c) 2025 lingyicute <li@92li.uk>
 * All Rights Reserved.
 */

package org.lyi.android.files.util

fun AutoCloseable.closeSafe() {
    try {
        close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
