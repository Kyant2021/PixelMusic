package com.kyant.pixelmusic.ui.nowplaying

import android.graphics.Bitmap
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.palette.graphics.Palette
import com.kyant.inimate.blur.blur
import com.kyant.inimate.insets.LocalWindowInsets
import com.kyant.inimate.insets.statusBarsPadding
import com.kyant.inimate.layer.FloatingForeLayer
import com.kyant.inimate.layer.progressOf
import com.kyant.inimate.shape.SuperellipseCornerShape
import com.kyant.pixelmusic.api.EmptyLyrics
import com.kyant.pixelmusic.api.findLyrics
import com.kyant.pixelmusic.locals.LocalNowPlaying
import com.kyant.pixelmusic.locals.LocalPixelPlayer
import com.kyant.pixelmusic.locals.LocalPreferences
import com.kyant.pixelmusic.ui.player.PlayController
import com.kyant.pixelmusic.ui.player.PlayPauseTransparentButton
import com.kyant.pixelmusic.ui.player.ProgressBar
import com.kyant.pixelmusic.ui.song.Cover
import com.kyant.pixelmusic.ui.theme.NowPlayingTheme
import com.kyant.pixelmusic.util.CacheDataStore
import com.kyant.pixelmusic.util.EmptyImage
import com.kyant.pixelmusic.util.loadCoverWithCache
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
    val context = LocalContext.current
    val density = LocalDensity.current
    val player = LocalPixelPlayer.current
    val song = LocalNowPlaying.current
    val preferences = LocalPreferences.current
    val scope = rememberCoroutineScope()
    var lyricsState by remember { mutableStateOf(false) }
    val infoState = rememberSwipeableState(false)
    val transition = updateTransition(lyricsState)
    val progress = state.progressOf(constraints.maxHeight.toFloat()).coerceIn(0f..1f)
    var cover: ImageBitmap? by remember { mutableStateOf(null) }
    val defaultColor = MaterialTheme.colors.surface
    val defaultOnColor = MaterialTheme.colors.onSurface
    var themeColor by remember { mutableStateOf(defaultColor) }
    var onColor by remember { mutableStateOf(defaultOnColor) }
    val squareSize = minOf(maxWidth, maxHeight)
    var lyrics by remember { mutableStateOf(EmptyLyrics) }
    var blurredImage: ImageBitmap? by remember { mutableStateOf(null) }
    LaunchedEffect(song.id) {
        lyrics = (song.id?.findLyrics() ?: EmptyLyrics).toList().sortedBy { it.first }.toMap()
    }
    LaunchedEffect(song.albumId) {
        if (!CacheDataStore(context, "covers").contains("${song.albumId}_500.jpg")) {
            cover = song.icon ?: song.albumId?.loadCoverWithCache(context, 100)
        }
        cover = song.albumId?.loadCoverWithCache(context, 500)
    }
    LaunchedEffect(cover) {
        withContext(Dispatchers.IO) {
            blurredImage = cover?.blur(150)
            blurredImage?.asAndroidBitmap()?.copy(Bitmap.Config.ARGB_8888, true)?.let { bitmap ->
                Palette.from(bitmap).generate { palette ->
                    themeColor = Color(palette?.dominantSwatch?.rgb ?: defaultColor.toArgb())
                    onColor = if (themeColor.luminance() <= 0.5f) Color.White else Color.Black
                }
            }
        }
    }
    NowPlayingTheme(
        color = if (preferences.improveAccessibility) MaterialTheme.colors.surface else themeColor,
        onColor = if (preferences.improveAccessibility) MaterialTheme.colors.onSurface else onColor
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
            shape = RoundedCornerShape(12.dp * (1f - progress) + preferences.screenCornerSizeDp.dp * progress),
            backgroundColor = Color.Transparent,
            elevation = 1.dp + 23.dp * progress
        ) {
            BoxWithConstraints(
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.surface)
            ) {
                var horizontalDragOffset by remember { mutableStateOf(0f) }.apply {
                    with(density) { value.coerceIn(-48.dp.toPx()..48.dp.toPx()) }
                }
                if (!preferences.improveAccessibility) {
                    Image(
                        blurredImage ?: EmptyImage, null,
                        Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
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
                        ProgressBar(progress, Modifier.padding(32.dp, 8.dp))
                    }
                }
                Box(Modifier.padding(top = 12.dp)) {
                    Cover(
                        cover,
                        modifier = Modifier
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
                            .zIndex(1f)
                    )
                    Column(
                        Modifier
                            .offset(
                                80.dp + 16.dp * progress - transition.animateDp { if (it) 8.dp * progress else 0.dp }.value,
                                4.dp + (squareSize + 68.dp) * progress - transition.animateDp { if (it) (squareSize + 48.dp) * progress else 0.dp }.value
                            )
                            .fillMaxWidth()
                            .draggable(
                                rememberDraggableState { horizontalDragOffset += it },
                                Orientation.Horizontal,
                                onDragStopped = {
                                    with(density) {
                                        when {
                                            horizontalDragOffset <= -48.dp.toPx() -> player.seekToNext()
                                            horizontalDragOffset >= 48.dp.toPx() -> player.seekToPrevious()
                                        }
                                    }
                                    horizontalDragOffset = 0f
                                }
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