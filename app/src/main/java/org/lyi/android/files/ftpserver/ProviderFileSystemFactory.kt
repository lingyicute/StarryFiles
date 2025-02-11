/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.ftpserver

import org.apache.ftpserver.ftplet.FileSystemFactory
import org.apache.ftpserver.ftplet.FileSystemView
import org.apache.ftpserver.ftplet.User

class ProviderFileSystemFactory : FileSystemFactory {
    override fun createFileSystemView(user: User): FileSystemView = ProviderFileSystemView(user)
}
