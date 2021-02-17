package com.kyant.pixelmusic.api.playlist

import kotlinx.serialization.Serializable

@Serializable
data class FreeTrialPrivilege(
    val resConsumable: Boolean? = false,
    val userConsumable: Boolean? = false
)