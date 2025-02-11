/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.common

import java8.nio.file.FileSystem

interface ByteStringListPathCreator {
    fun getPath(first: ByteString, vararg more: ByteString): ByteStringListPath<*>
}

fun FileSystem.getPath(first: ByteString, vararg more: ByteString): ByteStringListPath<*> =
    (this as ByteStringListPathCreator).getPath(first, *more)
