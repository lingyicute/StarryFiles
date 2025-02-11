/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.common

import java.io.Closeable

interface CloseableIterator<T> : Iterator<T>, Closeable
