package com.kyant.pixelmusic.api

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*

const val API = "http://47.100.93.91:3000"
const val LoginAPI = "https://ncm-api.glitch.me"

val client = HttpClient(CIO)

val jsonClient = HttpClient(CIO) {
    install(JsonFeature) {
        serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
            ignoreUnknownKeys = true
        })
    }
}