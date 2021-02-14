package com.kyant.pixelmusic.api.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginResult(
    val loginType: Long? = 0,
    val code: Long? = 0,
    val account: Account? = Account(),
    val token: String? = "",
    val profile: Profile? = Profile(),
    val bindings: List<Binding>? = listOf(),
    val cookie: String? = ""
)