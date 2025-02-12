/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.storage

import android.content.Context
import android.content.Intent
import android.os.storage.StorageVolume
import androidx.annotation.DrawableRes
import java8.nio.file.Path
import java8.nio.file.Paths
import kotlinx.parcelize.Parcelize
import org.lyi.android.files.R
import org.lyi.android.files.compat.getDescriptionCompat
import org.lyi.android.files.compat.isPrimaryCompat
import org.lyi.android.files.compat.pathCompat
import org.lyi.android.files.util.createIntent
import org.lyi.android.files.util.putArgs
import org.lyi.android.files.util.valueCompat

sealed class DeviceStorage : Storage() {
    override val description: String
        get() = linuxPath

    override val path: Path
        get() = Paths.get(linuxPath)

    abstract override val linuxPath: String

    override fun createEditIntent(): Intent =
        EditDeviceStorageDialogActivity::class.createIntent()
            .putArgs(EditDeviceStorageDialogFragment.Args(this))

    fun copy_(
        customName: String? = this.customName,
        isVisible: Boolean = this.isVisible
    ): DeviceStorage =
        when (this) {
            is FileSystemRoot -> copy(customName, isVisible)
            is PrimaryStorageVolume -> copy(customName, isVisible)
        }
}

@Parcelize
data class FileSystemRoot(
    override val customName: String?,
    override val isVisible: Boolean
) : DeviceStorage() {
    override val id: Long
        get() = "FileSystemRoot".hashCode().toLong()

    override val iconRes: Int
        @DrawableRes
        get() = R.drawable.device_icon_white_24dp

    override fun getDefaultName(context: Context): String =
        context.getString(R.string.storage_file_system_root_title)

    override val linuxPath: String
        get() = LINUX_PATH

    companion object {
        const val LINUX_PATH = "/"
    }
}

@Parcelize
data class PrimaryStorageVolume(
    override val customName: String?,
    override val isVisible: Boolean
) : DeviceStorage() {
    override val id: Long
        get() = "PrimaryStorageVolume".hashCode().toLong()

    override val iconRes: Int
        @DrawableRes
        get() = R.drawable.storage24

    override fun getDefaultName(context: Context): String =
        context.getString(R.string.primarystorage_title)

    override val linuxPath: String
        get() = storageVolume.pathCompat

    private val storageVolume: StorageVolume
        get() = StorageVolumeListLiveData.valueCompat.find { it.isPrimaryCompat }!!
}
