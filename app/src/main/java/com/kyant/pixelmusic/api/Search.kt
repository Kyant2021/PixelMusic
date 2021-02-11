package com.kyant.pixelmusic.api

import com.kyant.pixelmusic.api.search.SearchResult
import com.kyant.pixelmusic.locals.JsonParser
import java.net.URL

suspend fun String.searchSongs(): SearchResult? =
    JsonParser().parse(URL("$API2/search?keywords=$this").readText())