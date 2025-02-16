/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */
package org.lyi.android.files.ui

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.preference.ListPreference
import kotlinx.parcelize.Parcelize
import org.lyi.android.files.util.ParcelableState
import org.lyi.android.files.util.getState
import org.lyi.android.files.util.putState

class MaterialListPreferenceDialogFragmentCompat : MaterialPreferenceDialogFragmentCompat() {
    override val preference: ListPreference
        get() = super.preference as ListPreference

    private lateinit var entries: Array<CharSequence>
    private lateinit var entryValues: Array<CharSequence>

    private var checkedEntryIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            val preference = preference
            entries = preference.entries
            entryValues = preference.entryValues
            checkedEntryIndex = preference.findIndexOfValue(preference.value)
        } else {
            val state = savedInstanceState.getState<State>()
            entries = state.entries
            entryValues = state.entryValues
            checkedEntryIndex = state.checkedEntryIndex
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putState(State(entries, entryValues, checkedEntryIndex))
    }

    override fun onPrepareDialogBuilder(builder: AlertDialog.Builder) {
        super.onPrepareDialogBuilder(builder)

        builder.setSingleChoiceItems(entries, checkedEntryIndex) { dialog, which ->
            checkedEntryIndex = which
            onClick(dialog, DialogInterface.BUTTON_POSITIVE)
            dialog.dismiss()
        }
        builder.setPositiveButton(null, null)
    }

    override fun onDialogClosed(positiveResult: Boolean) {
        if (positiveResult && checkedEntryIndex >= 0) {
            val value = entryValues[checkedEntryIndex].toString()
            val preference = preference
            if (preference.callChangeListener(value)) {
                preference.value = value
            }
        }
    }

    @Parcelize
    private class State(
        val entries: Array<CharSequence>,
        val entryValues: Array<CharSequence>,
        val checkedEntryIndex: Int
    ) : ParcelableState
}
