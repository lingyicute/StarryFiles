/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.compat

import android.app.Service
import androidx.core.app.ServiceCompat

fun Service.stopForegroundCompat(flags: Int) {
    ServiceCompat.stopForeground(this, flags)
}
