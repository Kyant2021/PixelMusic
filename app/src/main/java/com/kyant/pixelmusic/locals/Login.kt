package com.kyant.pixelmusic.locals

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import com.kyant.pixelmusic.api.login.LoginResult
import com.kyant.pixelmusic.util.DataStore

val LocalLogin = staticCompositionLocalOf<LoginResult?> { null }

@Composable
fun ProvideLogin(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalLogin provides DataStore(LocalContext.current, "account").getJsonOrNull("login"),
        content = content
    )
}