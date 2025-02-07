/*
 * Copyright (c) 2019 Hai Zhang <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider

import java8.nio.file.Files
import java8.nio.file.ProviderNotFoundException
import java8.nio.file.spi.FileSystemProvider
import org.lyi.android.files.provider.archive.ArchiveFileSystemProvider
import org.lyi.android.files.provider.common.AndroidFileTypeDetector
import org.lyi.android.files.provider.content.ContentFileSystemProvider
import org.lyi.android.files.provider.document.DocumentFileSystemProvider
import org.lyi.android.files.provider.ftp.FtpFileSystemProvider
import org.lyi.android.files.provider.ftp.FtpesFileSystemProvider
import org.lyi.android.files.provider.ftp.FtpsFileSystemProvider
import org.lyi.android.files.provider.linux.LinuxFileSystemProvider
import org.lyi.android.files.provider.root.isRunningAsRoot
import org.lyi.android.files.provider.sftp.SftpFileSystemProvider
import org.lyi.android.files.provider.smb.SmbFileSystemProvider
import org.lyi.android.files.provider.webdav.WebDavFileSystemProvider
import org.lyi.android.files.provider.webdav.WebDavsFileSystemProvider

object FileSystemProviders {
    /**
     * If set, WatchService implementations will skip processing any event data and simply send an
     * overflow event to all the registered keys upon successful read from the inotify fd. This can
     * help reducing the JNI and GC overhead when large amount of inotify events are generated.
     * Simply sending an overflow event to all the keys is okay because we use only one key per
     * service for WatchServicePathObservable.
     */
    @Volatile
    var overflowWatchEvents = false

    fun install() {
        FileSystemProvider.installDefaultProvider(LinuxFileSystemProvider)
        FileSystemProvider.installProvider(ArchiveFileSystemProvider)
        if (!isRunningAsRoot) {
            FileSystemProvider.installProvider(ContentFileSystemProvider)
            FileSystemProvider.installProvider(DocumentFileSystemProvider)
            FileSystemProvider.installProvider(FtpFileSystemProvider)
            FileSystemProvider.installProvider(FtpsFileSystemProvider)
            FileSystemProvider.installProvider(FtpesFileSystemProvider)
            FileSystemProvider.installProvider(SftpFileSystemProvider)
            FileSystemProvider.installProvider(SmbFileSystemProvider)
            FileSystemProvider.installProvider(WebDavFileSystemProvider)
            FileSystemProvider.installProvider(WebDavsFileSystemProvider)
        }
        Files.installFileTypeDetector(AndroidFileTypeDetector)
    }

    operator fun get(scheme: String): FileSystemProvider {
        for (provider in FileSystemProvider.installedProviders()) {
            if (provider.scheme.equals(scheme, ignoreCase = true)) {
                return provider
            }
        }
        throw ProviderNotFoundException(scheme)
    }
}
