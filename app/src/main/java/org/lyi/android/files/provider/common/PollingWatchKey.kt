/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.common

import java8.nio.file.Path

class PollingWatchKey(
    watchService: PollingWatchService,
    path: Path
) : AbstractWatchKey<PollingWatchKey, Path>(watchService, path)
