/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.common

import java8.nio.file.FileSystemException

class FileStoreNotFoundException(file: String?) : FileSystemException(file)
