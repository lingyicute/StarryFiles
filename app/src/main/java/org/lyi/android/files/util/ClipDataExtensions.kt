/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.util

import android.content.ClipData
import kotlin.reflect.KClass

fun ClipData.firstOrNull(): ClipData.Item? = if (itemCount > 0) getItemAt(0) else null

fun KClass<ClipData>.create(
    label: CharSequence?,
    mimeTypes: List<String>,
    items: List<ClipData.Item>
): ClipData =
    ClipData(label, mimeTypes.toTypedArray(), items[0])
        .apply { items.asSequence().drop(1).forEach { addItem(it) } }
