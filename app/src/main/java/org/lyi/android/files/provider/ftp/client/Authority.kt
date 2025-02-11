/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.ftp.client

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.lyi.android.files.provider.common.UriAuthority
import org.lyi.android.files.util.takeIfNotEmpty
import java.nio.charset.StandardCharsets

@Parcelize
data class Authority(
    val protocol: Protocol,
    val host: String,
    val port: Int,
    val username: String,
    val mode: Mode,
    val encoding: String
) : Parcelable {
    fun toUriAuthority(): UriAuthority {
        val userInfo = username.takeIfNotEmpty()
        val uriPort = port.takeIf { it != protocol.defaultPort }
        return UriAuthority(userInfo, host, uriPort)
    }

    override fun toString(): String = toUriAuthority().toString()

    companion object {
        // @see https://www.rfc-editor.org/rfc/rfc1635
        const val ANONYMOUS_USERNAME = "anonymous"
        const val ANONYMOUS_PASSWORD = "guest"
        val DEFAULT_MODE = Mode.PASSIVE
        val DEFAULT_ENCODING = StandardCharsets.UTF_8.name()!!
    }
}
