/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.filelist

import java8.nio.file.Path
import org.lyi.android.files.file.MimeType
import org.lyi.android.files.file.isSupportedArchive
import org.lyi.android.files.provider.archive.archiveFile
import org.lyi.android.files.provider.archive.isArchivePath
import org.lyi.android.files.provider.document.isDocumentPath
import org.lyi.android.files.provider.document.resolver.DocumentResolver
import org.lyi.android.files.provider.linux.isLinuxPath

val Path.name: String
    get() = fileName?.toString() ?: if (isArchivePath) archiveFile.fileName.toString() else "/"

fun Path.toUserFriendlyString(): String = if (isLinuxPath) toFile().path else toUri().toString()

fun Path.isArchiveFile(mimeType: MimeType): Boolean = !isArchivePath && mimeType.isSupportedArchive

val Path.isLocalPath: Boolean
    get() =
        isLinuxPath || (isDocumentPath && DocumentResolver.isLocal(this as DocumentResolver.Path))

val Path.isRemotePath: Boolean
    get() = !isLocalPath
