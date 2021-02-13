package com.kyant.pixelmusic.locals

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import com.kyant.pixelmusic.media.Media
import com.kyant.pixelmusic.media.Song

val LocalNowPlaying = compositionLocalOf { Song() }

@Composable
fun ProvideNowPlaying(index: Int?, content: @Composable () -> Unit) {
    index?.let {
        CompositionLocalProvider(
            LocalNowPlaying provides Media.songs.getOrElse(index) { Song() },
            content = content
        )
    }
}