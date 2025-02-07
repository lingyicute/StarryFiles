/*
 * Copyright (c) 2019 Hai Zhang <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.filelist

import android.content.Intent
import android.os.Bundle
import java8.nio.file.Path
import org.lyi.android.files.app.AppActivity
import org.lyi.android.files.app.application
import org.lyi.android.files.file.MimeType
import org.lyi.android.files.file.asMimeTypeOrNull
import org.lyi.android.files.file.fileProviderUri
import org.lyi.android.files.filejob.FileJobService
import org.lyi.android.files.provider.archive.isArchivePath
import org.lyi.android.files.util.createViewIntent
import org.lyi.android.files.util.extraPath
import org.lyi.android.files.util.startActivitySafe

class OpenFileActivity : AppActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        val path = intent.extraPath
        val mimeType = intent.type?.asMimeTypeOrNull()
        if (path != null && mimeType != null) {
            openFile(path, mimeType)
        }
        finish()
    }

    private fun openFile(path: Path, mimeType: MimeType) {
        if (path.isArchivePath) {
            FileJobService.open(path, mimeType, false, this)
        } else {
            val intent = path.fileProviderUri.createViewIntent(mimeType)
                .addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                .apply { extraPath = path }
            startActivitySafe(intent)
        }
    }

    companion object {
        private const val ACTION_OPEN_FILE = "org.lyi.android.files.intent.action.OPEN_FILE"

        fun createIntent(path: Path, mimeType: MimeType): Intent =
            Intent(ACTION_OPEN_FILE)
                .setPackage(application.packageName)
                .setType(mimeType.value)
                .apply { extraPath = path }
    }
}
