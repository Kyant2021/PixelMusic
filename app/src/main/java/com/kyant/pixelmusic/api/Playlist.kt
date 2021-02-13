package com.kyant.pixelmusic.api

import com.beust.klaxon.Klaxon
import com.kyant.pixelmusic.api.playlist.PlaylistResult
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun TopListId.findPlaylist(): PlaylistResult? = withContext(Dispatchers.IO) {
    Klaxon().parse<PlaylistResult>(HttpClient(CIO).get<String>("$API/playlist/detail?id=${this@findPlaylist}"))
}