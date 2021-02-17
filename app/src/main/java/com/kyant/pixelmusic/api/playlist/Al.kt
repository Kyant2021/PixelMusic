package com.kyant.pixelmusic.api.playlist

import kotlinx.serialization.Serializable

@Serializable
data class Al(
    val id: Long? = 0,
    val name: String? = "",
    val picUrl: String? = "",
    val tns: List<String>? = listOf(),
    val picStr: String? = "",
    val pic: Long? = 0
)