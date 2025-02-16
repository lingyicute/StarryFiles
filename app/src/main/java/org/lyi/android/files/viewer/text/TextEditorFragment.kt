/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.viewer.text

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.SubMenu
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import java8.nio.file.Path
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import org.lyi.android.files.R
import org.lyi.android.files.databinding.TextEditorFragmentBinding
import org.lyi.android.files.ui.ThemedFastScroller
import org.lyi.android.files.util.ActionState
import org.lyi.android.files.util.DataState
import org.lyi.android.files.util.ParcelableArgs
import org.lyi.android.files.util.addOnBackPressedCallback
import org.lyi.android.files.util.args
import org.lyi.android.files.util.extraPath
import org.lyi.android.files.util.fadeInUnsafe
import org.lyi.android.files.util.fadeOutUnsafe
import org.lyi.android.files.util.isReady
import org.lyi.android.files.util.showToast
import org.lyi.android.files.util.viewModels
import java.nio.charset.Charset

class TextEditorFragment : Fragment(), ConfirmReloadDialogFragment.Listener,
    ConfirmCloseDialogFragment.Listener {
    private val args by args<Args>()
    private lateinit var argsFile: Path

    private lateinit var binding: TextEditorFragmentBinding

    private lateinit var menuBinding: MenuBinding

    private val viewModel by viewModels { { TextEditorViewModel(argsFile) } }

    private lateinit var onBackPressedCallback: OnBackPressedCallback

    private var isSettingText = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        lifecycleScope.launchWhenStarted {
            onBackPressedCallback = object : OnBackPressedCallback(false) {
                override fun handleOnBackPressed() {
                    ConfirmCloseDialogFragment.show(this@TextEditorFragment)
                }
            }
            launch {
                viewModel.isTextChanged.collect {
                    onBackPressedCallback.isEnabled = viewModel.isTextChanged.value
                }
            }
            addOnBackPressedCallback(onBackPressedCallback)

            launch { viewModel.encoding.collect { onEncodingChanged(it) } }
            launch { viewModel.textState.collect { onTextStateChanged(it) } }
            launch { viewModel.isTextChanged.collect { onIsTextChangedChanged(it) } }
            launch { viewModel.writeFileState.collect { onWriteFileStateChanged(it) } }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        TextEditorFragmentBinding.inflate(inflater, container, false)
            .also { binding = it }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val argsFile = args.intent.extraPath
        if (argsFile == null) {
            // TODO: Show a toast.
            finish()
            return
        }
        this.argsFile = argsFile

        val activity = requireActivity() as AppCompatActivity
        activity.lifecycleScope.launchWhenCreated {
            activity.setSupportActionBar(binding.toolbar)
            activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        // TODO: Move reload-prevent here so that we can also handle save-as, etc. Or maybe just get
        //  rid of the mPathLiveData in TextEditorViewModel.
        ThemedFastScroller.create(binding.scrollView)
        // Manually save and restore state in view model to avoid TransactionTooLargeException.
        binding.textEdit.isSaveEnabled = false
        val textEditSavedState = viewModel.removeEditTextSavedState()
        if (textEditSavedState != null) {
            binding.textEdit.onRestoreInstanceState(textEditSavedState)
        }
        binding.textEdit.doAfterTextChanged {
            if (isSettingText) {
                return@doAfterTextChanged
            }
            // Might happen if the animation is running and user is quick enough.
            if (viewModel.textState.value !is DataState.Success) {
                return@doAfterTextChanged
            }
            viewModel.isTextChanged.value = true
        }

        // TODO: Request storage permission if not granted.
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        viewModel.setEditTextSavedState(binding.textEdit.onSaveInstanceState())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        menuBinding = MenuBinding.inflate(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)

        updateSaveMenuItem()
        updateEncodingMenuItems()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.action_save -> {
                save()
                true
            }
            R.id.action_reload -> {
                onReload()
                true
            }
            Menu.FIRST -> {
                viewModel.encoding.value = Charset.forName(item.titleCondensed!!.toString())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    fun onSupportNavigateUp(): Boolean {
        if (onBackPressedCallback.isEnabled) {
            onBackPressedCallback.handleOnBackPressed()
            return true
        }
        return false
    }

    override fun finish() {
        requireActivity().finish()
    }

    private fun onEncodingChanged(encoding: Charset) {
        updateEncodingMenuItems()
    }

    private fun updateEncodingMenuItems() {
        if (!this::menuBinding.isInitialized) {
            return
        }
        val charsetName = viewModel.encoding.value.name()
        val charsetItem = menuBinding.encodingSubMenu.children
            .find { it.titleCondensed == charsetName }!!
        charsetItem.isChecked = true
    }

    private fun onTextStateChanged(state: DataState<String>) {
        updateTitle()
        when (state) {
            is DataState.Loading -> {
                binding.progress.fadeInUnsafe()
                binding.errorText.fadeOutUnsafe()
                binding.textEdit.fadeOutUnsafe()
            }
            is DataState.Success -> {
                binding.progress.fadeOutUnsafe()
                binding.errorText.fadeOutUnsafe()
                binding.textEdit.fadeInUnsafe()
                if (!viewModel.isTextChanged.value) {
                    setText(state.data)
                }
            }
            is DataState.Error -> {
                state.throwable.printStackTrace()
                binding.progress.fadeOutUnsafe()
                binding.errorText.fadeInUnsafe()
                binding.errorText.text = state.throwable.toString()
                binding.textEdit.fadeOutUnsafe()
            }
        }
    }

    private fun setText(text: String?) {
        isSettingText = true
        binding.textEdit.setText(text)
        isSettingText = false
        viewModel.isTextChanged.value = false
    }

    private fun onIsTextChangedChanged(changed: Boolean) {
        updateTitle()
    }

    private fun updateTitle() {
        val fileName = viewModel.file.value.fileName.toString()
        val changed = viewModel.isTextChanged.value
        requireActivity().title = getString(
            if (changed) {
                R.string.text_editor_title_changed_format
            } else {
                R.string.text_editor_title_format
            }, fileName
        )
    }

    private fun onReload() {
        if (viewModel.isTextChanged.value) {
            ConfirmReloadDialogFragment.show(this)
        } else {
            reload()
        }
    }

    override fun reload() {
        viewModel.isTextChanged.value = false
        viewModel.reload()
    }

    private fun save() {
        val text = binding.textEdit.text.toString()
        viewModel.writeFile(argsFile, text, requireContext())
    }

    private fun onWriteFileStateChanged(state: ActionState<Pair<Path, String>, Unit>) {
        when (state) {
            is ActionState.Ready, is ActionState.Running -> updateSaveMenuItem()
            is ActionState.Success -> {
                showToast(R.string.text_editor_save_success)
                viewModel.finishWritingFile()
                viewModel.isTextChanged.value = false
            }
            // The error will be toasted by service so we should never show it in UI.
            is ActionState.Error -> viewModel.finishWritingFile()
        }
    }

    private fun updateSaveMenuItem() {
        if (!this::menuBinding.isInitialized) {
            return
        }
        menuBinding.saveItem.isEnabled = viewModel.writeFileState.value.isReady
    }

    @Parcelize
    class Args(val intent: Intent) : ParcelableArgs

    private class MenuBinding private constructor(
        val menu: Menu,
        val saveItem: MenuItem,
        val encodingSubMenu: SubMenu
    ) {
        companion object {
            fun inflate(menu: Menu, inflater: MenuInflater): MenuBinding {
                inflater.inflate(R.menu.text_editor, menu)
                val encodingSubMenu = menu.findItem(R.id.action_encoding).subMenu!!
                for ((charsetName, charset) in Charset.availableCharsets()) {
                    // HACK: Use titleCondensed to store charset name.
                    encodingSubMenu.add(Menu.NONE, Menu.FIRST, Menu.NONE, charset.displayName())
                        .titleCondensed = charsetName
                }
                encodingSubMenu.setGroupCheckable(Menu.NONE, true, true)
                return MenuBinding(menu, menu.findItem(R.id.action_save), encodingSubMenu)
            }
        }
    }
}
