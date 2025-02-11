/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.compat

import android.content.pm.PermissionInfo
import androidx.core.content.pm.PermissionInfoCompat

val PermissionInfo.protectionCompat: Int
    get() = PermissionInfoCompat.getProtection(this)
