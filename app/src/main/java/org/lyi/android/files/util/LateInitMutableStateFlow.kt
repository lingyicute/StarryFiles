/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.util

import kotlinx.coroutines.flow.MutableStateFlow

@Suppress("FunctionName", "UNCHECKED_CAST")
fun <T : Any> LateInitMutableStateFlow(): MutableStateFlow<T> =
    MutableStateFlow<T?>(null) as MutableStateFlow<T>
