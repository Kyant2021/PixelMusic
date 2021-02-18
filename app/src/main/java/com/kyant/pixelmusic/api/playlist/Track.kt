package com.kyant.pixelmusic.api.playlist

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class Track(
    val name: String? = "",
    val id: Long? = 0,
    val pst: Long? = 0,
    val t: Long? = 0,
    val ar: List<Ar>? = listOf(),
    val alia: List<String>? = listOf(),
    val pop: Long? = 0,
    val st: Long? = 0,
    val rt: String? = "",
    val fee: Long? = 0,
    val v: Long? = 0,
    val crbt: String? = "",
    val cf: String? = "",
    val al: Al? = Al(),
    val dt: Long? = 0,
    val h: H? = H(),
    val m: M? = M(),
    val l: L? = L(),
    val a: String? = "",
    val cd: String? = "",
    val no: Long? = 0,
    val rtUrl: String? = "",
    val ftype: Long? = 0,
    val rtUrls: List<String>? = listOf(),
    val djId: Long? = 0,
    val copyright: Long? = 0,
    val sId: Long? = 0,
    val mark: Long? = 0,
    val originCoverType: Long? = 0,
    val originSongSimpleData: JsonObject? = JsonObject(mapOf()),
    val single: Long? = 0,
    val noCopyrightRcmd: JsonObject? = JsonObject(mapOf()),
    val rtype: Long? = 0,
    val rurl: String? = "",
    val mst: Long? = 0,
    val cp: Long? = 0,
    val mv: Long? = 0,
    val publishTime: Long? = 0,
    val tns: List<String>? = listOf()
)