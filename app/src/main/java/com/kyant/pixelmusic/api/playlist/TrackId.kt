package com.kyant.pixelmusic.api.playlist

import kotlinx.serialization.Serializable

@Serializable
data class TrackId(
    val id: Long? = 0,
    val v: Long? = 0,
    val at: Long? = 0,
    val alg: String? = "",
    val lr: Long? = 0,
    val ratio: Long? = 0
)