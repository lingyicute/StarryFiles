/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.util

import android.net.Uri
import org.lyi.android.files.app.contentResolver

fun Uri.takePersistablePermission(modeFlags: Int): Boolean =
    try {
        contentResolver.takePersistableUriPermission(this, modeFlags)
        true
    } catch (e: SecurityException) {
        e.printStackTrace()
        false
    }

fun Uri.releasePersistablePermission(modeFlags: Int): Boolean =
    try {
        contentResolver.releasePersistableUriPermission(this, modeFlags)
        true
    } catch (e: SecurityException) {
        e.printStackTrace()
        false
    }
