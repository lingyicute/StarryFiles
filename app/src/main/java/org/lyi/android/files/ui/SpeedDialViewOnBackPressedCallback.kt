/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.ui

import androidx.activity.OnBackPressedCallback
import com.leinardi.android.speeddial.SpeedDialView

class SpeedDialViewOnBackPressedCallback(
    private val speedDialView: SpeedDialView
) : OnBackPressedCallback(speedDialView.isOpen) {
    init {
        speedDialView.setOnChangeListener(
            object : SpeedDialView.OnChangeListener {
                override fun onMainActionSelected(): Boolean = false

                override fun onToggleChanged(isOpen: Boolean) {
                    isEnabled = speedDialView.isOpen
                }
            }
        )
    }

    override fun handleOnBackPressed() {
        speedDialView.close()
    }
}
