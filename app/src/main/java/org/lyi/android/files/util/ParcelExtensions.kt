/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.util

import android.os.Parcel
import android.os.Parcelable
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import org.lyi.android.files.app.appClassLoader
import org.lyi.android.files.compat.readParcelableListCompat

@Suppress("DEPRECATION")
fun <T : Parcelable> Parcel.readParcelable(): T? = readParcelable(appClassLoader)

fun <T : Parcelable?> Parcel.readParcelableListCompat(classLoader: ClassLoader?): List<T> =
    readParcelableListCompat(mutableListOf(), classLoader)

fun <E : Parcelable?, L : MutableList<E>> Parcel.readParcelableListCompat(list: L): L =
    readParcelableListCompat(list, appClassLoader)

fun <T : Parcelable?> Parcel.readParcelableListCompat(): List<T> =
    readParcelableListCompat(mutableListOf())

@Suppress("UNCHECKED_CAST") fun <T> Parcel.readValue(): T? = readValue(appClassLoader) as T?

@OptIn(ExperimentalContracts::class)
inline fun <R> Parcel.use(block: (Parcel) -> R): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return try {
        block(this)
    } finally {
        recycle()
    }
}

@OptIn(ExperimentalContracts::class)
inline fun <R> Parcel.withPosition(position: Int, block: Parcel.() -> R): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    val savedPosition = dataPosition()
    setDataPosition(position)
    return try {
        block(this)
    } finally {
        setDataPosition(savedPosition)
    }
}
