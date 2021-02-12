package com.kyant.pixelmusic.api

import com.kyant.pixelmusic.api.toplist.TopList
import com.kyant.pixelmusic.api.toplist.TopListResult
import com.kyant.pixelmusic.locals.JsonParser
import java.net.URL

typealias TopListId = Long

suspend fun findTopList(): List<TopList>? =
    JsonParser().parse<TopListResult>(URL("$API/toplist").readText())?.list