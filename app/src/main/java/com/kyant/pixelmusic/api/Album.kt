package com.kyant.pixelmusic.api

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable

typealias AlbumId = Long

suspend fun AlbumId.findCoverUrl(size: Int = 500): String = withContext(Dispatchers.IO) {
    "${jsonClient.get<AlbumResult>("$API/album?id=${this@findCoverUrl}").album?.picUrl}?param=${size}y$size"
}

@Serializable
data class AlbumResult(
    val code: Int? = 0,
    val album: Album? = Album()
)

@Serializable
data class Album(
    val picUrl: String? = "",
    val name: String? = "",
    val id: Long? = 0
)