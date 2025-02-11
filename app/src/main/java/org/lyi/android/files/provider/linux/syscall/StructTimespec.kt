/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
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
