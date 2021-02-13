package com.kyant.pixelmusic.api

import com.beust.klaxon.Klaxon
import com.kyant.pixelmusic.api.toplist.TopList
import com.kyant.pixelmusic.api.toplist.TopListResult
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

typealias TopListId = Long

suspend fun findTopList(): List<TopList>? = withContext(Dispatchers.IO) {
    Klaxon().parse<TopListResult>(HttpClient(CIO).get<String>("$API/toplist"))?.list
}