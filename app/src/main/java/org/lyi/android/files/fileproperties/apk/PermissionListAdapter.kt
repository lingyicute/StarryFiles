/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.fileproperties.apk

import android.content.pm.PermissionInfo
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import org.lyi.android.files.app.clipboardManager
import org.lyi.android.files.compat.protectionCompat
import org.lyi.android.files.databinding.PermissionItemBinding
import org.lyi.android.files.ui.SimpleAdapter
import org.lyi.android.files.util.copyText
import org.lyi.android.files.util.isBold
import org.lyi.android.files.util.layoutInflater
import java.util.Locale

class PermissionListAdapter : SimpleAdapter<PermissionItem, PermissionListAdapter.ViewHolder>() {
    override val hasStableIds: Boolean
        get() = true

    override fun getItemId(position: Int): Long = getItem(position).name.hashCode().toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(PermissionItemBinding.inflate(parent.context.layoutInflater, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        val permission = getItem(position)
        val name = permission.name
        binding.root.setOnLongClickListener {
            clipboardManager.copyText(name, binding.root.context)
            true
        }
        val label = permission.label
        binding.labelText.text = label?.capitalize(Locale.getDefault()) ?: name
        binding.labelText.isBold = (permission.permissionInfo?.protectionCompat
            == PermissionInfo.PROTECTION_DANGEROUS)
        binding.nameText.isVisible = label != null
        binding.nameText.text = name
        binding.descriptionText.text = permission.description
    }

    class ViewHolder(val binding: PermissionItemBinding) : RecyclerView.ViewHolder(binding.root)
}
