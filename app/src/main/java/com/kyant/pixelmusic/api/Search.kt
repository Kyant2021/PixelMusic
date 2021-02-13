package com.kyant.pixelmusic.api

import com.beust.klaxon.Klaxon
import com.kyant.pixelmusic.api.search.SearchResult
import java.net.URL

suspend fun String.searchSongs(): SearchResult? =
    Klaxon().parse(URL("$API/search?keywords=$this").readText())