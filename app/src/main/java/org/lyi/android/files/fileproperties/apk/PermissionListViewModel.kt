/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.fileproperties.apk

import androidx.lifecycle.ViewModel

class PermissionListViewModel(permissionNames: Array<String>) : ViewModel() {
    val permissionListLiveData = PermissionListLiveData(permissionNames)
}
