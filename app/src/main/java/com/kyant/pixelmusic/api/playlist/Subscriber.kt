package com.kyant.pixelmusic.api.playlist

import kotlinx.serialization.Serializable

@Serializable
data class Subscriber(
    val defaultAvatar: Boolean? = false,
    val province: Long? = 0,
    val authStatus: Long? = 0,
    val followed: Boolean? = false,
    val avatarUrl: String? = "",
    val accountStatus: Long? = 0,
    val gender: Long? = 0,
    val city: Long? = 0,
    val birthday: Long? = 0,
    val userId: Long? = 0,
    val userType: Long? = 0,
    val nickname: String? = "",
    val signature: String? = "",
    val description: String? = "",
    val detailDescription: String? = "",
    val avatarImgId: Long? = 0,
    val backgroundImgId: Long? = 0,
    val backgroundUrl: String? = "",
    val authority: Long? = 0,
    val mutual: Boolean? = false,
    val expertTags: List<String>? = listOf(),
    val experts: LinkedHashMap<String, String>? = linkedMapOf(),
    val djStatus: Long? = 0,
    val vipType: Long? = 0,
    val remarkName: String? = "",
    val authenticationTypes: Long? = 0,
    val avatarDetail: String? = "",
    val anchor: Boolean? = false,
    val backgroundImgIdStr: String? = "",
    val avatarImgIdStr: String? = ""
)