/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.viewer.text

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.lyi.android.files.R
import org.lyi.android.files.util.show

class ConfirmReloadDialogFragment : AppCompatDialogFragment() {
    private val listener: Listener
        get() = requireParentFragment() as Listener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext(), theme)
            .setMessage(R.string.text_editor_reload_message)
            .setPositiveButton(R.string.keep_editing, null)
            .setNegativeButton(R.string.reload) { _, _ -> listener.reload() }
            .create()
    }

    companion object {
        fun show(fragment: Fragment) {
            ConfirmReloadDialogFragment().show(fragment)
        }
    }

    interface Listener {
        fun reload()
    }
}
