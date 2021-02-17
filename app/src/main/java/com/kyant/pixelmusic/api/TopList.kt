package com.kyant.pixelmusic.api

import com.kyant.pixelmusic.api.playlist.Playlist
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable

suspend fun findTopList(): List<Playlist>? = withContext(Dispatchers.IO) {
    jsonClient.get<TopListResult>("$API/toplist").list
}

@Serializable
data class TopListResult(
    val code: Int? = 0,
    val list: List<Playlist>? = listOf()
)