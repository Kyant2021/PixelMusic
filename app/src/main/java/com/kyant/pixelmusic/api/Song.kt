package com.kyant.pixelmusic.api

import androidx.compose.runtime.*
import com.beust.klaxon.Klaxon
import com.kyant.pixelmusic.api.song.SongResult
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

typealias SongId = Long

suspend fun SongId.findUrl(): String? = withContext(Dispatchers.IO) {
    Klaxon().parse<SongResult>(HttpClient(CIO).get<String>("$API/song/url?id=${this@findUrl}"))
        ?.data?.getOrNull(0)?.url
}