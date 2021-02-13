package com.kyant.pixelmusic.api

import com.beust.klaxon.Klaxon
import com.kyant.pixelmusic.api.newsongs.NewSongsResult
import com.kyant.pixelmusic.media.Song
import com.kyant.pixelmusic.media.toSong
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun findNewSongs(type: Int = 0): List<Song>? = withContext(Dispatchers.IO) {
    Klaxon().parse<NewSongsResult>(HttpClient(CIO).get<String>("$API/top/song?type=$type"))?.data?.map { it.toSong() }
}