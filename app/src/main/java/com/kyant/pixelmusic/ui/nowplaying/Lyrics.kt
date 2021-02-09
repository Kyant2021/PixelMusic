package com.kyant.pixelmusic.ui.nowplaying

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.estimateAnimationDurationMillis
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.api.Lyrics
import com.kyant.pixelmusic.locals.LocalPixelPlayer
import com.kyant.pixelmusic.util.isCurrentLine
import com.kyant.pixelmusic.util.toMilliseconds
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Lyrics(
    lyrics: Lyrics,
    modifier: Modifier = Modifier
) {
    val player = LocalPixelPlayer.current
    val scope = rememberCoroutineScope()
    LazyColumn(
        modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(lyrics.toList(), { it.first }) { (time, lyric) ->
            val alpha = remember { Animatable(0f) }
            Box(
                Modifier
                    .fillMaxWidth()
                    .pointerInput(Unit) {
                        detectTapGestures(onLongPress = {
                            scope.launch {
                                alpha.animateTo(1f)
                                delay(
                                    estimateAnimationDurationMillis(
                                        Spring.StiffnessMedium,
                                        Spring.DampingRatioMediumBouncy,
                                        0f,
                                        0f,
                                        1f
                                    ) * 3
                                )
                                alpha.animateTo(0f)
                            }
                        }) {
                            player.snapTo(time.toMilliseconds() + 1)
                        }
                    }
                    .padding(16.dp)
            ) {
                Text(
                    lyric,
                    Modifier.align(Alignment.CenterStart),
                    if (time.isCurrentLine(lyrics.toMap())) MaterialTheme.colors.secondary
                    else MaterialTheme.colors.onSurface.copy(ContentAlpha.disabled),
                    fontWeight = FontWeight.Black,
                    style = MaterialTheme.typography.h5
                )
                Text(
                    time,
                    Modifier
                        .align(Alignment.TopEnd)
                        .alpha(alpha.value),
                    MaterialTheme.colors.secondary,
                    fontWeight = FontWeight.Black,
                    maxLines = 1,
                    style = MaterialTheme.typography.h5
                )
            }
            Spacer(Modifier.height(6.dp))
        }
    }
}