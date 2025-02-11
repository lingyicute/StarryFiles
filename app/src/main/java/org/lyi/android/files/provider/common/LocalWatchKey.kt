/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.common

import java8.nio.file.Path
import java8.nio.file.WatchEvent

class LocalWatchKey(
    watchService: LocalWatchService,
    path: Path,
    @Volatile
    internal var kinds: Set<WatchEvent.Kind<*>>
) : AbstractWatchKey<LocalWatchKey, Path>(watchService, path)
