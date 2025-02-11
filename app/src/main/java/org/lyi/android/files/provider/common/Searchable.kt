/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.common

import java8.nio.file.Path
import java.io.IOException

interface Searchable {
    @Throws(IOException::class)
    fun search(directory: Path, query: String, intervalMillis: Long, listener: (List<Path>) -> Unit)
}
