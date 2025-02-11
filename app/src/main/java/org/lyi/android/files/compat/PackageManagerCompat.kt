/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.compat

import android.annotation.SuppressLint
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.SigningInfo
import android.os.Build
import org.lyi.android.files.util.andInv
import org.lyi.android.files.util.hasBits

object PackageManagerCompat {
    @SuppressLint("InlinedApi")
    const val MATCH_UNINSTALLED_PACKAGES = PackageManager.MATCH_UNINSTALLED_PACKAGES
}

fun PackageManager.getPackageArchiveInfoCompat(archiveFilePath: String, flags: Int): PackageInfo? {
    var packageInfo = getPackageArchiveInfo(archiveFilePath, flags)
    // getPackageArchiveInfo() returns null for unsigned APKs if signing info is requested.
    if (packageInfo == null) {
        val flagsWithoutGetSigningInfo = flags.andInv(
            @Suppress("DEPRECATION")
            PackageManager.GET_SIGNATURES or if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                PackageManager.GET_SIGNING_CERTIFICATES
            } else {
                0
            }
        )
        if (flags != flagsWithoutGetSigningInfo) {
            packageInfo = getPackageArchiveInfo(archiveFilePath, flagsWithoutGetSigningInfo)
                ?.apply {
                    @Suppress("DEPRECATION")
                    if (flags.hasBits(PackageManager.GET_SIGNATURES)) {
                        signatures = emptyArray()
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
                        && flags.hasBits(PackageManager.GET_SIGNING_CERTIFICATES)) {
                        signingInfo = SigningInfo()
                    }
                }
        }
    }
    return packageInfo
}
