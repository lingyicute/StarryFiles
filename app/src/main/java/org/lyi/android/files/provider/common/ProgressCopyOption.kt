/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.common

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import java8.nio.file.CopyOption
import kotlinx.parcelize.Parcelize
import org.lyi.android.files.util.ParcelableArgs
import org.lyi.android.files.util.RemoteCallback
import org.lyi.android.files.util.getArgs
import org.lyi.android.files.util.putArgs
import org.lyi.android.files.util.readParcelable

class ProgressCopyOption(
    val intervalMillis: Long,
    val listener: (Long) -> Unit
) : CopyOption, Parcelable {
    private constructor(source: Parcel) : this(
        source.readLong(),
        source.readParcelable<RemoteCallback>()!!.let {
            { copiedSize -> it.sendResult(Bundle().putArgs(ListenerArgs(copiedSize))) }
        }
    )

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(intervalMillis)
        dest.writeParcelable(
            RemoteCallback { listener(it.getArgs<ListenerArgs>().copiedSize) }, flags
        )
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<ProgressCopyOption> {
            override fun createFromParcel(source: Parcel): ProgressCopyOption =
                ProgressCopyOption(source)

            override fun newArray(size: Int): Array<ProgressCopyOption?> = arrayOfNulls(size)
        }
    }

    @Parcelize
    private class ListenerArgs(val copiedSize: Long) : ParcelableArgs
}
