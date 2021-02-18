package com.kyant.pixelmusic.ui.song

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.data.Media
import com.kyant.pixelmusic.locals.LocalPixelPlayer
import com.kyant.pixelmusic.media.Song
import com.kyant.pixelmusic.media.fix
import com.kyant.pixelmusic.ui.blur.blur
import com.kyant.pixelmusic.ui.shape.SuperellipseCornerShape
import com.kyant.pixelmusic.ui.sharedelements.SharedMaterialContainer
import com.kyant.pixelmusic.util.CacheDataStore
import com.kyant.pixelmusic.util.EmptyImage
import com.kyant.pixelmusic.util.loadCoverWithCache
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@Composable
fun Song(song: Song, modifier: Modifier = Modifier, onLongClick: (() -> Unit)? = null) {
    BaseSong(
        song,
        modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(64.dp)
            .clip(RoundedCornerShape(8.dp)),
        onLongClick
    )
}

@Composable
fun SongCompact(song: Song, modifier: Modifier = Modifier) {
    BaseSong(
        song,
        modifier
            .padding(8.dp)
            .padding(horizontal = 16.dp)
            .widthIn(max = 256.dp)
            .height(72.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colors.onSurface.copy(0.02f))
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BaseSong(song: Song, modifier: Modifier = Modifier, onLongClick: (() -> Unit)? = null) {
    val context = LocalContext.current
    val player = LocalPixelPlayer.current
    var cover by remember { mutableStateOf(EmptyImage) }
    LaunchedEffect(song.albumId) {
        if (!CacheDataStore(context, "covers").contains("${song.albumId}_100.jpg")) {
            cover = song.albumId?.loadCoverWithCache(context, 8)?.blur(8) ?: EmptyImage
        }
        cover = song.albumId?.loadCoverWithCache(context, 100) ?: EmptyImage
    }
    SharedMaterialContainer(song.id.toString(), "song") {
        Row(
            modifier.combinedClickable(onLongClick = onLongClick) {
                CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
                    if (Media.browser.isConnected) {
                        val index = Media.songs.map { it.id }.indexOf(song.id)
                        if (index == -1) {
                            Media.addSongToPlaylist(
                                (player.currentWindowIndex + 1).coerceAtMost(Media.songs.size),
                                song.fix(context)
                            )
                            Media.session?.isActive = true
                            player.seekToNext(0)
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
                cover,
                song.albumId,
                Modifier
                    .size(48.dp)
                    .clip(SuperellipseCornerShape(8.dp))
            )
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text(
                    song.title.orEmpty(),
                    fontWeight = FontWeight.Medium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.body1
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    song.subtitle.orEmpty(),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}

@Composable
fun ExpandedSong(song: Song, fraction: Float, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var cover by remember { mutableStateOf(EmptyImage) }
    LaunchedEffect(song.albumId) {
        if (!CacheDataStore(context, "covers").contains("${song.albumId}_100.jpg")) {
            cover = song.albumId?.loadCoverWithCache(context, 8)?.blur(8) ?: EmptyImage
        }
        cover = song.albumId?.loadCoverWithCache(context, 100) ?: EmptyImage
    }
    Column(
        modifier
            .size(48.dp + (256.dp - 48.dp) * fraction)
            .padding(16.dp)
    ) {
        Cover(
            cover,
            song.albumId,
            Modifier
                .size(128.dp)
                .clip(SuperellipseCornerShape(16.dp))
        )
        Text(
            song.title.orEmpty(),
            fontWeight = FontWeight.Medium,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.body1
        )
        Spacer(Modifier.height(4.dp))
        Text(
            song.subtitle.orEmpty(),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.caption
        )
    }
}