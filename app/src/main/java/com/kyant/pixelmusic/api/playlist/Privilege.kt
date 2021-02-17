package com.kyant.pixelmusic.api.playlist

import kotlinx.serialization.Serializable

@Serializable
data class Privilege(
    val id: Long? = 0,
    val fee: Long? = 0,
    val payed: Long? = 0,
    val st: Long? = 0,
    val pl: Long? = 0,
    val dl: Long? = 0,
    val sp: Long? = 0,
    val cp: Long? = 0,
    val subp: Long? = 0,
    val cs: Boolean? = false,
    val maxbr: Long? = 0,
    val fl: Long? = 0,
    val toast: Boolean? = false,
    val flag: Long? = 0,
    val preSell: Boolean? = false,
    val playMaxbr: Long? = 0,
    val downloadMaxbr: Long? = 0,
    val freeTrialPrivilege: FreeTrialPrivilege? = FreeTrialPrivilege(),
    val chargeInfoList: List<ChargeInfo>? = listOf()
)