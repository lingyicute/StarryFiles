/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.provider.linux

import java8.nio.file.attribute.UserPrincipalLookupService
import java8.nio.file.attribute.UserPrincipalNotFoundException
import org.lyi.android.files.provider.common.ByteString
import org.lyi.android.files.provider.common.PosixGroup
import org.lyi.android.files.provider.common.PosixUser
import org.lyi.android.files.provider.common.toByteString
import org.lyi.android.files.provider.linux.syscall.Syscall
import org.lyi.android.files.provider.linux.syscall.SyscallException
import java.io.IOException

internal object LinuxUserPrincipalLookupService : UserPrincipalLookupService() {
    @Throws(IOException::class)
    override fun lookupPrincipalByName(name: String): PosixUser =
        lookupPrincipalByName(name.toByteString())

    @Throws(IOException::class)
    fun lookupPrincipalByName(name: ByteString): PosixUser {
        val passwd = try {
            Syscall.getpwnam(name)
        } catch (e: SyscallException) {
            throw e.toFileSystemException(null)
        } ?: throw UserPrincipalNotFoundException(name.toString())
        return PosixUser(passwd.pw_uid, passwd.pw_name)
    }

    @Throws(IOException::class)
    fun lookupPrincipalById(id: Int): PosixUser =
        try {
            getUserById(id)
        } catch (e: SyscallException) {
            throw e.toFileSystemException(null)
        }

    @Throws(SyscallException::class)
    fun getUserById(uid: Int): PosixUser {
        val passwd = Syscall.getpwuid(uid)
        return PosixUser(uid, passwd?.pw_name)
    }

    @Throws(IOException::class)
    override fun lookupPrincipalByGroupName(group: String): PosixGroup =
        lookupPrincipalByGroupName(group.toByteString())

    @Throws(IOException::class)
    fun lookupPrincipalByGroupName(group: ByteString): PosixGroup {
        val groupStruct = try {
            Syscall.getgrnam(group)
        } catch (e: SyscallException) {
            throw e.toFileSystemException(null)
        } ?: throw UserPrincipalNotFoundException(group.toString())
        return PosixGroup(groupStruct.gr_gid, groupStruct.gr_name)
    }

    @Throws(IOException::class)
    fun lookupPrincipalByGroupId(groupId: Int): PosixGroup =
        try {
            getGroupById(groupId)
        } catch (e: SyscallException) {
            throw e.toFileSystemException(null)
        }

    @Throws(SyscallException::class)
    fun getGroupById(gid: Int): PosixGroup {
        val group = Syscall.getgrgid(gid)
        return PosixGroup(gid, group?.gr_name)
    }
}
