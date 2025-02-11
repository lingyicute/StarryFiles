/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.viewer.saveas

import android.os.Bundle
import android.os.Environment
import java8.nio.file.Path
import java8.nio.file.Paths
import org.lyi.android.files.R
import org.lyi.android.files.app.AppActivity
import org.lyi.android.files.file.MimeType
import org.lyi.android.files.file.asMimeTypeOrNull
import org.lyi.android.files.filejob.FileJobService
import org.lyi.android.files.filelist.FileListActivity
import org.lyi.android.files.util.saveAsPath
import org.lyi.android.files.util.showToast

class SaveAsActivity : AppActivity() {
    private val createFileLauncher =
        registerForActivityResult(FileListActivity.CreateFileContract(), ::onCreateFileResult)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        val mimeType = intent.type?.asMimeTypeOrNull() ?: MimeType.ANY
        val path = intent.saveAsPath
        if (path == null) {
            showToast(R.string.save_as_error)
            finish()
            return
        }
        val title = path.fileName.toString()
        val initialPath =
            Paths.get(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path
            )
        createFileLauncher.launch(Triple(mimeType, title, initialPath))
    }

    private fun onCreateFileResult(result: Path?) {
        if (result == null) {
            finish()
            return
        }
        FileJobService.save(intent.saveAsPath!!, result, this)
        finish()
    }
}
