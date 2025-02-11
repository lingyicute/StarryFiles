/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.coil

import android.graphics.drawable.Drawable
import coil.request.ImageRequest
import coil.target.ImageViewTarget

// Setting the placeholder drawable as error drawable again causes animation glitches, so we just
// ignore the onError() callback.
fun ImageRequest.Builder.ignoreError() {
    val view = (build().target as ImageViewTarget).view
    target(object : ImageViewTarget(view) {
        override fun onError(error: Drawable?) {}
    })
}
