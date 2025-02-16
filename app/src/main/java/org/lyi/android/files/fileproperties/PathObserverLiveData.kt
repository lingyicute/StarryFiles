/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.fileproperties

import java8.nio.file.Path
import org.lyi.android.files.filelist.PathObserver
import org.lyi.android.files.util.CloseableLiveData

abstract class PathObserverLiveData<T>(protected val path: Path) : CloseableLiveData<T>() {
    private lateinit var observer: PathObserver

    @Volatile
    private var changedWhileInactive = false

    protected fun observe() {
        observer = PathObserver(path) { onChangeObserved() }
    }

    abstract fun loadValue()

    private fun onChangeObserved() {
        if (hasActiveObservers()) {
            loadValue()
        } else {
            changedWhileInactive = true
        }
    }

    override fun onActive() {
        if (changedWhileInactive) {
            loadValue()
            changedWhileInactive = false
        }
    }

    override fun close() {
        observer.close()
    }
}
