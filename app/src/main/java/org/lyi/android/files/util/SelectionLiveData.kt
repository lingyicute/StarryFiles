/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView

class SelectionLiveData<Key> : MutableLiveData<Key>() {
    fun observe(owner: LifecycleOwner, adapter: RecyclerView.Adapter<*>) {
        observe(owner) {
            adapter.notifyItemRangeChanged(0, adapter.itemCount, PAYLOAD_SELECTION_CHANGED)
        }
    }

    companion object {
        val PAYLOAD_SELECTION_CHANGED = Any()
    }
}
