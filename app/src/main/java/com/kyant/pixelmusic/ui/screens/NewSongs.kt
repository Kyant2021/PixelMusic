package com.kyant.pixelmusic.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kyant.inimate.layout.StaggeredGrid
import com.kyant.pixelmusic.api.findNewSongs
import com.kyant.pixelmusic.media.Song
import com.kyant.pixelmusic.ui.song.SongCompact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun NewSongs(modifier: Modifier = Modifier) {
    val songs = remember { mutableStateListOf<Song>() }
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            findNewSongs()?.onEach {
                songs += it
            }
        }
    }
    LazyColumn(
        modifier,
        contentPadding = PaddingValues(top = 64.dp, bottom = 128.dp)
    ) {
        item {
            Text(
                "New songs",
                Modifier.padding(16.dp),
                style = MaterialTheme.typography.body1
            )
        }
        item {
            Row(Modifier.horizontalScroll(rememberScrollState())) {
                StaggeredGrid {
                    songs.forEach {
                        SongCompact(it)
                    }
                }
            }
        }
    }
}