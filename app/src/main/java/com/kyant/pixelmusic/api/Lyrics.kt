package com.kyant.pixelmusic.api

import com.beust.klaxon.Klaxon
import com.kyant.pixelmusic.api.lyrics.LyricResult
import com.kyant.pixelmusic.util.toLyrics
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

typealias Time = String
typealias Content = String
typealias Lyrics = Map<Time, Content>

val EmptyLyrics: Lyrics = emptyMap()

suspend fun SongId.findLyrics(): Lyrics? = withContext(Dispatchers.IO) {
    Klaxon().parse<LyricResult>(HttpClient(CIO).get<String>("$API/lyric?id=${this@findLyrics}"))?.lrc?.lyric?.toLyrics()
}