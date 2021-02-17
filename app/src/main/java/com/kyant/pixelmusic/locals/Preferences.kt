package com.kyant.pixelmusic.locals

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext
import com.kyant.pixelmusic.data.Preferences
import com.kyant.pixelmusic.util.DataStore

val DefaultPreferences = Preferences()

val LocalPreferences = compositionLocalOf { DefaultPreferences }

@Composable
fun ProvidePreferences(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalPreferences provides (DataStore(LocalContext.current, "settings")
            .getJsonOrNull("preferences") ?: DefaultPreferences),
        content = content
    )
}