/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.ui

import android.view.ViewGroup
import me.zhanghai.android.fastscroll.FastScroller
import me.zhanghai.android.fastscroll.FastScrollerBuilder

object ThemedFastScroller {
    fun create(view: ViewGroup): FastScroller = FastScrollerBuilder(view).useMd2Style().build()
}
