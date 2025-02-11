/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.linux.syscall

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @see android.system.StructTimespec
 */
@Parcelize
class StructTimespec(
    val tv_sec: Long, /*time_t*/
    val tv_nsec: Long
) : Parcelable
