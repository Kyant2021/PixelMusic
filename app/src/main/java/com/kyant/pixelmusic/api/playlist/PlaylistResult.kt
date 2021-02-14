package com.kyant.pixelmusic.api.playlist

import kotlinx.serialization.Serializable

@Serializable
data class PlaylistResult(
    val version: String? = "",
    val more: Boolean? = false,
    val playlist: List<Playlist>? = listOf(),
    val code: Long? = 0
)