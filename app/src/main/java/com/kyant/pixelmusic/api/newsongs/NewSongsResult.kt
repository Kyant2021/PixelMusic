package com.kyant.pixelmusic.api.newsongs

data class NewSongsResult(
    val data: List<Data>? = listOf(),
    val code: Int? = 0
)