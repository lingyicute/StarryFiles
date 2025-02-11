/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.storage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.commit
import org.lyi.android.files.app.AppActivity
import org.lyi.android.files.util.args
import org.lyi.android.files.util.putArgs

class EditDeviceStorageDialogActivity : AppActivity() {
    private val args by args<EditDeviceStorageDialogFragment.Args>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Calls ensureSubDecor().
        findViewById<View>(android.R.id.content)
        if (savedInstanceState == null) {
            val fragment = EditDeviceStorageDialogFragment().putArgs(args)
            supportFragmentManager.commit {
                add(fragment, EditDeviceStorageDialogFragment::class.java.name)
            }
        }
    }
}
