/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.compat

import android.app.Dialog
import android.view.View
import androidx.annotation.IdRes
import androidx.core.app.DialogCompat

@Suppress("UNCHECKED_CAST")
fun <T : View> Dialog.requireViewByIdCompat(@IdRes id: Int): T =
    DialogCompat.requireViewById(this, id) as T
