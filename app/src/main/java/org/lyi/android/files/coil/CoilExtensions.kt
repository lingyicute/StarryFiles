/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.coil

import coil.request.ImageRequest
import coil.transition.CrossfadeTransition

fun ImageRequest.Builder.fadeIn(durationMillis: Int): ImageRequest.Builder =
    apply {
        placeholder(android.R.color.transparent)
        transitionFactory(CrossfadeTransition.Factory(durationMillis, true))
    }
