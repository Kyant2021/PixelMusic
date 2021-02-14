package com.kyant.pixelmusic.api

import com.kyant.pixelmusic.api.playlist.PlaylistResult
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

typealias PlaylistId = Long

typealias UserId = Long

suspend fun UserId.findUserPlaylist(): PlaylistResult? = withContext(Dispatchers.IO) {
    jsonClient.get("$API/user/playlist?uid=${this@findUserPlaylist}")
}