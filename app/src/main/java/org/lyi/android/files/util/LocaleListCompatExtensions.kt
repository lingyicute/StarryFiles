/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.util

import androidx.core.os.LocaleListCompat
import java.util.Locale

fun LocaleListCompat.toList(): List<Locale> = List(size()) { this[it]!! }
