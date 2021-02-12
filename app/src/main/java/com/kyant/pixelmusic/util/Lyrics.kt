package com.kyant.pixelmusic.util

import androidx.compose.runtime.Composable
import com.kyant.pixelmusic.api.Content
import com.kyant.pixelmusic.api.Lyrics
import com.kyant.pixelmusic.api.Time
import com.kyant.pixelmusic.locals.LocalPixelPlayer
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

fun String.toLyrics(): Lyrics {
    val lyrics = mutableMapOf<Time, Content>()
    split("\n").forEach { line ->
        if (line.isNotBlank() && line[1].isDigit()) {
            lyrics += line.substringBefore("]").drop(1) to line.substringAfter("]")
        }
    }
    return lyrics
}

@OptIn(ExperimentalTime::class)
fun Time.toMilliseconds(): Long =
    (substring(0, 2).toLong().toDuration(DurationUnit.MINUTES) +
            substring(3, 5).toLong().toDuration(DurationUnit.SECONDS) +
            substring(6, length - 1).toLong().toDuration(DurationUnit.MILLISECONDS) * (11 - length))
        .toLongMilliseconds()

@Composable
fun Time.isCurrentLine(lyrics: Lyrics): Boolean = this == lyrics.keys.lastOrNull {
    LocalPixelPlayer.current.position.value >= it.toMilliseconds()
}

@Composable
fun Lyrics.currentIndex(): Int = keys.lastIndexOf(keys.lastOrNull {
    LocalPixelPlayer.current.position.value >= it.toMilliseconds()
})