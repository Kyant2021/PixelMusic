package com.kyant.pixelmusic.api.playlist

import kotlinx.serialization.Serializable

@Serializable
data class AvatarDetail(
    val userType: Long? = 0,
    val identityLevel: Long? = 0,
    val identityIconUrl: String? = ""
)