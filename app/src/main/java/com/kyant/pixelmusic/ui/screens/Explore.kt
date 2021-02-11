package com.kyant.pixelmusic.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.api.findTopList
import com.kyant.pixelmusic.api.toplist.TopList
import com.kyant.pixelmusic.ui.playlist.TopSongItem
import kotlinx.coroutines.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Explore(
    state: SwipeableState<Boolean>,
    topList: MutableState<TopList?>,
    modifier: Modifier = Modifier
) {
    val topLists = remember { mutableStateListOf<TopList>() }
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            findTopList()?.let { topLists.addAll(it) }
        }
    }
    LazyColumn(
        modifier,
        contentPadding = PaddingValues(top = 64.dp, bottom = 128.dp)
    ) {
        item {
            Row(
                Modifier
                    .fillMaxWidth()
                    .clickable {},
                Arrangement.SpaceBetween,
                Alignment.CenterVertically
            ) {
                Text(
                    "Top Songs",
                    Modifier.padding(16.dp),
                    style = MaterialTheme.typography.body1
                )
                IconButton({}) {
                    Icon(Icons.Outlined.KeyboardArrowRight, "See more")
                }
            }
        }
        item {
            LazyRow(contentPadding = PaddingValues(64.dp, 16.dp)) {
                items(topLists) {
                    TopSongItem(it, {
                        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
                            state.animateTo(true)
                        }
                        topList.value = it
                    })
                }
            }
        }
    }
}