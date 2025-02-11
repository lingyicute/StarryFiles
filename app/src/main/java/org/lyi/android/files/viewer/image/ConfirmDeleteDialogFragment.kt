/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.viewer.image

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java8.nio.file.Path
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.WriteWith
import org.lyi.android.files.R
import org.lyi.android.files.util.ParcelableArgs
import org.lyi.android.files.util.ParcelableParceler
import org.lyi.android.files.util.args
import org.lyi.android.files.util.putArgs
import org.lyi.android.files.util.show

class ConfirmDeleteDialogFragment : AppCompatDialogFragment() {
    private val args by args<Args>()

    private val listener: Listener
        get() = requireParentFragment() as Listener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext(), theme)
            .setMessage(getString(R.string.image_viewer_delete_message_format, args.path.fileName))
            .setPositiveButton(android.R.string.ok) { _, _ -> listener.delete(args.path) }
            .setNegativeButton(android.R.string.cancel, null)
            .create()
    }

    companion object {
        fun show(path: Path, fragment: Fragment) {
            ConfirmDeleteDialogFragment().putArgs(Args(path)).show(fragment)
        }
    }

    @Parcelize
    class Args(val path: @WriteWith<ParcelableParceler> Path) : ParcelableArgs

    interface Listener {
        fun delete(path: Path)
    }
}
