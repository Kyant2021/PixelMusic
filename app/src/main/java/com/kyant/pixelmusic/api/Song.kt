package com.kyant.pixelmusic.api

import androidx.compose.runtime.*
import com.beust.klaxon.Klaxon
import com.kyant.pixelmusic.api.song.SongResult
import java.net.URL

typealias SongId = Long

suspend fun SongId.findUrl(): String? = Klaxon().parse<SongResult>(
    URL("$API/song/url?id=${this@findUrl}").readText()
)?.data?.get(0)?.url

suspend fun List<SongId>.findUrls(): List<String?> {
    val result = Klaxon().parse<SongResult>(
        URL("$API/song/url?id=${this@findUrls.joinToString()}").readText()
    )?.data?.map { it.id to it.url }?.toMap()
    val urls = mutableListOf<String?>()
    forEach {
        urls += result?.getValue(it)
    }
    return urls
}