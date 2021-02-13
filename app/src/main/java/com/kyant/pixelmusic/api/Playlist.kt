package com.kyant.pixelmusic.api

import com.beust.klaxon.Klaxon
import com.kyant.pixelmusic.api.playlist.PlaylistResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

suspend fun TopListId.findPlaylist(): PlaylistResult? = withContext(Dispatchers.IO) {
    Klaxon().parse<PlaylistResult>(URL("$API/playlist/detail?id=${this@findPlaylist}").readText())
}