/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.filelist

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import java8.nio.file.Path
import org.lyi.android.files.navigation.NavigationRootMapLiveData
import org.lyi.android.files.util.valueCompat

class BreadcrumbLiveData(
    private val trailLiveData: LiveData<TrailData>
) : MediatorLiveData<BreadcrumbData>() {
    init {
        addSource(trailLiveData) { loadValue() }
        addSource(NavigationRootMapLiveData) { loadValue() }
    }

    private fun loadValue() {
        val navigationRootMap = NavigationRootMapLiveData.valueCompat
        val trailData = trailLiveData.valueCompat
        val paths = mutableListOf<Path>()
        val nameProducers = mutableListOf<(Context) -> String>()
        var selectedIndex = trailData.currentIndex
        for (path in trailData.trail) {
            val navigationRoot = navigationRootMap[path]
            val itemCount = nameProducers.size
            if (navigationRoot != null && selectedIndex >= itemCount) {
                selectedIndex -= itemCount
                paths.clear()
                paths.add(navigationRoot.path)
                nameProducers.clear()
                nameProducers.add { navigationRoot.getName(it) }
            } else {
                paths.add(path)
                nameProducers.add { path.name }
            }
        }
        value = BreadcrumbData(paths, nameProducers, selectedIndex)
    }
}
