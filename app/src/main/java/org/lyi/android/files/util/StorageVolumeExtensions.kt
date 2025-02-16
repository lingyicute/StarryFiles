package org.lyi.android.files.util

import android.os.storage.StorageVolume
import org.lyi.android.files.compat.directoryCompat

val StorageVolume.isMounted: Boolean
    get() = directoryCompat != null
