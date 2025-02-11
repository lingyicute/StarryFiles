/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.navigation

import android.content.Context
import java8.nio.file.Path

interface NavigationRoot {
    val path: Path

    fun getName(context: Context): String
}
