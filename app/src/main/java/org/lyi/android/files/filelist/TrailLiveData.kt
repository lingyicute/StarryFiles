/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.filelist

import android.os.Parcelable
import androidx.lifecycle.LiveData
import java8.nio.file.Path

class TrailLiveData : LiveData<TrailData>() {
    fun navigateTo(lastState: Parcelable, path: Path) {
        val oldTrailData = value
        if (oldTrailData == null) {
            resetTo(path)
            return
        }
        value = oldTrailData.navigateTo(lastState, path)
    }

    fun resetTo(path: Path) {
        value = TrailData.of(path)
    }

    fun navigateUp(): Boolean {
        val oldTrailData = value ?: return false
        val trailData = oldTrailData.navigateUp() ?: return false
        value = trailData
        return true
    }

    fun reload() {
        value = value
    }
}
