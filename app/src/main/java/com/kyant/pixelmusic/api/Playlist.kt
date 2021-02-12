package com.kyant.pixelmusic.api

import com.kyant.pixelmusic.api.playlist.PlaylistResult
import com.kyant.pixelmusic.locals.JsonParser
import java.net.URL

suspend fun TopListId.findPlaylist(): PlaylistResult? = JsonParser().parse<PlaylistResult>(
    URL("$API/playlist/detail?id=${this@findPlaylist}").readText()
)