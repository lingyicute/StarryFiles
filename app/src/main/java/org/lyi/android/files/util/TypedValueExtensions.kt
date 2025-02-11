/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.util

import android.util.TypedValue
import java.util.concurrent.atomic.AtomicReference
import kotlin.reflect.KClass

inline fun <T> KClass<TypedValue>.useTemp(block: (TypedValue) -> T): T {
    val temp = TypedValue::class.obtainTemp()
    return try {
        block(temp)
    } finally {
        temp.releaseTemp()
    }
}

private val tempTypedValue = AtomicReference(TypedValue())

fun KClass<TypedValue>.obtainTemp(): TypedValue = tempTypedValue.getAndSet(null) ?: TypedValue()

fun TypedValue.releaseTemp() {
    tempTypedValue.compareAndSet(null, this)
}
