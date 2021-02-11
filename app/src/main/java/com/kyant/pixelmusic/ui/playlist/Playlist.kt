package com.kyant.pixelmusic.ui.playlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PlaylistPlay
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kyant.inimate.shape.SuperellipseCornerShape
import com.kyant.pixelmusic.api.findPlaylist
import com.kyant.pixelmusic.api.toplist.TopList
import com.kyant.pixelmusic.media.Song
import com.kyant.pixelmusic.media.toSong
import com.kyant.pixelmusic.ui.song.Song
import com.kyant.pixelmusic.util.EmptyImage
import com.kyant.pixelmusic.util.loadImage
import com.kyant.pixelmusic.util.loadCoverWithCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun Playlist(
    topList: MutableState<TopList?>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var image by remember(topList.value?.id) { mutableStateOf(EmptyImage) }
    val songs = remember(topList.value?.id) { mutableStateListOf<Song>() }
    val icons = remember(topList.value?.id) { mutableStateMapOf<Long, ImageBitmap>() }
    LaunchedEffect(topList.value?.id) {
        withContext(Dispatchers.IO) {
            image = topList.value?.coverImgUrl?.loadImage(context) ?: EmptyImage
            topList.value?.id?.findPlaylist()?.playlist?.tracks?.apply {
                forEach {
                    songs += it.toSong()
                }
                parallelStream().forEachOrdered {
                    launch {
                        icons[it.al?.id ?: 0] =
                            it.al?.id?.loadCoverWithCache(context, "covers", 100) ?: EmptyImage
                    }
                }
            }
        }
    }
    LazyColumn(modifier) {
        item {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    image,
                    topList.value?.name.orEmpty(),
                    modifier
                        .padding(16.dp)
                        .size(160.dp)
                        .clip(SuperellipseCornerShape(8.dp))
                )
                Column(Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        topList.value?.name.orEmpty(),
                        Modifier.padding(16.dp),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = MaterialTheme.typography.h5
                    )
                    OutlinedButton(
                        {},
                        Modifier.padding(horizontal = 8.dp)
                    ) {
                        Icon(Icons.Outlined.PlaylistPlay, "Play all")
                        Spacer(Modifier.width(8.dp))
                        Text("Play all")
                    }
                }
            }
        }
        items(songs, { it.id?.toString().orEmpty() }) {
            Song(it.copy(icon = icons.getOrElse(it.albumId ?: 0) { EmptyImage }))
        }
    }
}