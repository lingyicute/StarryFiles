/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.fileaction

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.SparseArray
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.widget.NestedScrollView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java8.nio.file.Path
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.WriteWith
import org.lyi.android.files.R
import org.lyi.android.files.compat.requireViewByIdCompat
import org.lyi.android.files.databinding.ArchivePasswordDialogBinding
import org.lyi.android.files.provider.archive.archiveAddPassword
import org.lyi.android.files.provider.archive.archiveFile
import org.lyi.android.files.ui.AllowSoftInputHackAlertDialogCustomView
import org.lyi.android.files.util.ParcelableArgs
import org.lyi.android.files.util.ParcelableParceler
import org.lyi.android.files.util.ParcelableState
import org.lyi.android.files.util.RemoteCallback
import org.lyi.android.files.util.args
import org.lyi.android.files.util.finish
import org.lyi.android.files.util.getArgs
import org.lyi.android.files.util.getState
import org.lyi.android.files.util.hideTextInputLayoutErrorOnTextChange
import org.lyi.android.files.util.layoutInflater
import org.lyi.android.files.util.putArgs
import org.lyi.android.files.util.putState
import org.lyi.android.files.util.readParcelable
import org.lyi.android.files.util.setOnEditorConfirmActionListener

class ArchivePasswordDialogFragment : AppCompatDialogFragment() {
    private val args by args<Args>()

    private lateinit var binding: ArchivePasswordDialogBinding

    private var isListenerNotified = false

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val hierarchyState = SparseArray<Parcelable>()
            .apply { binding.root.saveHierarchyState(this) }
        outState.putState(State(hierarchyState))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = requireContext()
        return MaterialAlertDialogBuilder(requireContext(), theme)
            .setTitle(getTitle(context))
            .setMessage(getMessage(args.path.archiveFile.fileName, context))
            .apply {
                binding = ArchivePasswordDialogBinding.inflate(context.layoutInflater)
                binding.passwordEdit.hideTextInputLayoutErrorOnTextChange(binding.passwordLayout)
                binding.passwordEdit.setOnEditorConfirmActionListener { onOk() }
                if (savedInstanceState != null) {
                    val state = savedInstanceState.getState<State>()
                    binding.root.restoreHierarchyState(state.hierarchyState)
                }
                setView(AllowSoftInputHackAlertDialogCustomView(context))
            }
            .setPositiveButton(android.R.string.ok, null)
            .setNegativeButton(android.R.string.cancel) { _, _ -> finish() }
            .create()
            .apply {
                setCanceledOnTouchOutside(false)
                // Override the listener here so that we have control over when to close the dialog.
                setOnShowListener {
                    getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener { onOk() }
                }
                window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            }
    }

    override fun onStart() {
        super.onStart()

        val dialog = requireDialog() as AlertDialog
        if (binding.root.parent == null) {
            val scrollView = dialog.requireViewByIdCompat<NestedScrollView>(R.id.scrollView)
            val linearLayout = scrollView.getChildAt(0) as LinearLayout
            linearLayout.addView(binding.root)
            binding.passwordEdit.requestFocus()
        }
    }

    private fun onOk() {
        val password = binding.passwordEdit.text!!.toString()
        if (password.isEmpty()) {
            binding.passwordLayout.error =
                getString(R.string.file_action_archive_password_error_empty)
            return
        }
        args.path.archiveAddPassword(password)
        notifyListenerOnce(true)
        finish()
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)

        notifyListenerOnce(false)
        finish()
    }

    fun onFinish() {
        notifyListenerOnce(false)
    }

    private fun notifyListenerOnce(successful: Boolean) {
        if (isListenerNotified) {
            return
        }
        args.listener(successful)
        isListenerNotified = true
    }

    companion object {
        fun getTitle(context: Context): String =
            context.getString(R.string.file_action_archive_password_title)

        fun getMessage(archiveFile: Path, context: Context): String =
            context.getString(
                R.string.file_action_archive_password_message_format, archiveFile.fileName
            )
    }

    @Parcelize
    class Args(
        val path: @WriteWith<ParcelableParceler> Path,
        val listener: @WriteWith<ListenerParceler>()
        (Boolean) -> Unit
    ) : ParcelableArgs {
        object ListenerParceler : Parceler<(Boolean) -> Unit> {
            override fun create(parcel: Parcel): (Boolean) -> Unit =
                parcel.readParcelable<RemoteCallback>()!!.let {
                    { successful ->
                        it.sendResult(Bundle().putArgs(ListenerArgs(successful)))
                    }
                }

            override fun ((Boolean) -> Unit).write(parcel: Parcel, flags: Int) {
                parcel.writeParcelable(
                    RemoteCallback {
                        val args = it.getArgs<ListenerArgs>()
                        this(args.successful)
                    }, flags
                )
            }

            @Parcelize
            private class ListenerArgs(
                val successful: Boolean
            ) : ParcelableArgs
        }
    }

    @Parcelize
    private class State(
        val hierarchyState: SparseArray<Parcelable>
    ) : ParcelableState
}
