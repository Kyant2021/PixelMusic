package com.kyant.pixelmusic.ui.player

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.ui.shape.SmoothRoundedCornerShape
import com.kyant.pixelmusic.ui.shape.SuperellipseCornerShape
import com.kyant.pixelmusic.locals.LocalPixelPlayer
import com.kyant.pixelmusic.data.Media
import com.kyant.pixelmusic.media.Song
import com.kyant.pixelmusic.ui.song.Cover

@Composable
fun PlayerPlaylistItem(
    index: Int,
    song: Song,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    val player = LocalPixelPlayer.current
    val transition = updateTransition(selected)
    val backgroundColor by transition.animateColor {
        if (it) MaterialTheme.colors.primary else MaterialTheme.colors.surface
    }
    val contentColor by transition.animateColor {
        if (it) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface
    }
    val contentPadding by transition.animateDp {
        if (it) 16.dp else 0.dp
    }
    val coverSize by transition.animateDp {
        if (it) 56.dp else 48.dp
    }
    Card(
        modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp),
        SuperellipseCornerShape(12.dp),
        backgroundColor,
        contentColor,
        elevation = 0.dp
    ) {
        Box(
            Modifier
                .clickable {
                    if (Media.browser.isConnected) {
                        Media.session?.isActive = true
                        if (!selected) {
                            player.seekTo(index, 0)
                        }
                        player.play()
                    }
                }
                .padding(contentPadding)
        ) {
            Row(
                Modifier.align(Alignment.CenterStart),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Cover(
                    song.icon,
                    song.albumId,
                    Modifier
                        .size(coverSize)
                        .clip(SmoothRoundedCornerShape())
                )
                Column(Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        song.title.toString(),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = MaterialTheme.typography.h6
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        song.subtitle.toString(),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = MaterialTheme.typography.caption
                    )
                }
            }
            if (selected) {
                PlayPauseButton(Modifier.align(Alignment.CenterEnd))
            }
        }
    }
}