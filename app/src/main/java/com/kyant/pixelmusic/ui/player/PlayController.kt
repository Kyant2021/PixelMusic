package com.kyant.pixelmusic.ui.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.locals.LocalPixelPlayer
import com.kyant.pixelmusic.locals.Media

@Composable
fun PlayController(modifier: Modifier = Modifier) {
    val player = LocalPixelPlayer.current
    Row(modifier) {
        IconButton({}) {
            Icon(Icons.Outlined.FavoriteBorder, "Favorite")
        }
        Spacer(Modifier.width(16.dp))
        IconButton({ player.previous() }) {
            Icon(Icons.Outlined.SkipPrevious, "Skip to prevoius")
        }
        Spacer(Modifier.width(16.dp))
        PlayPauseButton()
        Spacer(Modifier.width(16.dp))
        IconButton({ player.next() }) {
            Icon(Icons.Outlined.SkipNext, "Skip to next")
        }
        Spacer(Modifier.width(16.dp))
        IconButton({}) {
            Icon(Icons.Outlined.Info, "Info")
        }
    }
}

@Composable
fun PlayControllerCompact(modifier: Modifier = Modifier) {
    val player = LocalPixelPlayer.current
    Row(modifier) {
        IconButton({ player.previous() }) {
            Icon(Icons.Outlined.SkipPrevious, "Skip to prevoius")
        }
        PlayPauseButton()
        IconButton({ player.next() }) {
            Icon(Icons.Outlined.SkipNext, "Skip to next")
        }
    }
}

@Composable
fun PlayPauseButton(modifier: Modifier = Modifier) {
    val player = LocalPixelPlayer.current
    IconButton(
        {
            if (Media.browser.isConnected) {
                Media.session?.isActive = true
                player.playOrPause()
            }
        },
        modifier.background(
            MaterialTheme.colors.primary,
            RoundedCornerShape(50)
        )
    ) {
        Icon(
            if (player.isPlayingState) Icons.Outlined.Pause else Icons.Outlined.PlayArrow,
            if (player.isPlayingState) "Pause" else "Play",
            tint = MaterialTheme.colors.onPrimary
        )
    }
}