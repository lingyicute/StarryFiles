/*
 * Copyright (c) 2025 lingyicute <li@92li.uk>
 * All Rights Reserved.
 */

package org.lyi.android.files.util

inline fun <reified T : Throwable> Throwable.findCauseByClass(): T? {
    var current: Throwable? = this
    do {
        if (current is T) {
            return current
        }
        current = current!!.cause
    } while (current != null)
    return null
}
