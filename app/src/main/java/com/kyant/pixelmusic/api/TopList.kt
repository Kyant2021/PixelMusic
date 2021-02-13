package com.kyant.pixelmusic.api

import com.beust.klaxon.Klaxon
import com.kyant.pixelmusic.api.toplist.TopList
import com.kyant.pixelmusic.api.toplist.TopListResult
import java.net.URL

typealias TopListId = Long

suspend fun findTopList(): List<TopList>? =
    Klaxon().parse<TopListResult>(URL("$API/toplist").readText())?.list