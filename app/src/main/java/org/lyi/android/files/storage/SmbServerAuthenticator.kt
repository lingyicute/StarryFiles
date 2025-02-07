/*
 * Copyright (c) 2020 Hai Zhang <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.storage

import org.lyi.android.files.provider.smb.client.Authenticator
import org.lyi.android.files.provider.smb.client.Authority
import org.lyi.android.files.settings.Settings
import org.lyi.android.files.util.valueCompat

object SmbServerAuthenticator : Authenticator {
    private val transientServers = mutableSetOf<SmbServer>()

    override fun getPassword(authority: Authority): String? {
        val server = synchronized(transientServers) {
            transientServers.find { it.authority == authority }
        } ?: Settings.STORAGES.valueCompat.find {
            it is SmbServer && it.authority == authority
        } as SmbServer?
        return server?.password
    }

    fun addTransientServer(server: SmbServer) {
        synchronized(transientServers) { transientServers += server }
    }

    fun removeTransientServer(server: SmbServer) {
        synchronized(transientServers) { transientServers -= server }
    }
}
