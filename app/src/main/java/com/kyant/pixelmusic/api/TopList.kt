package com.kyant.pixelmusic.api

import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable

typealias TopListId = Long

suspend fun findTopList(): List<TopList>? = withContext(Dispatchers.IO) {
    jsonClient.get<TopListResult>("$API/toplist").list
}

@Serializable
data class TopListResult(
    val code: Int? = 0,
    val list: List<TopList>? = listOf()
)

@Serializable
data class TopList(
    val updateFrequency: String? = "",
    val name: String? = "",
    val id: Long? = 0,
    val coverImgUrl: String? = ""
)