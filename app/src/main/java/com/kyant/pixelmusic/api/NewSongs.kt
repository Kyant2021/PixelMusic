package com.kyant.pixelmusic.api

import com.beust.klaxon.Klaxon
import com.kyant.pixelmusic.api.newsongs.NewSongsResult
import com.kyant.pixelmusic.media.Song
import com.kyant.pixelmusic.media.toSong
import java.net.URL

suspend fun findNewSongs(type: Int = 0): List<Song>? = Klaxon().parse<NewSongsResult>(
    URL("$API/top/song?type=$type").readText()
)?.data?.map { it.toSong() }