package com.kyant.pixelmusic.api.login

import kotlinx.serialization.Serializable

@Serializable
data class Account(
    val id: Long? = 0,
    val userName: String? = "",
    val type: Long? = 0,
    val status: Long? = 0,
    val whitelistAuthority: Long? = 0,
    val createTime: Long? = 0,
    val salt: String? = "",
    val tokenVersion: Long? = 0,
    val ban: Long? = 0,
    val baoyueVersion: Long? = 0,
    val donateVersion: Long? = 0,
    val vipType: Long? = 0,
    val viptypeVersion: Long? = 0,
    val anonimousUser: Boolean? = false
)