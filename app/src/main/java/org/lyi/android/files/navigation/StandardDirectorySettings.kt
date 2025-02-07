/*
 * Copyright (c) 2019 Hai Zhang <dreaming.in.code.zh@gmail.com>
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
