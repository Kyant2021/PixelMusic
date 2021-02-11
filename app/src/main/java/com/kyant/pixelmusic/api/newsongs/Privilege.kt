package com.kyant.pixelmusic.api.newsongs

data class Privilege(
    val id: Int? = 0,
    val fee: Int? = 0,
    val payed: Int? = 0,
    val st: Int? = 0,
    val pl: Int? = 0,
    val dl: Int? = 0,
    val sp: Int? = 0,
    val cp: Int? = 0,
    val subp: Int? = 0,
    val cs: Boolean? = false,
    val maxbr: Int? = 0,
    val fl: Int? = 0,
    val toast: Boolean? = false,
    val flag: Int? = 0,
    val preSell: Boolean? = false
)