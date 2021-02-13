package com.kyant.pixelmusic.ui.player

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ClearAll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.locals.LocalNowPlaying
import com.kyant.pixelmusic.media.Media

@Composable
fun PlayerPlaylist(modifier: Modifier = Modifier) {
    Column(modifier) {
        Row(
            Modifier.fillMaxWidth(),
            Arrangement.SpaceBetween,
            Alignment.CenterVertically
        ) {
            Text(
                "Playlist",
                Modifier.padding(16.dp),
                style = MaterialTheme.typography.h5
            )
            OutlinedButton(
                { Media.clearPlaylist() },
                Modifier.padding(horizontal = 8.dp)
            ) {
                Icon(Icons.Outlined.ClearAll, "Clear all")
                Spacer(Modifier.width(8.dp))
                Text("Clear all")
            }
        }
        LazyColumn {
            if (!Media.songs.isNullOrEmpty()) {
                itemsIndexed(Media.songs, { _, song -> song.id.toString() }) { index, song ->
                    PlayerPlaylistItem(index, song, song.id == LocalNowPlaying.current.id)
                }
            }
        }
    }
}