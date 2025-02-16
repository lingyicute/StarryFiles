/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.util

sealed class DataState<T> {
    abstract val data: T?

    data class Loading<T>(override val data: T? = null) : DataState<T>()

    data class Success<T>(override val data: T) : DataState<T>()

    data class Error<T>(override val data: T?, val throwable: Throwable) : DataState<T>()
}

fun <T> DataState<T>.toLoading(): DataState.Loading<T> =
    this as? DataState.Loading ?: DataState.Loading(data)

fun <T> DataState<T>.toError(throwable: Throwable): DataState.Error<T> =
    DataState.Error(data, throwable)
