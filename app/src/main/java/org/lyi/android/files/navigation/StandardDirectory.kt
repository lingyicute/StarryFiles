/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.navigation

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class StandardDirectory internal constructor(
    @DrawableRes val iconRes: Int,
    @StringRes private val titleRes: Int,
    private val customTitle: String?,
    val relativePath: String,
    val isEnabled: Boolean
) {
    constructor(
        @DrawableRes iconRes: Int,
        @StringRes titleRes: Int,
        relativePath: String,
        enabled: Boolean
    ) : this(iconRes, titleRes, null, relativePath, enabled)

    val id: Long
        get() = relativePath.hashCode().toLong()

    val key: String
        get() = relativePath

    fun getTitle(context: Context): String =
        if (!customTitle.isNullOrEmpty()) customTitle else context.getString(titleRes)

    fun withSettings(settings: StandardDirectorySettings): StandardDirectory =
        StandardDirectory(iconRes, titleRes, settings.customTitle, relativePath, settings.isEnabled)

    fun toSettings(): StandardDirectorySettings =
        StandardDirectorySettings(relativePath, customTitle, isEnabled)
}
