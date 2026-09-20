/*
 * Copyright (c) 2025 lingyicute <li@92li.uk>
 * All Rights Reserved.
 */

package org.lyi.android.files.filejob

enum class FileJobConflictAction {
    MERGE_OR_REPLACE,
    RENAME,
    SKIP,
    CANCEL,
    CANCELED
}
