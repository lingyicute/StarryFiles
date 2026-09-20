/*
 * Copyright (c) 2025 lingyicute <li@92li.uk>
 * All Rights Reserved.
 */

package org.lyi.android.files.util

fun Any.hash(vararg values: Any?): Int = values.contentDeepHashCode()
