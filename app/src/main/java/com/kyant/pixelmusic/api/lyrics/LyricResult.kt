package com.kyant.pixelmusic.api.lyrics

data class LyricResult(
    val sgc: Boolean? = false,
    val sfy: Boolean? = false,
    val qfy: Boolean? = false,
    val lrc: Lrc? = Lrc(),
    val klyric: Klyric? = Klyric(),
    val tlyric: Tlyric? = Tlyric(),
    val code: Int? = 0
)