/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.fileproperties.permission

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.lyi.android.files.provider.common.PosixFileModeBit
import org.lyi.android.files.util.toEnumSet
import org.lyi.android.files.util.valueCompat

class SetModeViewModel(mode: Set<PosixFileModeBit>) : ViewModel() {
    private val _modeLiveData: MutableLiveData<Set<PosixFileModeBit>> = MutableLiveData(mode)
    val modeLiveData: LiveData<Set<PosixFileModeBit>>
        get() = _modeLiveData
    val mode: Set<PosixFileModeBit>
        get() = _modeLiveData.valueCompat

    fun toggleModeBit(modeBit: PosixFileModeBit) {
        val mode = _modeLiveData.valueCompat.toEnumSet()
        if (modeBit in mode) {
            mode -= modeBit
        } else {
            mode += modeBit
        }
        _modeLiveData.value = mode
    }
}
