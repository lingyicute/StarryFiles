/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.navigation

import org.lyi.android.files.settings.Settings
import org.lyi.android.files.util.removeFirst
import org.lyi.android.files.util.valueCompat

object BookmarkDirectories {
    fun add(bookmarkDirectory: BookmarkDirectory) {
        val bookmarkDirectories = Settings.BOOKMARK_DIRECTORIES.valueCompat.toMutableList()
            .apply { add(bookmarkDirectory) }
        Settings.BOOKMARK_DIRECTORIES.putValue(bookmarkDirectories)
    }

    fun move(fromPosition: Int, toPosition: Int) {
        val bookmarkDirectories = Settings.BOOKMARK_DIRECTORIES.valueCompat.toMutableList()
            .apply { add(toPosition, removeAt(fromPosition)) }
        Settings.BOOKMARK_DIRECTORIES.putValue(bookmarkDirectories)
    }

    fun replace(bookmarkDirectory: BookmarkDirectory) {
        val bookmarkDirectories = Settings.BOOKMARK_DIRECTORIES.valueCompat.toMutableList()
            .apply { this[indexOfFirst { it.id == bookmarkDirectory.id }] = bookmarkDirectory }
        Settings.BOOKMARK_DIRECTORIES.putValue(bookmarkDirectories)
    }

    fun remove(bookmarkDirectory: BookmarkDirectory) {
        val bookmarkDirectories = Settings.BOOKMARK_DIRECTORIES.valueCompat.toMutableList()
            .apply { removeFirst { it.id == bookmarkDirectory.id } }
        Settings.BOOKMARK_DIRECTORIES.putValue(bookmarkDirectories)
    }
}
