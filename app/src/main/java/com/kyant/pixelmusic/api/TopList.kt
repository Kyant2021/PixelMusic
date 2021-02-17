package com.kyant.pixelmusic.api

import com.kyant.pixelmusic.api.playlist.PlaylistResult
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable

suspend fun findTopList(): List<TopList>? = withContext(Dispatchers.IO) {
    jsonClient.get<TopListResult>("$API/toplist").list
}

@Serializable
data class TopListResult(
    val code: Int? = 0,
    val list: List<TopList>? = listOf()
)

@Serializable
data class TopList(
    val updateFrequency: String? = "",
    val name: String? = "",
    val id: Long? = 0,
    val coverImgUrl: String? = ""
)

suspend fun PlaylistId.findPlaylist(): PlaylistResult? = withContext(Dispatchers.IO) {
    jsonClient.get("$API/playlist/detail?id=${this@findPlaylist}")
}