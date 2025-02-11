/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.colorpicker

import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter

class ColorPaletteAdapter(private val colors: IntArray) : BaseAdapter() {
    override fun hasStableIds(): Boolean = true

    override fun getCount(): Int = colors.size

    override fun getItem(position: Int): Int = colors[position]

    override fun getItemId(position: Int): Long = getItem(position).toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val swatchView = convertView as ColorSwatchView?
            ?: ColorSwatchView(parent.context).apply {
                layoutParams = AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        return swatchView.apply {
            setColor(getItem(position))
        }
    }
}
