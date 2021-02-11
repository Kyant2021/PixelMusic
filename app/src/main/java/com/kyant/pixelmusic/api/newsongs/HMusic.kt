package com.kyant.pixelmusic.api.newsongs

data class HMusic(
    val volumeDelta: Int? = 0,
    val bitrate: Int? = 0,
    val sr: Int? = 0,
    val dfsId: Int? = 0,
    val playTime: Int? = 0,
    val name: Any? = Any(),
    val id: Long? = 0,
    val size: Int? = 0,
    val extension: String? = ""
)