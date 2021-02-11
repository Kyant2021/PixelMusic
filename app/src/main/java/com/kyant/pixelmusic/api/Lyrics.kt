package com.kyant.pixelmusic.api

import com.kyant.pixelmusic.api.lyrics.LyricResult
import com.kyant.pixelmusic.locals.JsonParser
import com.kyant.pixelmusic.util.toLyrics
import java.net.URL

typealias Time = String
typealias Content = String
typealias Lyrics = Map<Time, Content>

val EmptyLyrics: Lyrics = emptyMap()

suspend fun SongId.findLyrics(): Lyrics? = JsonParser().parse<LyricResult>(
    URL("$API?type=lyric&id=${this@findLyrics}").readText()
)?.lrc?.lyric?.toLyrics()