/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.ui

import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar

class PersistentBarLayoutToolbarActionMode(
    private val persistentBarLayout: PersistentBarLayout,
    bar: ViewGroup,
    toolbar: Toolbar
) : ToolbarActionMode(bar, toolbar) {
    override fun show(bar: ViewGroup, animate: Boolean) {
        persistentBarLayout.showBar(bar, animate)
    }

    override fun hide(bar: ViewGroup, animate: Boolean) {
        persistentBarLayout.hideBar(bar, animate)
    }
}
