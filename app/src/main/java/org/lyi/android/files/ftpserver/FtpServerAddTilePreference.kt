/*
 * Copyright (c) 2023 Hai Zhang <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.ftpserver

import android.app.StatusBarManager
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.AttrRes
import androidx.annotation.RequiresApi
import androidx.annotation.StyleRes
import androidx.preference.Preference
import org.lyi.android.files.R
import org.lyi.android.files.compat.getSystemServiceCompat
import org.lyi.android.files.compat.mainExecutorCompat
import org.lyi.android.files.util.requestAddTileService
import org.lyi.android.files.util.showToast

class FtpServerAddTilePreference : Preference {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    )

    constructor(
        context: Context,
        attrs: AttributeSet?,
        @AttrRes defStyleAttr: Int,
        @StyleRes defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        isPersistent = false
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onClick() {
        val statusBarManager = context.getSystemServiceCompat(StatusBarManager::class.java)
        statusBarManager.requestAddTileService(
            FtpServerTileService::class.java, context.mainExecutorCompat
        ) { result ->
            val resultRes = when (result) {
                StatusBarManager.TILE_ADD_REQUEST_RESULT_TILE_NOT_ADDED ->
                    return@requestAddTileService
                StatusBarManager.TILE_ADD_REQUEST_RESULT_TILE_ALREADY_ADDED ->
                    R.string.ftp_server_add_tile_result_already_added
                StatusBarManager.TILE_ADD_REQUEST_RESULT_TILE_ADDED ->
                    R.string.ftp_server_add_tile_result_added
                else -> R.string.ftp_server_add_tile_result_error
            }
            context.showToast(resultRes)
        }
    }
}
