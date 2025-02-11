/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.util

import android.os.Bundle
import android.os.Parcelable
import android.util.SparseArray
import org.lyi.android.files.app.appClassLoader

fun <T : Parcelable> Bundle.getParcelableSafe(key: String?): T? {
    classLoader = appClassLoader
    return getParcelable(key)
}

fun Bundle.getParcelableArraySafe(key: String?): Array<Parcelable>? {
    classLoader = appClassLoader
    return getParcelableArray(key)
}

fun <T : Parcelable?> Bundle.getParcelableArrayListSafe(key: String?): ArrayList<T>? {
    classLoader = appClassLoader
    return getParcelableArrayList(key)
}

fun <T : Parcelable?> Bundle.getSparseParcelableArraySafe(key: String?): SparseArray<T>? {
    classLoader = appClassLoader
    return getSparseParcelableArray(key)
}
