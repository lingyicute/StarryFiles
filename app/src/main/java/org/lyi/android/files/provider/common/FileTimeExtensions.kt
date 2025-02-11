/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.common

import java.time.Instant
import java8.nio.file.attribute.FileTime
import kotlin.reflect.KClass

val KClass<FileTime>.EPOCH: FileTime
    get() = FileTime.from(Instant.EPOCH)
