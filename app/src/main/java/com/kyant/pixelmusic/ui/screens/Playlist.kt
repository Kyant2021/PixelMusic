package com.kyant.pixelmusic.ui.screens

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.api.findPlaylist
import com.kyant.pixelmusic.api.playlist.Playlist
import com.kyant.pixelmusic.locals.LocalLogin
import com.kyant.pixelmusic.media.Song
import com.kyant.pixelmusic.media.toSong
import com.kyant.pixelmusic.ui.shape.SuperellipseCornerShape
import com.kyant.pixelmusic.ui.song.Song
import com.kyant.pixelmusic.util.EmptyImage
import com.kyant.pixelmusic.util.loadImage

@Composable
fun Playlist(playlist: Playlist?, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val login = LocalLogin.current
    var result by remember(playlist?.id) { mutableStateOf(Playlist()) }
    var image by remember(playlist?.id) { mutableStateOf(EmptyImage) }
    val songs = remember(playlist?.id) { mutableStateListOf<Song>() }
    LaunchedEffect(playlist?.id) {
        playlist?.id?.findPlaylist(login?.cookie)?.playlist?.let { result = it }
        image = result.coverImgUrl?.loadImage(context) ?: EmptyImage
        println(result.coverImgUrl)
        songs.addAll(result.tracks?.map { it.toSong() } ?: emptyList())
    }
    LazyColumn(modifier) {
        item {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    image,
                    result.name.orEmpty(),
                    modifier
                        .padding(16.dp)
                        .size(160.dp)
                        .clip(SuperellipseCornerShape(8.dp))
                )
                Column(Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        result.name.orEmpty(),
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
            Song(it)
        }
    }
}