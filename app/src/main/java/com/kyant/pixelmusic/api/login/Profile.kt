package com.kyant.pixelmusic.api.login

import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val description: String? = "",
    val followed: Boolean? = false,
    val backgroundUrl: String? = "",
    val detailDescription: String? = "",
    val userId: Long? = 0,
    val avatarImgIdStr: String? = "",
    val backgroundImgIdStr: String? = "",
    val userType: Long? = 0,
    val vipType: Long? = 0,
    val gender: Long? = 0,
    val accountStatus: Long? = 0,
    val djStatus: Long? = 0,
    val mutual: Boolean? = false,
    val remarkName: String? = "",
    val expertTags: String? = "",
    val authStatus: Long? = 0,
    val experts: Experts? = Experts(),
    val nickname: String? = "",
    val avatarImgId: Long? = 0,
    val birthday: Long? = 0,
    val city: Long? = 0,
    val backgroundImgId: Long? = 0,
    val avatarUrl: String? = "",
    val defaultAvatar: Boolean? = false,
    val province: Long? = 0,
    val signature: String? = "",
    val authority: Long? = 0,
    val followeds: Long? = 0,
    val follows: Long? = 0,
    val eventCount: Long? = 0,
    val avatarDetail: String? = "",
    val playlistCount: Long? = 0,
    val playlistBeSubscribedCount: Long? = 0
)