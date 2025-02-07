/*
 * Copyright (c) 2023 Hai Zhang <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.storage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.commit
import org.lyi.android.files.app.AppActivity
import org.lyi.android.files.util.args
import org.lyi.android.files.util.putArgs

class AddExternalStorageShortcutActivity : AppActivity() {
    private val args by args<AddExternalStorageShortcutFragment.Args>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Calls ensureSubDecor().
        findViewById<View>(android.R.id.content)
        if (savedInstanceState == null) {
            val fragment = AddExternalStorageShortcutFragment().putArgs(args)
            supportFragmentManager.commit {
                add(fragment, AddExternalStorageShortcutFragment::class.java.name)
            }
        }
    }
}
