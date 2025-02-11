/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.storage

import java.net.InetAddress

data class LanSmbServer(
    val host: String,
    val address: InetAddress
) : Comparable<LanSmbServer> {
    override fun compareTo(other: LanSmbServer): Int =
        compareValuesBy(this, other, { it.address.hostAddress }, { it.host })
}
