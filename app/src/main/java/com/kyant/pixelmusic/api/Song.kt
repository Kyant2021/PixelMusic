package com.kyant.pixelmusic.api

import androidx.compose.runtime.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable

typealias SongId = Long

suspend fun SongId.findUrl(): String? = withContext(Dispatchers.IO) {
    jsonClient.get<SongResult>("$API/song/url?id=${this@findUrl}").data?.getOrNull(0)?.url
}

@Serializable
data class SongResult(
    val data: List<Data>? = listOf(),
    val code: Int? = 0
)

@Serializable
data class Data(
    val artists: List<Artist>? = listOf(),
    val album: Album? = Album(),
    val name: String? = "",
    val id: Long? = 0,
    val url: String? = ""
)

@Serializable
data class Artist(
    val name: String? = "",
    val id: Long? = 0
)