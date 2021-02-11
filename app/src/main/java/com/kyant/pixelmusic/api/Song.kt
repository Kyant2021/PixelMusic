package com.kyant.pixelmusic.api

import androidx.compose.runtime.*
import com.kyant.pixelmusic.api.song.SongResult
import com.kyant.pixelmusic.locals.JsonParser
import java.net.URL

typealias SongId = Long

suspend fun SongId.findUrl(): String? = JsonParser().parse<SongResult>(
    URL("$API2/song/url?id=${this@findUrl}").readText()
)?.data?.get(0)?.url

suspend fun List<SongId>.findUrls(): List<String?> {
    val result = JsonParser().parse<SongResult>(
        URL("$API2/song/url?id=${this@findUrls.joinToString()}").readText()
    )?.data?.map { it.id to it.url }?.toMap()
    val urls = mutableListOf<String?>()
    forEach {
        urls += result?.getValue(it)
    }
    return urls
}