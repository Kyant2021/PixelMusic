package com.kyant.pixelmusic.api

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable

suspend fun String.searchSongs(): SearchResult? = withContext(Dispatchers.IO) {
    jsonClient.get("$API/search?keywords=${this@searchSongs}")
}

@Serializable
data class SearchResult(
    val result: Result? = Result(),
    val code: Int? = 0
)

@Serializable
data class Result(
    val songs: List<Song>? = listOf(),
    val hasMore: Boolean? = false,
    val songCount: Int? = 0
)

@Serializable
data class Song(
    val id: Long? = 0,
    val name: String? = "",
    val artists: List<Artist>? = listOf(),
    val album: Album? = Album()
)