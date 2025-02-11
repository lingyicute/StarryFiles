/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.storage

import android.view.ViewGroup
import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableItemViewHolder
import org.lyi.android.files.compat.foregroundCompat
import org.lyi.android.files.compat.isTransformedTouchPointInViewCompat
import org.lyi.android.files.databinding.StorageItemBinding
import org.lyi.android.files.ui.SimpleAdapter
import org.lyi.android.files.util.layoutInflater

class StorageListAdapter(
    private val listener: Listener
) : SimpleAdapter<Storage, StorageListAdapter.ViewHolder>(),
    DraggableItemAdapter<StorageListAdapter.ViewHolder> {
    override val hasStableIds: Boolean
        get() = true

    override fun getItemId(position: Int): Long = getItem(position).id

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            StorageItemBinding.inflate(parent.context.layoutInflater, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val storage = getItem(position)
        val binding = holder.binding
        // Need to remove the ripple before it's drawn onto the bitmap for dragging.
        binding.root.foregroundCompat!!.mutate().setVisible(!holder.dragState.isActive, false)
        binding.root.setOnClickListener { listener.editStorage(storage) }
        binding.iconImage.setImageResource(storage.iconRes)
        binding.nameText.isActivated = storage.isVisible
        binding.nameText.text = storage.getName(binding.nameText.context)
        binding.descriptionText.text = storage.description
    }

    override fun onCheckCanStartDrag(holder: ViewHolder, position: Int, x: Int, y: Int): Boolean =
        (holder.binding.root as ViewGroup).isTransformedTouchPointInViewCompat(
            x.toFloat(), y.toFloat(), holder.binding.dragHandleView, null
        )

    override fun onGetItemDraggableRange(holder: ViewHolder, position: Int): ItemDraggableRange? =
        null

    override fun onCheckCanDrop(draggingPosition: Int, dropPosition: Int): Boolean = true

    override fun onItemDragStarted(position: Int) {
        notifyDataSetChanged()
    }

    override fun onItemDragFinished(fromPosition: Int, toPosition: Int, result: Boolean) {
        notifyDataSetChanged()
    }

    override fun onMoveItem(fromPosition: Int, toPosition: Int) {
        if (fromPosition == toPosition) {
            return
        }
        listener.moveStorage(fromPosition, toPosition)
    }

    class ViewHolder(val binding: StorageItemBinding) : AbstractDraggableItemViewHolder(
        binding.root
    )

    interface Listener {
        fun editStorage(storage: Storage)
        fun moveStorage(fromPosition: Int, toPosition: Int)
    }
}
