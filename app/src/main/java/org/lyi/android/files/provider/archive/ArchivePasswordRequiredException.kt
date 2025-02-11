/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.archive

import android.content.Context
import java8.nio.file.Path
import org.lyi.android.files.fileaction.ArchivePasswordDialogActivity
import org.lyi.android.files.fileaction.ArchivePasswordDialogFragment
import org.lyi.android.files.provider.common.UserAction
import org.lyi.android.files.provider.common.UserActionRequiredException
import org.lyi.android.files.util.createIntent
import org.lyi.android.files.util.putArgs
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

class ArchivePasswordRequiredException(
    private val file: Path,
    reason: String?
) :
    UserActionRequiredException(file.toString(), null, reason) {

    override fun getUserAction(continuation: Continuation<Boolean>, context: Context): UserAction {
        return UserAction(
            ArchivePasswordDialogActivity::class.createIntent().putArgs(
                ArchivePasswordDialogFragment.Args(file) { continuation.resume(it) }
            ), ArchivePasswordDialogFragment.getTitle(context),
            ArchivePasswordDialogFragment.getMessage(file, context)
        )
    }
}
