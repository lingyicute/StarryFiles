/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.content

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import androidx.core.net.toUri
import java8.nio.file.FileSystem
import java8.nio.file.LinkOption
import java8.nio.file.Path
import java8.nio.file.WatchEvent
import java8.nio.file.WatchKey
import java8.nio.file.WatchService
import org.lyi.android.files.provider.common.ByteString
import org.lyi.android.files.provider.common.ByteStringListPath
import org.lyi.android.files.provider.common.UriAuthority
import org.lyi.android.files.provider.common.toByteString
import org.lyi.android.files.provider.content.resolver.Resolver
import org.lyi.android.files.provider.content.resolver.ResolverException
import org.lyi.android.files.util.StableUriParceler
import org.lyi.android.files.util.readParcelable
import java.io.File
import java.net.URI

internal class ContentPath : ByteStringListPath<ContentPath> {
    private val fileSystem: ContentFileSystem

    val uri: Uri?

    constructor(fileSystem: ContentFileSystem, uri: Uri) : super(
        ContentFileSystem.SEPARATOR, true,
        listOf(Uri.encode(uri.toString()).toByteString(), uri.bestFileName)
    ) {
        this.fileSystem = fileSystem
        this.uri = uri
    }

    private constructor(fileSystem: ContentFileSystem, segments: List<ByteString>) : super(
        ContentFileSystem.SEPARATOR, false, segments
    ) {
        this.fileSystem = fileSystem
        uri = null
    }

    override fun isPathAbsolute(path: ByteString): Boolean {
        throw AssertionError()
    }

    override fun createPath(path: ByteString): ContentPath =
        ContentPath(fileSystem, path.toString().toUri())

    override fun createPath(absolute: Boolean, segments: List<ByteString>): ContentPath {
        if (absolute) {
            require(segments.size == 2) {
                "Cannot create absolute ContentPath with segments $segments"
            }
        }
        return ContentPath(fileSystem, segments)
    }

    override val uriScheme: String
        get() {
            throw AssertionError()
        }

    override val uriAuthority: UriAuthority
        get() {
            throw AssertionError()
        }

    override val uriPath: ByteString
        get() {
            throw AssertionError()
        }

    override val uriQuery: ByteString?
        get() {
            throw AssertionError()
        }

    override val defaultDirectory: ContentPath
        get() {
            throw AssertionError()
        }

    override fun getFileSystem(): FileSystem = fileSystem

    override fun getRoot(): ContentPath? = null

    override fun getParent(): ContentPath? = null

    override fun normalize(): ContentPath = this

    override fun toUri(): URI = URI.create(uri!!.toString())

    override fun toAbsolutePath(): ContentPath {
        if (!isAbsolute) {
            throw UnsupportedOperationException()
        }
        return this
    }

    override fun toRealPath(vararg options: LinkOption): ContentPath = this

    override fun toFile(): File {
        throw UnsupportedOperationException()
    }

    override fun register(
        watcher: WatchService,
        events: Array<WatchEvent.Kind<*>>,
        vararg modifiers: WatchEvent.Modifier
    ): WatchKey {
        throw UnsupportedOperationException()
    }

    override fun toByteString(): ByteString =
        uri?.toString()?.toByteString() ?: super.toByteString()

    override fun toString(): String = uri?.toString() ?: super.toString()

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (javaClass != other?.javaClass) {
            return false
        }
        other as ContentPath
        return if (uri != null || other.uri != null) uri == other.uri else super.equals(other)
    }

    override fun hashCode(): Int {
        return uri?.hashCode() ?: super.hashCode()
    }

    private constructor(source: Parcel) : super(source) {
        fileSystem = source.readParcelable()!!
        //uri = source.readParcelable()
        uri = StableUriParceler.create(source)
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        super.writeToParcel(dest, flags)

        dest.writeParcelable(fileSystem, flags)
        //dest.writeParcelable(uri, flags)
        with(StableUriParceler) { uri.write(dest, flags) }
    }

    companion object {
        private val Uri.bestFileName: ByteString
            get() =
                (try {
                    Resolver.getDisplayName(this)
                } catch (e: ResolverException) {
                    e.printStackTrace()
                    null
                } ?: lastPathSegment ?: "file").toByteString()

        @JvmField
        val CREATOR = object : Parcelable.Creator<ContentPath> {
            override fun createFromParcel(source: Parcel): ContentPath = ContentPath(source)

            override fun newArray(size: Int): Array<ContentPath?> = arrayOfNulls(size)
        }
    }
}

val Path.isContentPath: Boolean
    get() = this is ContentPath
