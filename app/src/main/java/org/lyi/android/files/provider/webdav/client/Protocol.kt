/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.webdav.client

enum class Protocol(val scheme: String, val httpScheme: String, val defaultPort: Int) {
    DAV("dav", "http", 80),
    DAVS("davs", "https", 443);

    companion object {
        val SCHEMES = entries.map { it.scheme }

        fun fromScheme(scheme: String): Protocol =
            entries.firstOrNull { it.scheme == scheme } ?: throw IllegalArgumentException(scheme)
    }
}
