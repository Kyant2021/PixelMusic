package com.kyant.pixelmusic.api

import com.beust.klaxon.Klaxon
import com.kyant.pixelmusic.api.album.AlbumResult
import java.net.URL

typealias AlbumId = Long

suspend fun AlbumId.findCoverUrl(size: Int = 500): String =
    "${Klaxon().parse<AlbumResult>(URL("$API/album?id=${this@findCoverUrl}").readText())?.album?.picUrl}?param=${size}y$size"