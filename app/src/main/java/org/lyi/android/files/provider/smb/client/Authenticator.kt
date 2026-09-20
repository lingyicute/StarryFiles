/*
 * Copyright (c) 2025 lingyicute <li@92li.uk>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.smb.client

interface Authenticator {
    fun getPassword(authority: Authority): String?
}
