/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.common

import android.os.Parcel
import android.os.Parcelable
import org.lyi.android.files.compat.readSerializableCompat
import org.lyi.android.files.util.toEnumSet
import java.io.Serializable

class ParcelablePosixFileMode(val value: Set<PosixFileModeBit>) : Parcelable {
    private constructor(source: Parcel) : this(
        source.readSerializableCompat<Set<PosixFileModeBit>>()!!
    )

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        val serializable = when (value) {
            is Serializable -> value
            else -> value.toEnumSet()
        }
        dest.writeSerializable(serializable)
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<ParcelablePosixFileMode> {
            override fun createFromParcel(source: Parcel): ParcelablePosixFileMode =
                ParcelablePosixFileMode(source)

            override fun newArray(size: Int): Array<ParcelablePosixFileMode?> = arrayOfNulls(size)
        }
    }
}

fun Set<PosixFileModeBit>.toParcelable(): ParcelablePosixFileMode = ParcelablePosixFileMode(this)
