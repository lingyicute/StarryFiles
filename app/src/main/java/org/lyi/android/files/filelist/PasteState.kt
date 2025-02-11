/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.filelist

// TODO: Make immutable?
class PasteState(
    var copy: Boolean = false,
    val files: FileItemSet = fileItemSetOf()
)
