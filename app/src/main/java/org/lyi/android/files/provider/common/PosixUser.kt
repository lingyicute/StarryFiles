/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.common

import android.os.Parcel
import android.os.Parcelable
import java8.nio.file.attribute.UserPrincipal

class PosixUser : PosixPrincipal, UserPrincipal {
    constructor(id: Int, name: ByteString?) : super(id, name)

    private constructor(source: Parcel) : super(source)

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<PosixUser> {
            override fun createFromParcel(source: Parcel): PosixUser = PosixUser(source)

            override fun newArray(size: Int): Array<PosixUser?> = arrayOfNulls(size)
        }
    }
}
