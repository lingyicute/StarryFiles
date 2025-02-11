/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.hiddenapi

import android.os.Build

object HiddenApi {
    fun disableHiddenApiChecks() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            System.loadLibrary("hiddenapi")
        }
    }
}
