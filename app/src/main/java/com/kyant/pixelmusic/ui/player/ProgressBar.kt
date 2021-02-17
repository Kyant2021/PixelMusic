package com.kyant.pixelmusic.ui.player

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.locals.LocalPixelPlayer
import com.kyant.pixelmusic.util.toPositiveTimeString
import java.util.concurrent.TimeUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

@OptIn(ExperimentalTime::class)
@Composable
fun ProgressBar(modifier: Modifier = Modifier) {
    val player = LocalPixelPlayer.current
    Column(modifier.fillMaxWidth()) {
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
            Slider(
                player.position.value,
                { player.seekToPosition(it.toLong()) },
                valueRange = 0f..player.duration.toFloat()
            )
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
            }
            Text(
                player.duration.toDuration(TimeUnit.MILLISECONDS).toPositiveTimeString(),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.caption
            )
        }
    }
}