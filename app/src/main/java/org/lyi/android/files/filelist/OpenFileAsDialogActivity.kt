/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.filelist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.commit
import org.lyi.android.files.app.AppActivity
import org.lyi.android.files.util.args
import org.lyi.android.files.util.putArgs

class OpenFileAsDialogActivity : AppActivity() {
    private val args by args<OpenFileAsDialogFragment.Args>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Calls ensureSubDecor().
        findViewById<View>(android.R.id.content)
        if (savedInstanceState == null) {
            val fragment = OpenFileAsDialogFragment().putArgs(args)
            supportFragmentManager.commit {
                add(fragment, OpenFileAsDialogFragment::class.java.name)
            }
        }
    }
}
