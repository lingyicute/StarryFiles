/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.common

import java.io.IOException

abstract class PosixFileStore : AbstractFileStore() {
    @Throws(IOException::class)
    abstract fun refresh()

    @Throws(IOException::class)
    abstract fun setReadOnly(readOnly: Boolean)
}
