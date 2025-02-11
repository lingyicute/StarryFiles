/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.compat

import kotlin.comparisons.reversed as kotlinReversed
import kotlin.collections.removeFirst as kotlinRemoveFirst
import kotlin.collections.removeLast as kotlinRemoveLast

fun <T> Comparator<T>.reversedCompat(): Comparator<T> = kotlinReversed()

fun <T> MutableList<T>.removeFirstCompat(): T = kotlinRemoveFirst()

fun <T> MutableList<T>.removeLastCompat(): T = kotlinRemoveLast()
