/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.webdav.client

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class MemoryCookieJar : CookieJar {
    private val cookieMap = mutableMapOf<Triple<String, String, String>, Cookie>()

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        synchronized(cookieMap) {
            for (cookie in cookies) {
                cookieMap[Triple(cookie.domain, cookie.path, cookie.name)] = cookie
            }
        }
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> =
        buildList {
            synchronized(cookieMap) {
                val iterator = cookieMap.values.iterator()
                val currentTimeMillis = System.currentTimeMillis()
                while (iterator.hasNext()) {
                    val cookie = iterator.next()
                    if (cookie.expiresAt <= currentTimeMillis) {
                        iterator.remove()
                        continue
                    }
                    if (cookie.matches(url)) {
                        this += cookie
                    }
                }
            }
        }
}
