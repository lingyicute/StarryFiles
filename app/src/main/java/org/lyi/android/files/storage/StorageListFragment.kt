/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.storage

import android.graphics.drawable.NinePatchDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.h6ah4i.android.widget.advrecyclerview.animator.DraggableItemAnimator
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils
import org.lyi.android.files.databinding.StorageListFragmentBinding
import org.lyi.android.files.settings.Settings
import org.lyi.android.files.ui.ScrollingViewOnApplyWindowInsetsListener
import org.lyi.android.files.util.createIntent
import org.lyi.android.files.util.fadeToVisibilityUnsafe
import org.lyi.android.files.util.getDrawable
import org.lyi.android.files.util.startActivitySafe

class StorageListFragment : Fragment(), StorageListAdapter.Listener {
    private lateinit var binding: StorageListFragmentBinding

    private lateinit var adapter: StorageListAdapter
    private lateinit var dragDropManager: RecyclerViewDragDropManager
    private lateinit var wrappedAdapter: RecyclerView.Adapter<*>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        StorageListFragmentBinding.inflate(inflater, container, false)
            .also { binding = it }
            .root

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)
        activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(
            activity, RecyclerView.VERTICAL, false
        )
        adapter = StorageListAdapter(this)
        dragDropManager = RecyclerViewDragDropManager().apply {
            setDraggingItemShadowDrawable(
                getDrawable(
                    com.h6ah4i.android.materialshadowninepatch.R.drawable.ms9_composite_shadow_z2
                ) as NinePatchDrawable
            )
        }
        wrappedAdapter = dragDropManager.createWrappedAdapter(adapter)
        binding.recyclerView.adapter = wrappedAdapter
        binding.recyclerView.itemAnimator = DraggableItemAnimator()
        dragDropManager.attachRecyclerView(binding.recyclerView)
        binding.recyclerView.setOnApplyWindowInsetsListener(
            ScrollingViewOnApplyWindowInsetsListener(binding.recyclerView)
        )
        binding.fab.setOnClickListener { onAddStorage() }

        Settings.STORAGES.observe(viewLifecycleOwner) { onStorageListChanged(it) }
    }

    override fun onPause() {
        super.onPause()

        dragDropManager.cancelDrag()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        dragDropManager.release()
        WrapperAdapterUtils.releaseAll(wrappedAdapter)
    }

    private fun onStorageListChanged(storages: List<Storage>) {
        binding.emptyView.fadeToVisibilityUnsafe(storages.isEmpty())
        adapter.replace(storages)
    }

    private fun onAddStorage() {
        startActivitySafe(AddStorageDialogActivity::class.createIntent())
    }

    override fun editStorage(storage: Storage) {
        startActivitySafe(storage.createEditIntent())
    }

    override fun moveStorage(fromPosition: Int, toPosition: Int) {
        Storages.move(fromPosition, toPosition)
    }
}
