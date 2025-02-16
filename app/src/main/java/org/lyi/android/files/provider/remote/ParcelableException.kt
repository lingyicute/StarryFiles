/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.remote

import android.os.Parcel
import android.os.Parcelable
import org.lyi.android.files.compat.readSerializableCompat
import java.io.IOException

class ParcelableException() : Parcelable {
    var value: Exception? = null
        set(value) {
            check((value == null) or (value is IOException) or (value is RuntimeException)) {
                "$value is not an IOException or a RuntimeException"
            }
            check(field == null) { "Exception is already set" }
            field = value
        }

    private constructor(source: Parcel) : this() {
        value = source.readSerializableCompat()
    }

    override fun describeContents(): Int = 0

    fun readFromParcel(source: Parcel) {
        value = source.readSerializableCompat()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeSerializable(value)
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<ParcelableException> {
            override fun createFromParcel(source: Parcel): ParcelableException =
                ParcelableException(source)

            override fun newArray(size: Int): Array<ParcelableException?> = arrayOfNulls(size)
        }
    }
}
