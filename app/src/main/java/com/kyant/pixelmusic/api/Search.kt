package com.kyant.pixelmusic.api

import com.beust.klaxon.Klaxon
import com.kyant.pixelmusic.api.search.SearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

suspend fun String.searchSongs(): SearchResult? = withContext(Dispatchers.IO) {
    Klaxon().parse(URL("$API/search?keywords=${this@searchSongs}").readText())
}