package com.kyant.pixelmusic.api

import com.kyant.pixelmusic.api.album.AlbumResult
import com.kyant.pixelmusic.locals.JsonParser
import java.net.URL

typealias AlbumId = Long

suspend fun AlbumId.findCoverUrl(size: Int = 500): String =
    "${JsonParser().parse<AlbumResult>(URL("$API/album?id=${this@findCoverUrl}").readText())?.album?.picUrl}?param=${size}y$size"