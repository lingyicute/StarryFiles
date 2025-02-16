/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.view.isGone

class AllowSoftInputHackAlertDialogCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0,
    @StyleRes defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {
    override fun onCheckIsTextEditor(): Boolean = true

    // Called once during ViewGroup.addView().
    override fun hasFocus(): Boolean {
        // Makes hasCustomPanel false in AlertController.setupView().
        (parent.parent as View).isGone = true
        return super.hasFocus()
    }
}
