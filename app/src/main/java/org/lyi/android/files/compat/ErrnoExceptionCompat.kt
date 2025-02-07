/*
 * Copyright (c) 2018 Hai Zhang <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.compat

import android.system.ErrnoException
import org.lyi.android.files.hiddenapi.RestrictedHiddenApi
import org.lyi.android.files.util.lazyReflectedField

@RestrictedHiddenApi
private val functionNameField by lazyReflectedField(ErrnoException::class.java, "functionName")

val ErrnoException.functionNameCompat: String
    get() = functionNameField.get(this) as String
