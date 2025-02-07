/*
 * Copyright (c) 2022 Hai Zhang <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.filelist

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import java8.nio.file.Path
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.WriteWith
import org.lyi.android.files.R
import org.lyi.android.files.util.ParcelableArgs
import org.lyi.android.files.util.ParcelableParceler
import org.lyi.android.files.util.args
import org.lyi.android.files.util.putArgs
import org.lyi.android.files.util.show

class NavigateToPathDialogFragment : PathDialogFragment() {
    private val args by args<Args>()

    override val listener: Listener
        get() = super.listener as Listener

    @StringRes
    override val titleRes: Int = R.string.file_list_navigate_to_title

    override val initialName: String?
        get() = args.path.toUserFriendlyString()

    override fun onOk(path: Path) {
        listener.navigateTo(path)
    }

    companion object {
        fun show(path: Path, fragment: Fragment) {
            NavigateToPathDialogFragment().putArgs(Args(path)).show(fragment)
        }
    }

    @Parcelize
    class Args(val path: @WriteWith<ParcelableParceler> Path) : ParcelableArgs

    interface Listener : NameDialogFragment.Listener {
        fun navigateTo(path: Path)
    }
}
