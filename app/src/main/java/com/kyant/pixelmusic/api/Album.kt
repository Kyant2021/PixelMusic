package com.kyant.pixelmusic.api

import com.beust.klaxon.Klaxon
import com.kyant.pixelmusic.api.album.AlbumResult
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

typealias AlbumId = Long

suspend fun AlbumId.findCoverUrl(size: Int = 500): String = withContext(Dispatchers.IO) {
    "${Klaxon().parse<AlbumResult>(HttpClient(CIO).get<String>("$API/album?id=${this@findCoverUrl}"))?.album?.picUrl}?param=${size}y$size"
}