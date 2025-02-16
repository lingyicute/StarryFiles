/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import org.lyi.android.files.R
import org.lyi.android.files.app.application

var ClipboardManager.primaryText: CharSequence
    get() = primaryClip?.firstOrNull()?.coerceToText(application)!!
    set(value) {
        setPrimaryClip(ClipData.newPlainText(null, value))
    }

private const val TOAST_COPIED_TEXT_MAX_LENGTH = 40

fun ClipboardManager.copyText(text: CharSequence, context: Context) {
    primaryText = text
    var copiedText = text
    var ellipsized = false
    if (copiedText.length > TOAST_COPIED_TEXT_MAX_LENGTH) {
        copiedText = copiedText.subSequence(0, TOAST_COPIED_TEXT_MAX_LENGTH)
        ellipsized = true
    }
    val indexOfFirstNewline = copiedText.indexOf('\n')
    if (indexOfFirstNewline != -1) {
        val indexOfSecondNewline = copiedText.indexOf('\n', indexOfFirstNewline + 1)
        if (indexOfSecondNewline != -1) {
            copiedText = copiedText.subSequence(0, indexOfSecondNewline)
            ellipsized = true
        }
    }
    if (ellipsized) {
        copiedText = "$copiedText…"
    }
    context.showToast(context.getString(R.string.copied_to_clipboard_format, copiedText))
}
