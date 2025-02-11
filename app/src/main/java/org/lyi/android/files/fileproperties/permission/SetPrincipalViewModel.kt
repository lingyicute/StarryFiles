/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.fileproperties.permission

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.lyi.android.files.util.SelectionLiveData
import org.lyi.android.files.util.Stateful
import org.lyi.android.files.util.Success
import org.lyi.android.files.util.valueCompat

abstract class SetPrincipalViewModel(
    private val principalListLiveData: MutableLiveData<Stateful<List<PrincipalItem>>>
) : ViewModel() {
    val principalListStateful: Stateful<List<PrincipalItem>>
        get() = principalListLiveData.valueCompat

    private val filterLiveData = MutableLiveData("")
    var filter: String
        get() = filterLiveData.valueCompat
        set(value) {
            if (filterLiveData.valueCompat != value) {
                filterLiveData.value = value
            }
        }

    val filteredPrincipalListLiveData: LiveData<Stateful<List<PrincipalItem>>> =
        FilteredPrincipalListLiveData(principalListLiveData, filterLiveData)

    val selectionLiveData = SelectionLiveData<Int>()

    private class FilteredPrincipalListLiveData(
        private val principalListLiveData: LiveData<Stateful<List<PrincipalItem>>>,
        private val filterLiveData: LiveData<String>
    ) : MediatorLiveData<Stateful<List<PrincipalItem>>>() {
        init {
            addSource(principalListLiveData) { loadValue() }
            addSource(filterLiveData) { loadValue() }
        }

        private fun loadValue() {
            var principalListStateful = principalListLiveData.valueCompat
            val filter = filterLiveData.valueCompat
            if (principalListStateful is Success && filter.isNotEmpty()) {
                principalListStateful = Success(
                    principalListStateful.value.filter { it.applyFilter(filter) }
                )
            }
            value = principalListStateful
        }

        private fun PrincipalItem.applyFilter(filter: String): Boolean =
            (filter in id.toString() || (name != null && filter in name)
                || applicationInfos.any { filter in it.packageName }
                || applicationLabels.any { filter in it })
    }
}
