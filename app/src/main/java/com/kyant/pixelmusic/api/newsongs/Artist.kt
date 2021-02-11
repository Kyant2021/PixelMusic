package com.kyant.pixelmusic.api.newsongs

data class Artist(
    val img1v1Id: Long? = 0,
    val topicPerson: Int? = 0,
    val musicSize: Int? = 0,
    val alias: List<Any>? = listOf(),
    val followed: Boolean? = false,
    val briefDesc: String? = "",
    val picId: Int? = 0,
    val trans: String? = "",
    val albumSize: Int? = 0,
    val img1v1Url: String? = "",
    val picUrl: String? = "",
    val name: String? = "",
    val id: Int? = 0,
    val img1v1Id_str: String? = ""
)