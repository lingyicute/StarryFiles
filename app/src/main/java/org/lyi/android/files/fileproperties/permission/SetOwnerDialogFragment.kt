/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.fileproperties.permission

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import java8.nio.file.Path
import org.lyi.android.files.R
import org.lyi.android.files.file.FileItem
import org.lyi.android.files.filejob.FileJobService
import org.lyi.android.files.provider.common.PosixFileAttributes
import org.lyi.android.files.provider.common.PosixPrincipal
import org.lyi.android.files.provider.common.PosixUser
import org.lyi.android.files.provider.common.toByteString
import org.lyi.android.files.util.SelectionLiveData
import org.lyi.android.files.util.putArgs
import org.lyi.android.files.util.show
import org.lyi.android.files.util.viewModels

class SetOwnerDialogFragment : SetPrincipalDialogFragment() {
    override val viewModel: SetPrincipalViewModel by viewModels { { SetOwnerViewModel() } }

    @StringRes
    override val titleRes: Int = R.string.file_properties_permission_set_owner_title

    override fun createAdapter(selectionLiveData: SelectionLiveData<Int>): PrincipalListAdapter =
        UserListAdapter(selectionLiveData)

    override val PosixFileAttributes.principal: PosixPrincipal
        get() = owner()!!

    override fun setPrincipal(path: Path, principal: PrincipalItem, recursive: Boolean) {
        val owner = PosixUser(principal.id, principal.name?.toByteString())
        FileJobService.setOwner(path, owner, recursive, requireContext())
    }

    companion object {
        fun show(file: FileItem, fragment: Fragment) {
            SetOwnerDialogFragment().putArgs(Args(file)).show(fragment)
        }
    }
}
