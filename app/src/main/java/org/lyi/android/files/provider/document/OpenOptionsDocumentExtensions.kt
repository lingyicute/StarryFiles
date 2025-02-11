/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.document

import java8.nio.file.StandardOpenOption
import org.lyi.android.files.provider.common.OpenOptions

internal fun OpenOptions.toDocumentMode(): String =
    StringBuilder().apply {
        if (read && write) {
            append("rw")
        } else if (write) {
            append('w')
        } else {
            append('r')
        }
        if (append) {
            append('a')
        }
        if (truncateExisting) {
            append('t')
        }
        if (create || createNew) {
            throw AssertionError(
                "${StandardOpenOption.CREATE} and ${StandardOpenOption.CREATE_NEW
                } should have been handled before calling OpenOptions.toDocumentMode()"
            )
        }
        if (deleteOnClose) {
            throw UnsupportedOperationException(StandardOpenOption.DELETE_ON_CLOSE.toString())
        }
        if (sync) {
            throw UnsupportedOperationException(StandardOpenOption.SYNC.toString())
        }
        if (dsync) {
            throw UnsupportedOperationException(StandardOpenOption.DSYNC.toString())
        }
    }.toString()
