/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.linux

import android.system.OsConstants
import org.lyi.android.files.provider.common.OpenOptions
import org.lyi.android.files.provider.linux.syscall.Constants

internal fun OpenOptions.toLinuxFlags(): Int {
    var flags = if (read && write) {
        OsConstants.O_RDWR
    } else if (write) {
        OsConstants.O_WRONLY
    } else {
        OsConstants.O_RDONLY
    }
    if (append) {
        flags = flags or OsConstants.O_APPEND
    }
    if (truncateExisting) {
        flags = flags or OsConstants.O_TRUNC
    }
    if (createNew) {
        flags = flags or OsConstants.O_CREAT or OsConstants.O_EXCL
    } else if (create) {
        flags = flags or OsConstants.O_CREAT
    }
    if (sync) {
        flags = flags or OsConstants.O_SYNC
    }
    if (dsync) {
        flags = flags or Constants.O_DSYNC
    }
    if (noFollowLinks || (!createNew && deleteOnClose)) {
        flags = flags or OsConstants.O_NOFOLLOW
    }
    return flags
}
