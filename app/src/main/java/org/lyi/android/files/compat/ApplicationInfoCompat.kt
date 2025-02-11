/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.compat

import android.content.pm.ApplicationInfo
import android.os.Build
import org.lyi.android.files.hiddenapi.RestrictedHiddenApi
import org.lyi.android.files.util.lazyReflectedField

@RestrictedHiddenApi
private val versionCodeField by lazyReflectedField(ApplicationInfo::class.java, "versionCode")

@RestrictedHiddenApi
private val longVersionCodeField by lazyReflectedField(
    ApplicationInfo::class.java, "longVersionCode"
)

val ApplicationInfo.longVersionCodeCompat: Long
    get() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            longVersionCodeField.getLong(this)
        } else {
            versionCodeField.getInt(this).toLong()
        }
