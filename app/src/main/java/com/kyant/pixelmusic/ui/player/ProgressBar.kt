package com.kyant.pixelmusic.ui.player

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.locals.LocalPixelPlayer
import com.kyant.pixelmusic.util.minus
import com.kyant.pixelmusic.util.times
import com.kyant.pixelmusic.util.toOffsetTimeString
import com.kyant.pixelmusic.util.toPositiveTimeString
import java.util.concurrent.TimeUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

@OptIn(ExperimentalAnimationApi::class, ExperimentalTime::class)
@Composable
fun ProgressBar(modifier: Modifier = Modifier) {
    val player = LocalPixelPlayer.current
    var width by remember { mutableStateOf(0) }
    BoxWithConstraints(modifier.fillMaxWidth()) {
        var draggingOffset by remember { mutableStateOf(0f) }.apply {
            value = value.coerceIn(((0f..1f) - player.progress) * constraints.maxWidth.toFloat())
        }
        val durationOffset = draggingOffset / width * player.duration
        Column {
            Box {
                LinearProgressIndicator(
                    player.bufferedProgress,
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterStart)
                        .clip(RoundedCornerShape(50)),
                    color = MaterialTheme.colors.primary.copy(0.4f),
                    backgroundColor = LocalContentColor.current.copy(0.08f)
                )
                LinearProgressIndicator(
                    player.progress,
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterStart)
                        .clip(RoundedCornerShape(50)),
                    backgroundColor = Color.Transparent
                )
                Box(
                    Modifier
                        .fillMaxWidth()
                        .onSizeChanged { width = it.width }
                        .pointerInput(Unit) {
                            detectTapGestures {
                                player.seekToPosition(((it.x / width).coerceIn(0f..1f) * player.duration).toLong())
                            }
                        }
                        .draggable(
                            rememberDraggableState { draggingOffset += it },
                            Orientation.Horizontal,
                            onDragStopped = {
                                player.seekToPosition((player.position.value + durationOffset).toLong())
                                draggingOffset = 0f
                            }
                        )
                ) {
                    Box(
                        Modifier
                            .offset(x = with(LocalDensity.current) { (this@BoxWithConstraints.maxWidth * player.progress + draggingOffset.toDp()) } - 8.dp)
                            .size(16.dp)
                            .background(LocalContentColor.current, RoundedCornerShape(50))
                    )
                }
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 8.dp),
                Arrangement.SpaceBetween,
                Alignment.CenterVertically
            ) {
                Row {
                    Text(
                        player.position.value.toLong().toDuration(TimeUnit.MILLISECONDS)
                            .toPositiveTimeString(),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.caption
                    )
                    AnimatedVisibility(durationOffset <= -1000 || durationOffset >= 1000) {
                        Text(
                            "    ${
                                durationOffset.toLong().toDuration(TimeUnit.MILLISECONDS)
                                    .toOffsetTimeString()
                            }",
                            color = MaterialTheme.colors.primary,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.caption
                        )
                    }
                }
                Text(
                    player.duration.toDuration(TimeUnit.MILLISECONDS).toPositiveTimeString(),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}