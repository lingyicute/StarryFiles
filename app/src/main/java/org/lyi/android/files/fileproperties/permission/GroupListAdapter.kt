/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.fileproperties.permission

import androidx.annotation.DrawableRes
import org.lyi.android.files.R
import org.lyi.android.files.util.SelectionLiveData

class GroupListAdapter(
    selectionLiveData: SelectionLiveData<Int>
) : PrincipalListAdapter(selectionLiveData) {
    @DrawableRes
    override val principalIconRes: Int = R.drawable.people_icon_control_normal_24dp
}
