/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.util

import androidx.lifecycle.LiveData

abstract class StatefulLiveData<T : Any> : LiveData<Stateful<T>>() {
    init {
        value = Loading(null)
    }

    val isReady: Boolean
        get() = valueCompat.let { it is Loading && it.value == null }

    fun reset() {
        check(!(valueCompat.let { it is Loading && it.value != null }))
        value = Loading(null)
    }
}
