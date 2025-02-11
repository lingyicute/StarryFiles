/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.common

import java8.nio.file.attribute.GroupPrincipal
import java8.nio.file.attribute.PosixFileAttributeView
import java8.nio.file.attribute.PosixFilePermission
import java8.nio.file.attribute.UserPrincipal
import java.io.IOException

interface PosixFileAttributeView : PosixFileAttributeView {
    @Throws(IOException::class)
    override fun readAttributes(): PosixFileAttributes

    @Throws(IOException::class)
    override fun getOwner(): PosixUser? = readAttributes().owner()

    @Throws(IOException::class)
    override fun setOwner(owner: UserPrincipal) {
        if (owner !is PosixUser) {
            throw UnsupportedOperationException(owner.toString())
        }
        setOwner(owner)
    }

    @Throws(IOException::class)
    fun setOwner(owner: PosixUser)

    @Throws(IOException::class)
    override fun setGroup(group: GroupPrincipal) {
        if (group !is PosixGroup) {
            throw UnsupportedOperationException(group.toString())
        }
        setGroup(group)
    }

    @Throws(IOException::class)
    fun setGroup(group: PosixGroup)

    @Throws(IOException::class)
    override fun setPermissions(permissions: Set<PosixFilePermission>) {
        setMode(permissions.toMode())
    }

    @Throws(IOException::class)
    fun setMode(mode: Set<PosixFileModeBit>)

    @Throws(IOException::class)
    fun setSeLinuxContext(context: ByteString)

    @Throws(IOException::class)
    fun restoreSeLinuxContext()
}
