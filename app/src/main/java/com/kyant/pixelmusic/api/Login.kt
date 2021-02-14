package com.kyant.pixelmusic.api

import com.kyant.pixelmusic.api.login.LoginResult
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun login(phone: String, md5: String): LoginResult = withContext(Dispatchers.IO) {
    jsonClient.get("$LoginAPI/login/cellphone?phone=$phone&md5_password=$md5")
}