/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.util

import androidx.recyclerview.widget.RecyclerView

inline fun RecyclerView.addOnScrollListener(
    crossinline onScrollStateChanged: (recyclerView: RecyclerView, newState: Int) -> Unit =
        { _, _ -> },
    crossinline onScrolled: (recyclerView: RecyclerView, dx: Int, dy: Int) -> Unit = { _, _, _ -> },
): RecyclerView.OnScrollListener {
    val listener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            onScrollStateChanged(recyclerView, newState)
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            onScrolled(recyclerView, dx, dy)
        }
    }
    addOnScrollListener(listener)
    return listener
}
