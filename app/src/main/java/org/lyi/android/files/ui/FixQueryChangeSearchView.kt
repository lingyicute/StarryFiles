/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.ui

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.AttrRes

class FixQueryChangeSearchView : FixLayoutSearchView {
    var shouldIgnoreQueryChange = false
        private set

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    )

    override fun setIconified(iconify: Boolean) {
        shouldIgnoreQueryChange = true
        super.setIconified(iconify)
        shouldIgnoreQueryChange = false
    }

    override fun onActionViewCollapsed() {
        shouldIgnoreQueryChange = true
        super.onActionViewCollapsed()
        shouldIgnoreQueryChange = false
    }

    override fun onActionViewExpanded() {
        shouldIgnoreQueryChange = true
        super.onActionViewExpanded()
        shouldIgnoreQueryChange = false
    }
}
