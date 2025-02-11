/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.linux.syscall

import org.lyi.android.files.provider.common.ByteString

class StructPasswd(
    val pw_name: ByteString?,
    val pw_uid: Int,
    val pw_gid: Int,
    val pw_gecos: ByteString?,
    val pw_dir: ByteString?,
    val pw_shell: ByteString?
)
