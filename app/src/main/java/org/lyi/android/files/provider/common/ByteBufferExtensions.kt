/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.common

import java.nio.ByteBuffer
import kotlin.reflect.KClass

private val EMPTY_BYTE_BUFFER = ByteBuffer.allocate(0)

val KClass<ByteBuffer>.EMPTY: ByteBuffer
    get() = EMPTY_BYTE_BUFFER
