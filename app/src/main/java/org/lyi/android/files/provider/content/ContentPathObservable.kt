/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.content

import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import org.lyi.android.files.provider.common.AbstractPathObservable
import org.lyi.android.files.provider.content.resolver.Resolver
import org.lyi.android.files.provider.content.resolver.ResolverException

internal class ContentPathObservable(
    uri: Uri,
    intervalMillis: Long
) : AbstractPathObservable(intervalMillis) {
    private val cursor: Cursor

    private val contentObserver = object : ContentObserver(handler) {
        override fun deliverSelfNotifications(): Boolean = true

        override fun onChange(selfChange: Boolean) {
            notifyObservers()
        }
    }

    init {
        cursor = try {
            Resolver.query(uri, emptyArray(), null, null, null)
        } catch (e: ResolverException) {
            throw e.toFileSystemException(uri.toString())
        }
        cursor.registerContentObserver(contentObserver)
    }

    override fun onCloseLocked() {
        cursor.unregisterContentObserver(contentObserver)
        cursor.close()
    }
}
