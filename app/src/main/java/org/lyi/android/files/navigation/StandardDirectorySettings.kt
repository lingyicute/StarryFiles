/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StandardDirectorySettings(
    val id: String,
    val customTitle: String?,
    val isEnabled: Boolean
) : Parcelable
