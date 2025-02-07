/*
 * Copyright (c) 2020 Hai Zhang <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.app

import android.os.AsyncTask
import android.os.Build
import android.webkit.WebView
import jcifs.context.SingletonContext
import org.lyi.android.files.BuildConfig
import org.lyi.android.files.coil.initializeCoil
import org.lyi.android.files.filejob.fileJobNotificationTemplate
import org.lyi.android.files.ftpserver.ftpServerServiceNotificationTemplate
import org.lyi.android.files.hiddenapi.HiddenApi
import org.lyi.android.files.provider.FileSystemProviders
import org.lyi.android.files.settings.Settings
import org.lyi.android.files.storage.FtpServerAuthenticator
import org.lyi.android.files.storage.SftpServerAuthenticator
import org.lyi.android.files.storage.SmbServerAuthenticator
import org.lyi.android.files.storage.StorageVolumeListLiveData
import org.lyi.android.files.storage.WebDavServerAuthenticator
import org.lyi.android.files.theme.custom.CustomThemeHelper
import org.lyi.android.files.theme.night.NightModeHelper
import java.util.Properties
import org.lyi.android.files.provider.ftp.client.Client as FtpClient
import org.lyi.android.files.provider.sftp.client.Client as SftpClient
import org.lyi.android.files.provider.smb.client.Client as SmbClient
import org.lyi.android.files.provider.webdav.client.Client as WebDavClient

val appInitializers = listOf(
    ::initializeCrashlytics,
    ::disableHiddenApiChecks,
    ::initializeWebViewDebugging,
    ::initializeCoil,
    ::initializeFileSystemProviders,
    ::upgradeApp,
    ::initializeLiveDataObjects,
    ::initializeCustomTheme,
    ::initializeNightMode,
    ::createNotificationChannels
)

private fun initializeCrashlytics() {
//#ifdef NONFREE
    org.lyi.android.files.nonfree.CrashlyticsInitializer.initialize()
//#endif
}

private fun disableHiddenApiChecks() {
    HiddenApi.disableHiddenApiChecks()
}

private fun initializeWebViewDebugging() {
    if (BuildConfig.DEBUG) {
        WebView.setWebContentsDebuggingEnabled(true)
    }
}

private fun initializeFileSystemProviders() {
    FileSystemProviders.install()
    FileSystemProviders.overflowWatchEvents = true
    // SingletonContext.init() calls NameServiceClientImpl.initCache() which connects to network.
    AsyncTask.THREAD_POOL_EXECUTOR.execute {
        SingletonContext.init(
            Properties().apply {
                setProperty("jcifs.netbios.cachePolicy", "0")
                setProperty("jcifs.smb.client.maxVersion", "SMB1")
            }
        )
    }
    FtpClient.authenticator = FtpServerAuthenticator
    SftpClient.authenticator = SftpServerAuthenticator
    SmbClient.authenticator = SmbServerAuthenticator
    WebDavClient.authenticator = WebDavServerAuthenticator
}

private fun initializeLiveDataObjects() {
    // Force initialization of LiveData objects so that it won't happen on a background thread.
    StorageVolumeListLiveData.value
    Settings.FILE_LIST_DEFAULT_DIRECTORY.value
}

private fun initializeCustomTheme() {
    CustomThemeHelper.initialize(application)
}

private fun initializeNightMode() {
    NightModeHelper.initialize(application)
}

private fun createNotificationChannels() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        notificationManager.createNotificationChannels(
            listOf(
                backgroundActivityStartNotificationTemplate.channelTemplate,
                fileJobNotificationTemplate.channelTemplate,
                ftpServerServiceNotificationTemplate.channelTemplate
            ).map { it.create(application) }
        )
    }
}
