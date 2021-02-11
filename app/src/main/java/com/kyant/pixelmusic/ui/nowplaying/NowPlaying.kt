package com.kyant.pixelmusic.ui.nowplaying

import android.graphics.Bitmap
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import com.kyant.inimate.insets.LocalWindowInsets
import com.kyant.inimate.insets.statusBarsPadding
import com.kyant.inimate.layer.FloatingForeLayer
import com.kyant.inimate.layer.progressOf
import com.kyant.inimate.shape.SuperellipseCornerShape
import com.kyant.pixelmusic.api.EmptyLyrics
import com.kyant.pixelmusic.api.findLyrics
import com.kyant.pixelmusic.locals.LocalNowPlaying
import com.kyant.pixelmusic.locals.LocalPixelPlayer
import com.kyant.pixelmusic.ui.player.PlayController
import com.kyant.pixelmusic.ui.player.PlayPauseTransparentButton
import com.kyant.pixelmusic.ui.player.ProgressBar
import com.kyant.pixelmusic.ui.song.Cover
import com.kyant.pixelmusic.ui.theme.NowPlayingTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.absoluteValue

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
fun BoxWithConstraintsScope.NowPlaying(
    state: SwipeableState<Boolean>,
    playlistState: SwipeableState<Boolean>,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    val player = LocalPixelPlayer.current
    val song = LocalNowPlaying.current
    val scope = rememberCoroutineScope()
    var horizontalDragOffset by remember { mutableStateOf(0f) }
    var lyricsState by remember { mutableStateOf(false) }
    val infoState = rememberSwipeableState(false)
    val transition = updateTransition(lyricsState)
    val progress = state.progressOf(constraints.maxHeight.toFloat()).coerceIn(0f..1f)
    val isLight = MaterialTheme.colors.isLight
    val defaultColor = MaterialTheme.colors.surface
    var colors by remember { mutableStateOf(listOf(defaultColor, defaultColor)) }
    var themeColor by remember { mutableStateOf(defaultColor) }
    val squareSize = minOf(maxWidth, maxHeight)
    var lyrics by remember { mutableStateOf(EmptyLyrics) }
    LaunchedEffect(song.id) {
        withContext(Dispatchers.IO) {
            lyrics = (song.id?.findLyrics() ?: EmptyLyrics).toList().sortedBy { it.first }.toMap()
        }
    }
    LaunchedEffect(song.icon) {
        withContext(Dispatchers.IO) {
            song.icon?.asAndroidBitmap()?.copy(Bitmap.Config.ARGB_8888, true)?.let { bitmap ->
                Palette.from(bitmap).generate { palette ->
                    colors = listOf(
                        Color(
                            (if (isLight) palette?.lightMutedSwatch?.rgb else palette?.darkMutedSwatch?.rgb)
                                ?: defaultColor.toArgb()
                        ),
                        Color(
                            (if (isLight) palette?.lightVibrantSwatch?.rgb else palette?.darkVibrantSwatch?.rgb)
                                ?: defaultColor.toArgb()
                        )
                    )
                    themeColor = Color(palette?.dominantSwatch?.rgb ?: defaultColor.toArgb())
                }
            }
        }
    }
    NowPlayingTheme(
        color = themeColor,
        onColor = if (colors.map { it.luminance() }.average() <= 0.5f) Color.White else Color.Black
    ) {
        Card(
            modifier
                .size(
                    256.dp + (maxWidth - 256.dp) * progress,
                    72.dp + (maxHeight - 72.dp) * progress
                )
                .align(Alignment.BottomStart)
                .offset(
                    16.dp * (1f - progress),
                    -(with(density) { LocalWindowInsets.current.navigationBars.bottom.toDp() } + 16.dp) * (1f - progress)
                )
                .swipeable(
                    state,
                    mapOf(
                        0f to true,
                        constraints.maxHeight.toFloat() to false
                    ),
                    Orientation.Vertical
                )
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            scope.launch {
                                playlistState.animateTo(
                                    !playlistState.currentValue,
                                    spring(stiffness = 700f)
                                )
                            }
                        }
                    ) {
                        scope.launch {
                            state.animateTo(true, spring(stiffness = 700f))
                        }
                    }
                },
            shape = RoundedCornerShape(12.dp * (1f - progress)),
            backgroundColor = Color.Transparent,
            elevation = 1.dp + 23.dp * progress
        ) {
            BoxWithConstraints(
                Modifier
                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            0f to animateColorAsState(colors[0]).value,
                            1f to animateColorAsState(colors[1]).value
                        )
                    )
            ) {
                Column(
                    Modifier.fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .offset(y = maxHeight / 1.5f * (1f - (progress - 0.5f).coerceAtLeast(0f) * 2))
                        .alpha(transition.animateFloat {
                            if (it) -((progress - 0.5f).coerceAtLeast(0f) * 2)
                            else (progress - 0.5f).coerceAtLeast(0f) * 2
                        }.value),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Divider(
                            Modifier.padding(8.dp),
                            MaterialTheme.colors.onSurface.copy(0.08f)
                        )
                        PlayController(
                            onFavoriteButtonClick = {},
                            onInfoButtonClick = {
                                scope.launch {
                                    infoState.animateTo(true)
                                }
                            },
                            Modifier.padding(16.dp)
                        )
                        ProgressBar(Modifier.padding(32.dp, 8.dp))
                    }
                }
                Box(
                    Modifier.draggable(
                        rememberDraggableState {
                            horizontalDragOffset += it
                            with(density) {
                                when {
                                    horizontalDragOffset <= -48.dp.toPx() -> player.next()
                                    horizontalDragOffset >= 48.dp.toPx() -> player.previous()
                                }
                            }
                        },
                        Orientation.Horizontal,
                        onDragStopped = { horizontalDragOffset = 0f }
                    ).padding(top = 12.dp)
                ) {
                    Cover(
                        song,
                        Modifier
                            .padding(horizontal = 16.dp * (1f - progress))
                            .size(
                                48.dp + ((squareSize - 96.dp) * progress - transition.animateDp { if (it) (squareSize - 96.dp) * progress else 0.dp }.value)
                                    .coerceAtLeast(0.dp)
                            )
                            .offset(
                                24.dp * progress,
                                68.dp * progress - transition.animateDp { if (it) 44.dp * progress else 0.dp }.value
                            )
                            .clip(SuperellipseCornerShape(8.dp + 8.dp * progress - transition.animateDp { if (it) 8.dp * progress else 0.dp }.value))
                            .pointerInput(Unit) {
                                detectTapGestures {
                                    if (state.currentValue) {
                                        lyricsState = !lyricsState
                                    } else {
                                        player.playOrPause()
                                    }
                                }
                            }
                    )
                    Column(
                        Modifier
                            .offset(
                                80.dp + 16.dp * progress - transition.animateDp { if (it) 8.dp * progress else 0.dp }.value,
                                4.dp + (squareSize + 68.dp) * progress - transition.animateDp { if (it) (squareSize + 48.dp) * progress else 0.dp }.value
                            )
                            .alpha((progress - 0.5f).absoluteValue * 2)
                    ) {
                        Text(
                            song.title.toString(),
                            fontWeight = FontWeight.Medium,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            style = if (progress <= 0.5f) MaterialTheme.typography.body1
                            else MaterialTheme.typography.h6
                        )
                        Text(
                            song.subtitle.toString(),
                            fontWeight = FontWeight.Medium,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            style = MaterialTheme.typography.caption
                        )
                    }
                }
                AnimatedVisibility(
                    lyricsState && state.targetValue,
                    Modifier
                        .align(Alignment.TopEnd)
                        .statusBarsPadding()
                        .padding(16.dp, 8.dp),
                    enter = slideInHorizontally({ constraints.maxWidth }),
                    exit = fadeOut(animationSpec = spring(stiffness = Spring.StiffnessHigh))
                ) {
                    PlayPauseTransparentButton()
                }
                AnimatedVisibility(
                    lyricsState,
                    Modifier
                        .fillMaxSize()
                        .padding(top = 96.dp),
                    enter = slideInVertically({ constraints.maxHeight }),
                    exit = fadeOut(animationSpec = spring(stiffness = Spring.StiffnessHigh))
                ) {
                    Lyrics(
                        lyrics,
                        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 48.dp)
                    )
                }
            }
        }
        FloatingForeLayer(infoState) {
            Info(song)
        }
    }
}