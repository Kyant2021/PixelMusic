package com.kyant.pixelmusic.api

import com.beust.klaxon.Klaxon
import com.kyant.pixelmusic.api.search.SearchResult
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun String.searchSongs(): SearchResult? = withContext(Dispatchers.IO) {
    Klaxon().parse(HttpClient(CIO).get<String>("$API/search?keywords=${this@searchSongs}"))
}