package com.kyant.pixelmusic.api

import com.kyant.pixelmusic.api.playlist.PlaylistResult
import com.kyant.pixelmusic.api.playlist.PlaylistResults
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

typealias PlaylistId = Long

typealias UserId = Long

suspend fun PlaylistId.findPlaylist(cookie: String? = null): PlaylistResult? =
    withContext(Dispatchers.IO) {
        jsonClient.get(
            if (cookie.isNullOrEmpty()) "$API/playlist/detail?id=${this@findPlaylist}"
            else "$API/playlist/detail?id=${this@findPlaylist}&cookie=$cookie"
        )
    }

suspend fun UserId.findUserPlaylist(): PlaylistResults? = withContext(Dispatchers.IO) {
    jsonClient.get("$API/user/playlist?uid=${this@findUserPlaylist}")
}