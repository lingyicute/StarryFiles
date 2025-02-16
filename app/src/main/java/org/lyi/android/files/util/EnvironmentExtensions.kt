/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.util

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.annotation.RequiresApi
import org.lyi.android.files.app.application
import org.lyi.android.files.app.packageManager
import kotlin.reflect.KClass

// TvSettings didn't have "All files access" page until Android 13.
@ChecksSdkIntAtLeast(Build.VERSION_CODES.R)
fun KClass<Environment>.supportsExternalStorageManager(): Boolean =
    when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> true
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.R ->
            isManageAppAllFilesAccessPermissionIntentResolved
        else -> false
    }

@RequiresApi(Build.VERSION_CODES.R)
fun KClass<Environment>.createManageAppAllFilesAccessPermissionIntent(packageName: String): Intent =
    Intent(
        Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
        Uri.fromParts("package", packageName, null)
    )

@delegate:RequiresApi(Build.VERSION_CODES.R)
private val isManageAppAllFilesAccessPermissionIntentResolved: Boolean
    by lazy(LazyThreadSafetyMode.NONE) {
        Environment::class.createManageAppAllFilesAccessPermissionIntent(application.packageName)
            .resolveActivity(packageManager) != null
    }
