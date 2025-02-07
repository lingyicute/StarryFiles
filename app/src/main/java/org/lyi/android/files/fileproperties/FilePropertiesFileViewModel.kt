/*
 * Copyright (c) 2019 Hai Zhang <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.fileproperties

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import org.lyi.android.files.file.FileItem
import org.lyi.android.files.util.Stateful

class FilePropertiesFileViewModel(file: FileItem) : ViewModel() {
    private val _fileLiveData = FileLiveData(file)
    val fileLiveData: LiveData<Stateful<FileItem>>
        get() = _fileLiveData

    fun reload() {
        _fileLiveData.loadValue()
    }

    override fun onCleared() {
        _fileLiveData.close()
    }
}
