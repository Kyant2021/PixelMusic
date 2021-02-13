package com.kyant.pixelmusic.api

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable

suspend fun TopListId.findPlaylist(): PlaylistResult? = withContext(Dispatchers.IO) {
    jsonClient.get("$API/playlist/detail?id=${this@findPlaylist}")
}

@Serializable
data class PlaylistResult(
    val code: Int? = 0,
    val playlist: Playlist? = Playlist()
)

@Serializable
data class Playlist(
    val tracks: List<Track>? = listOf(),
    val name: String? = "",
    val id: Long? = 0
)

@Serializable
data class Track(
    val name: String? = "",
    val id: Long? = 0,
    val ar: List<Artist>? = listOf(),
    val al: Album? = Album()
)