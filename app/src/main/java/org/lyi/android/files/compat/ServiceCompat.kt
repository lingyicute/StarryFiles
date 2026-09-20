/*
 * Copyright (c) 2025 lingyicute <li@92li.uk>
 * All Rights Reserved.
 */

package org.lyi.android.files.compat

import android.app.Service
import androidx.core.app.ServiceCompat

fun Service.stopForegroundCompat(flags: Int) {
    ServiceCompat.stopForeground(this, flags)
}
