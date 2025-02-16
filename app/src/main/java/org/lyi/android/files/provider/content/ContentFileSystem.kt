/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.content

import android.os.Parcel
import android.os.Parcelable
import androidx.core.net.toUri
import java8.nio.file.FileStore
import java8.nio.file.FileSystem
import java8.nio.file.Path
import java8.nio.file.PathMatcher
import java8.nio.file.WatchService
import java8.nio.file.attribute.UserPrincipalLookupService
import java8.nio.file.spi.FileSystemProvider
import org.lyi.android.files.provider.common.ByteString
import org.lyi.android.files.provider.common.ByteStringListPathCreator
import java.io.IOException

internal class ContentFileSystem(private val provider: ContentFileSystemProvider) : FileSystem(),
    ByteStringListPathCreator, Parcelable {
    override fun provider(): FileSystemProvider = provider

    override fun close() {
        throw UnsupportedOperationException()
    }

    override fun isOpen(): Boolean = true

    override fun isReadOnly(): Boolean = false

    override fun getSeparator(): String = SEPARATOR_STRING

    override fun getRootDirectories(): Iterable<Path> = emptyList()

    override fun getFileStores(): Iterable<FileStore> {
        // TODO
        throw UnsupportedOperationException()
    }

    override fun supportedFileAttributeViews(): Set<String> =
        ContentFileAttributeView.SUPPORTED_NAMES

    override fun getPath(first: String, vararg more: String): ContentPath {
        if (more.isNotEmpty()) {
            throw UnsupportedOperationException()
        }
        val uri = first.toUri()
        return ContentPath(this, uri)
    }

    override fun getPath(first: ByteString, vararg more: ByteString): ContentPath {
        if (more.isNotEmpty()) {
            throw UnsupportedOperationException()
        }
        val uri = first.toString().toUri()
        return ContentPath(this, uri)
    }

    override fun getPathMatcher(syntaxAndPattern: String): PathMatcher {
        throw UnsupportedOperationException()
    }

    override fun getUserPrincipalLookupService(): UserPrincipalLookupService {
        throw UnsupportedOperationException()
    }

    @Throws(IOException::class)
    override fun newWatchService(): WatchService {
        // TODO
        throw UnsupportedOperationException()
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {}

    companion object {
        const val SEPARATOR = '/'.code.toByte()
        private const val SEPARATOR_STRING = SEPARATOR.toInt().toChar().toString()

        @JvmField
        val CREATOR = object : Parcelable.Creator<ContentFileSystem> {
            override fun createFromParcel(source: Parcel): ContentFileSystem =
                ContentFileSystemProvider.fileSystem

            override fun newArray(size: Int): Array<ContentFileSystem?> = arrayOfNulls(size)
        }
    }
}
