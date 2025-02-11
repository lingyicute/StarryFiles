/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.storage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runInterruptible
import org.lyi.android.files.provider.common.newDirectoryStream
import org.lyi.android.files.util.ActionState
import org.lyi.android.files.util.isFinished
import org.lyi.android.files.util.isReady

class EditWebDavServerViewModel : ViewModel() {
    private val _connectState =
        MutableStateFlow<ActionState<WebDavServer, Unit>>(ActionState.Ready())
    val connectState = _connectState.asStateFlow()

    fun connect(server: WebDavServer) {
        viewModelScope.launch {
            check(_connectState.value.isReady)
            _connectState.value = ActionState.Running(server)
            _connectState.value = try {
                runInterruptible(Dispatchers.IO) {
                    WebDavServerAuthenticator.addTransientServer(server)
                    try {
                        val path = server.path
                        path.fileSystem.use {
                            path.newDirectoryStream().toList()
                        }
                    } finally {
                        WebDavServerAuthenticator.removeTransientServer(server)
                    }
                }
                ActionState.Success(server, Unit)
            } catch (e: Exception) {
                ActionState.Error(server, e)
            }
        }
    }

    fun finishConnecting() {
        viewModelScope.launch {
            check(_connectState.value.isFinished)
            _connectState.value = ActionState.Ready()
        }
    }
}
