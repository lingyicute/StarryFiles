/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.fileproperties.permission

import android.content.pm.ApplicationInfo

class PrincipalItem(
    val id: Int,
    val name: String?,
    val applicationInfos: List<ApplicationInfo>,
    val applicationLabels: List<String>
)
