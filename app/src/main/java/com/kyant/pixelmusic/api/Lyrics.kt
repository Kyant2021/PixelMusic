package com.kyant.pixelmusic.api

import com.kyant.pixelmusic.util.toLyrics
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable

typealias Time = String
typealias Content = String
typealias Lyrics = Map<Time, Content>

val EmptyLyrics: Lyrics = emptyMap()

suspend fun SongId.findLyrics(): Lyrics? = withContext(Dispatchers.IO) {
    jsonClient.get<LyricResult>("$API/lyric?id=${this@findLyrics}").lrc?.lyric?.toLyrics()
}

@Serializable
data class LyricResult(
    val lrc: Lrc? = Lrc(),
    val code: Int? = 0
)

@Serializable
data class Lrc(
    val version: Int? = 0,
    val lyric: String? = ""
)