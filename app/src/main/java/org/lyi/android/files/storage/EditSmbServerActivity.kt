/*
 * Copyright (c) 2019 Hai Zhang <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.storage

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContract
import androidx.fragment.app.commit
import org.lyi.android.files.app.AppActivity
import org.lyi.android.files.util.args
import org.lyi.android.files.util.createIntent
import org.lyi.android.files.util.putArgs

class EditSmbServerActivity : AppActivity() {
    private val args by args<EditSmbServerFragment.Args>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Calls ensureSubDecor().
        findViewById<View>(android.R.id.content)
        if (savedInstanceState == null) {
            val fragment = EditSmbServerFragment().putArgs(args)
            supportFragmentManager.commit { add(android.R.id.content, fragment) }
        }
    }

    class Contract : ActivityResultContract<EditSmbServerFragment.Args, Boolean>() {
        override fun createIntent(context: Context, input: EditSmbServerFragment.Args): Intent =
            EditSmbServerActivity::class.createIntent().putArgs(input)

        override fun parseResult(resultCode: Int, intent: Intent?): Boolean =
            resultCode == Activity.RESULT_OK
    }
}
