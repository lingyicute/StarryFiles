/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.ftpserver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.lyi.android.files.app.application

class FtpServerReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (val action = intent.action) {
            ACTION_STOP -> FtpServerService.stop(context)
            else -> throw IllegalArgumentException(action)
        }
    }

    companion object {
        const val ACTION_STOP = "stop"

        fun createIntent(): Intent =
            Intent(application, FtpServerReceiver::class.java)
                .setAction(ACTION_STOP)
    }
}
