package com.kyant.pixelmusic.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.rememberSwipeableState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kyant.inimate.layer.BackLayer
import com.kyant.inimate.layer.ForeLayer
import com.kyant.inimate.shape.SuperellipseCornerShape
import com.kyant.pixelmusic.api.TopList
import com.kyant.pixelmusic.api.findTopList
import com.kyant.pixelmusic.util.EmptyImage
import com.kyant.pixelmusic.util.loadImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun Leaderboards() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val state = rememberSwipeableState(false)
    var topList by remember { mutableStateOf<TopList?>(null) }
    val topLists = remember { mutableStateListOf<TopList>() }
    LaunchedEffect(Unit) {
        topLists.addAll(findTopList() ?: emptyList())
    }
    BackHandler(state.targetValue) {
        scope.launch {
            state.animateTo(false, spring(stiffness = 700f))
        }
    }
    BackLayer(state) {
        Column(Modifier.padding(top = 24.dp)) {
            Text(
                "Leaderboards",
                Modifier.padding(16.dp),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.h5
            )
            LazyVerticalGrid(
                GridCells.Fixed(3),
                contentPadding = PaddingValues(8.dp, 8.dp, 8.dp, 24.dp)
            ) {
                items(topLists) {
                    var image by remember { mutableStateOf(EmptyImage) }
                    LaunchedEffect(it.id) {
                        image = it.coverImgUrl?.loadImage(context) ?: EmptyImage
                    }
                    Image(
                        image,
                        it.name.orEmpty(),
                        Modifier
                            .aspectRatio(1f)
                            .padding(8.dp)
                            .clip(SuperellipseCornerShape(8.dp))
                            .clickable {
                                scope.launch {
                                    topList = it
                                    state.animateTo(true, spring(stiffness = 700f))
                                }
                            },
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
    ForeLayer(state) {
        TopList(topList)
    }
}