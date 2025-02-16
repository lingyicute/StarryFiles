/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.util

import android.location.Address

val Address.addressLines: Iterable<String?>
    get() =
        object : Iterable<String?> {
            override fun iterator(): Iterator<String?> = object : Iterator<String?> {
                var index = 0

                override fun hasNext(): Boolean = index <= maxAddressLineIndex

                override fun next(): String? = getAddressLine(index).also { ++index }
            }
        }

// @see com.android.documentsui.inspector.MediaView.getAddress
val Address.userFriendlyString: String?
    get() =
        addressLines.joinToString("\n") { it.orEmpty() }.takeIfNotBlank()
            ?: locality.takeIfNotBlank() ?: subAdminArea.takeIfNotBlank()
            ?: adminArea.takeIfNotBlank() ?: countryName.takeIfNotBlank()
