package com.kyant.pixelmusic.ui.song

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.locals.LocalPixelPlayer
import com.kyant.pixelmusic.locals.Media
import com.kyant.pixelmusic.media.Song
import com.kyant.inimate.shape.SmoothRoundedCornerShape
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@Composable
fun Song(
    song: Song,
    modifier: Modifier = Modifier
) {
    val player = LocalPixelPlayer.current
    Row(
        modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
                    if (Media.browser.isConnected) {
                        val index = Media.songs.map { it.id }.indexOf(song.id)
                        if (index == -1) {
                            Media.addSongToPlaylist(
                                (player.currentWindowIndex + 1).coerceAtMost(Media.songs.size),
                                song
                            )
                            Media.session?.isActive = true
                            player.next()
                            player.seekTo(0)
                        } else {
                            if (Media.songs[player.currentWindowIndex].id != song.id) {
                                player.seekTo(index, 0)
                            }
                        }
                        player.play()
                    }
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Cover(
            song,
            Modifier
                .size(48.dp)
                .clip(SmoothRoundedCornerShape())
        )
        Column(Modifier.padding(horizontal = 16.dp)) {
            Text(
                song.title.toString(),
                fontWeight = FontWeight.Medium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.body1
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
}