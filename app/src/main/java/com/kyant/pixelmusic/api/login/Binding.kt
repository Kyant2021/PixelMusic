package com.kyant.pixelmusic.api.login

import kotlinx.serialization.Serializable

@Serializable
data class Binding(
    val bindingTime: Long? = 0,
    val refreshTime: Long? = 0,
    val userId: Long? = 0,
    val url: String? = "",
    val tokenJsonStr: String? = "",
    val expiresIn: Long? = 0,
    val expired: Boolean? = false,
    val id: Long? = 0,
    val type: Long? = 0
)