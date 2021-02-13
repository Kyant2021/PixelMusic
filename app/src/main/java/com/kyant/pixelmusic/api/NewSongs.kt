package com.kyant.pixelmusic.api

import com.beust.klaxon.Klaxon
import com.kyant.pixelmusic.api.newsongs.NewSongsResult
import com.kyant.pixelmusic.media.Song
import com.kyant.pixelmusic.media.toSong
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

suspend fun findNewSongs(type: Int = 0): List<Song>? = withContext(Dispatchers.IO) {
    Klaxon().parse<NewSongsResult>(URL("$API/top/song?type=$type").readText())?.data?.map { it.toSong() }
}