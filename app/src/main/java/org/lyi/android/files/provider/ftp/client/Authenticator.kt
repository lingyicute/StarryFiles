/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.ftp.client

interface Authenticator {
    fun getPassword(authority: Authority): String?
}
