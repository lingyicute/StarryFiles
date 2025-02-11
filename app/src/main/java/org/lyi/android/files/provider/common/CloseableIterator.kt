/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.common

import java.io.Closeable

interface CloseableIterator<T> : Iterator<T>, Closeable
