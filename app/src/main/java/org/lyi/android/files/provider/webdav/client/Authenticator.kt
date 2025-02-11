/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.webdav.client

interface Authenticator {
    fun getAuthentication(authority: Authority): Authentication?
}
