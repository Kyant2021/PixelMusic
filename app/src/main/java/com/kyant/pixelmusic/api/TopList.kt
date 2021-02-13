package com.kyant.pixelmusic.api

import com.beust.klaxon.Klaxon
import com.kyant.pixelmusic.api.toplist.TopList
import com.kyant.pixelmusic.api.toplist.TopListResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

typealias TopListId = Long

suspend fun findTopList(): List<TopList>? = withContext(Dispatchers.IO) {
    Klaxon().parse<TopListResult>(URL("$API/toplist").readText())?.list
}