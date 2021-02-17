package com.kyant.pixelmusic.api.playlist

import kotlinx.serialization.Serializable

@Serializable
data class PlaylistResult(
    val code: Long? = 0,
    val relatedVideos: List<String>? = listOf(),
    val playlist: Playlist? = Playlist(),
    val urls: List<String>? = listOf(),
    val privileges: List<Privilege>? = listOf()
)

@Serializable
data class PlaylistResults(
    val version: String? = "",
    val more: Boolean? = false,
    val playlist: List<Playlist>? = listOf(),
    val code: Long? = 0
)