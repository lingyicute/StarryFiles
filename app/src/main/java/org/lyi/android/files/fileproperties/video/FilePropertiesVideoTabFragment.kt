/*
 * Copyright (c) 2025 lingyicute <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.fileproperties.video

import android.content.Intent
import android.location.Geocoder
import androidx.lifecycle.lifecycleScope
import java8.nio.file.Path
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.WriteWith
import org.lyi.android.files.R
import org.lyi.android.files.file.FileItem
import org.lyi.android.files.file.format
import org.lyi.android.files.file.formatLong
import org.lyi.android.files.file.isVideo
import org.lyi.android.files.filelist.name
import org.lyi.android.files.fileproperties.FilePropertiesTabFragment
import org.lyi.android.files.util.ParcelableArgs
import org.lyi.android.files.util.ParcelableParceler
import org.lyi.android.files.util.Stateful
import org.lyi.android.files.util.args
import org.lyi.android.files.util.awaitGetFromLocation
import org.lyi.android.files.util.createViewLocation
import org.lyi.android.files.util.isGeocoderPresent
import org.lyi.android.files.util.isMediaMetadataRetrieverCompatible
import org.lyi.android.files.util.startActivitySafe
import org.lyi.android.files.util.userFriendlyString
import org.lyi.android.files.util.viewModels

class FilePropertiesVideoTabFragment : FilePropertiesTabFragment() {
    private val args by args<Args>()

    private val viewModel by viewModels { { FilePropertiesVideoTabViewModel(args.path) } }

    private var addressJob: Job? = null

    override fun onResume() {
        super.onResume()

        viewModel.videoInfoLiveData.observe(viewLifecycleOwner) { onVideoInfoChanged(it) }
    }

    override fun refresh() {
        viewModel.reload()
    }

    private fun onVideoInfoChanged(stateful: Stateful<VideoInfo>) {
        addressJob?.cancel()
        addressJob = null
        bindView(stateful) { videoInfo ->
            if (videoInfo.title != null) {
                addItemView(R.string.file_properties_media_title, videoInfo.title)
            }
            if (videoInfo.dimensions != null) {
                addItemView(
                    R.string.file_properties_media_dimensions, getString(
                        R.string.file_properties_media_dimensions_format,
                        videoInfo.dimensions.width, videoInfo.dimensions.height
                    )
                )
            }
            if (videoInfo.duration != null) {
                addItemView(R.string.file_properties_media_duration, videoInfo.duration.format())
            }
            if (videoInfo.date != null) {
                addItemView(R.string.file_properties_media_date_time, videoInfo.date.formatLong())
            }
            if (videoInfo.location != null) {
                addItemView(
                    R.string.file_properties_media_coordinates, getString(
                        R.string.file_properties_media_coordinates_format, videoInfo.location.first,
                        videoInfo.location.second
                    )
                ) {
                    startActivitySafe(
                        Intent::class.createViewLocation(
                            videoInfo.location.first, videoInfo.location.second, args.path.name
                        )
                    )
                }
                if (isGeocoderPresent) {
                    val textView = addItemView(
                        R.string.file_properties_media_address, getString(R.string.loading)
                    )
                    val geocoder = Geocoder(requireContext())
                    addressJob = viewLifecycleOwner.lifecycleScope.launch {
                        val address = try {
                            geocoder.awaitGetFromLocation(
                                videoInfo.location.first.toDouble(),
                                videoInfo.location.second.toDouble(), 1
                            ).first()
                        } catch (e: Exception) {
                            null
                        }
                        if (isActive) {
                            textView.text = address?.userFriendlyString
                                ?: getString(R.string.unknown)
                        }
                    }
                }
            }
            if (videoInfo.bitRate != null) {
                addItemView(
                    R.string.file_properties_media_bit_rate, getString(
                        R.string.file_properties_media_bit_rate_format, videoInfo.bitRate / 1000
                    )
                )
            }
        }
    }

    companion object {
        fun isAvailable(file: FileItem): Boolean =
            file.mimeType.isVideo && file.path.isMediaMetadataRetrieverCompatible
    }

    @Parcelize
    class Args(val path: @WriteWith<ParcelableParceler> Path) : ParcelableArgs
}
