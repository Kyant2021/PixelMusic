package com.kyant.pixelmusic.api

import com.kyant.pixelmusic.media.Song
import com.kyant.pixelmusic.media.toSong
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable

suspend fun findNewReleases(type: Int = 0): List<Song>? = withContext(Dispatchers.IO) {
    jsonClient.get<NewReleasesResult>("$API/top/song?type=$type").data?.map { it.toSong() }
}

@Serializable
data class NewReleasesResult(
    val data: List<Data>? = listOf(),
    val code: Int? = 0
)