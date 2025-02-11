/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.common

import java8.nio.file.DirectoryStream
import java8.nio.file.Path

class PathListDirectoryStream(
    paths: List<Path>,
    filter: DirectoryStream.Filter<in Path>
) : PathIteratorDirectoryStream(paths.iterator(), null, filter)
