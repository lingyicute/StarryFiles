/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.util

fun <T> List<T>.startsWith(prefix: List<T>): Boolean =
    size >= prefix.size && subList(0, prefix.size) == prefix

fun <T> List<T>.endsWith(suffix: List<T>): Boolean =
    size >= suffix.size && subList(size - suffix.size, size) == suffix
