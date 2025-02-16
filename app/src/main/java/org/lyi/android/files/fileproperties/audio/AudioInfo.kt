/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.fileproperties.audio

import java.time.Duration

// @see com.android.providers.media.scan.ModernMediaScanner.scanItemAudio
// @see com.android.documentsui.inspector.MediaView.showAudioData
// @see https://github.com/GNOME/nautilus/blob/c73ad94a72f8e9a989b01858018de74182d17f0e/extensions/audio-video-properties/bacon-video-widget-properties.c#L89
class AudioInfo(
    val title: String?,
    val artist: String?,
    val album: String?,
    val albumArtist: String?,
    val composer: String?,
    val discNumber: String?,
    val trackNumber: String?,
    val year: String?,
    val genre: String?,
    val duration: Duration?,
    val bitRate: Int?,
    val sampleRate: Int?
)
