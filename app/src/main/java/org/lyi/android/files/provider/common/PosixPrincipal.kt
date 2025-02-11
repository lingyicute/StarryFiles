/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.common

import android.os.Parcel
import android.os.Parcelable
import org.lyi.android.files.util.hash
import org.lyi.android.files.util.readParcelable
import java.security.Principal

abstract class PosixPrincipal(val id: Int, name: ByteString?) : Parcelable, Principal {
    private val nameByteString = name

    override fun getName(): String? = nameByteString?.toString()

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (javaClass != other?.javaClass) {
            return false
        }
        other as PosixPrincipal
        return id == other.id && nameByteString == other.nameByteString
    }

    override fun hashCode(): Int = hash(id, nameByteString)

    protected constructor(source: Parcel) : this(source.readInt(), source.readParcelable())

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeParcelable(nameByteString, flags)
    }
}
