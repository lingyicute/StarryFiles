/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.linux

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class LinuxFileKey(
    private val deviceId: Long,
    private val inodeNumber: Long
) : Parcelable
