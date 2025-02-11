/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.fileproperties.apk

import android.content.pm.PackageInfo

class ApkInfo(
    val packageInfo: PackageInfo,
    val label: String,
    val signingCertificateDigests: List<String>,
    val pastSigningCertificateDigests: List<String>
)
