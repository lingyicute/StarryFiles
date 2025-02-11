/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.compat

import android.view.Menu
import androidx.core.view.MenuCompat

fun Menu.setGroupDividerEnabledCompat(groupDividerEnabled: Boolean) {
    MenuCompat.setGroupDividerEnabled(this, groupDividerEnabled)
}
