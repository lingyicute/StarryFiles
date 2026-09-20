/*
 * Copyright (c) 2025 lingyicute <li@92li.uk>
 * All Rights Reserved.
 */

package org.lyi.android.files.file

import android.text.format.DateUtils
import java.time.Duration

fun Duration.format(): String = DateUtils.formatElapsedTime(seconds)
