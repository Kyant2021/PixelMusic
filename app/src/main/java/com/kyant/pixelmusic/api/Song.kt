package com.kyant.pixelmusic.api

import androidx.compose.runtime.*
import com.beust.klaxon.Klaxon
import com.kyant.pixelmusic.api.song.SongResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

typealias SongId = Long

suspend fun SongId.findUrl(): String? = withContext(Dispatchers.IO) {
    Klaxon().parse<SongResult>(URL("$API/song/url?id=${this@findUrl}").readText())?.data
        ?.getOrNull(0)?.url
}