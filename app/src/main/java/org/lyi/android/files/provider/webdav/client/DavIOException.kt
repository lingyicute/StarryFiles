/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.webdav.client

import at.bitfire.dav4jvm.exception.DavException
import java.io.IOException

class DavIOException(cause: IOException) : DavException(cause.message ?: "", cause) {
    override val cause: Throwable
        get() = super.cause!!
}

fun IOException.toDavException(): DavIOException = DavIOException(this)
