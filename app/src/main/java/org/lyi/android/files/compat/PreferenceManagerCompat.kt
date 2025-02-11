/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.compat

import android.content.Context

object PreferenceManagerCompat {
    fun getDefaultSharedPreferencesName(context: Context): String =
        "${context.packageName}_preferences"

    val defaultSharedPreferencesMode: Int
        get() = Context.MODE_PRIVATE
}
