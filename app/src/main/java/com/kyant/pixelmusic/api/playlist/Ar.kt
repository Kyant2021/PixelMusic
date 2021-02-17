package com.kyant.pixelmusic.api.playlist

import kotlinx.serialization.Serializable

@Serializable
data class Ar(
    val id: Long? = 0,
    val name: String? = "",
    val tns: List<String>? = listOf(),
    val alias: List<String>? = listOf()
)