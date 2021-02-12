package com.kyant.pixelmusic.ui.nowplaying

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kyant.inimate.insets.navigationBarsPadding
import com.kyant.inimate.shape.SuperellipseCornerShape
import com.kyant.pixelmusic.api.Lyrics
import com.kyant.pixelmusic.locals.LocalPixelPlayer
import com.kyant.pixelmusic.util.currentIndex
import com.kyant.pixelmusic.util.indexOf
import com.kyant.pixelmusic.util.isCurrentLine
import com.kyant.pixelmusic.util.toMilliseconds
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.pow

@Composable
fun Lyrics(
    lyrics: Lyrics,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues()
) {
    val density = LocalDensity.current
    val player = LocalPixelPlayer.current
    val scope = rememberCoroutineScope()
    val state = rememberLazyListState()
    var dragOffset by remember { mutableStateOf(0f) }
    val currentIndex = lyrics.currentIndex()
    val transition = updateTransition(currentIndex)
    if (lyrics.isNotEmpty()) {
        LaunchedEffect(lyrics) {
            state.scrollToItem((currentIndex - 1).coerceAtLeast(0))
        }
        LaunchedEffect(currentIndex) {
            state.animateScrollToItem((currentIndex - 1).coerceAtLeast(0))
        }
    }
    BoxWithConstraints(modifier.fillMaxSize()) {
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
            state,
            contentPadding
        ) {
            itemsIndexed(lyrics.toList(), { _, lyric -> lyric.first }) { index, (time, lyric) ->
                val deltaIndex = ((index - currentIndex).absoluteValue).coerceAtMost(6)
                val isCurrentLine = time.isCurrentLine(lyrics.toMap())
                val offset = transition.animateDp({ spring(stiffness = 20f * (6 - deltaIndex)) }) {
                    deltaIndex.toFloat().pow(2.5f).dp
                }
                Box(
                    Modifier
                        .fillMaxWidth()
                        .offset(y = offset.value)
                        .clip(SuperellipseCornerShape(16.dp))
                        .clickable { player.seekToPosition(time.toMilliseconds() + 1) }
                        .padding(16.dp)
                ) {
                    Text(
                        lyric,
                        Modifier.align(Alignment.CenterStart)
                            .alpha(
                                animateFloatAsState(
                                    if (isCurrentLine) 1f
                                    else if (!player.isPlayingState || dragOffset != 0f) 0.5f
                                    else (0.4f * (6 - deltaIndex) / 6).coerceAtLeast(0.1f)
                                ).value
                            ),
                        fontWeight = FontWeight.Black,
                        style = MaterialTheme.typography.h5.copy(
                            fontSize = MaterialTheme.typography.h5.fontSize * animateFloatAsState(if (isCurrentLine) 1.1f else 1f).value
                        )
                    )
                }
            }
        }
        BoxWithConstraints(
            modifier
                .fillMaxHeight()
                .navigationBarsPadding()
                .padding(bottom = 16.dp)
                .align(Alignment.TopEnd)
        ) {
            Box(
                Modifier
                    .size(24.dp)
                    .offset(
                        (-8).dp,
                        (maxHeight * player.progress + with(density) { dragOffset.toDp() }).coerceIn(
                            0.dp..maxHeight
                        )
                    )
                    .draggable(
                        rememberDraggableState {
                            dragOffset += it
                            scope.launch {
                                state.scrollToItem(
                                    (((maxHeight * player.progress + with(density) { dragOffset.toDp() }) / maxHeight * player.duration).toLong()
                                        .indexOf(lyrics)).coerceAtLeast(0)
                                )
                            }
                        },
                        Orientation.Vertical,
                        onDragStopped = {
                            player.seekToPosition(((maxHeight * player.progress + with(density) { dragOffset.toDp() }) / maxHeight * player.duration).toLong())
                            dragOffset = 0f
                        }
                    )
            ) {
                Card(
                    Modifier
                        .size(4.dp, 24.dp)
                        .align(Alignment.Center),
                    RoundedCornerShape(50),
                    MaterialTheme.colors.secondary
                ) {}
            }
        }
    }
}