package com.kyant.pixelmusic.api.playlist

import kotlinx.serialization.Serializable

@Serializable
data class ChargeInfo(
    val rate: Long? = 0,
    val chargeUrl: String? = "",
    val chargeMessage: String? = "",
    val chargeType: Long? = 0
)