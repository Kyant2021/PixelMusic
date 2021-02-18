package com.kyant.pixelmusic.ui.nowplaying

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.data.Media
import com.kyant.pixelmusic.media.Song
import com.kyant.pixelmusic.ui.component.Tag
import com.kyant.pixelmusic.ui.theme.androidBlue
import com.kyant.pixelmusic.ui.theme.androidChartreuse
import com.kyant.pixelmusic.ui.theme.androidFawn
import com.kyant.pixelmusic.ui.theme.androidGreen

@Composable
fun Info(
    song: Song,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier,
        contentPadding = PaddingValues(32.dp)
    ) {
        item {
            Text(
                song.title.orEmpty(),
                style = MaterialTheme.typography.h5
            )
            Spacer(Modifier.height(8.dp))
            Text(
                song.subtitle.orEmpty(),
                style = MaterialTheme.typography.body1
            )
            Divider(Modifier.padding(vertical = 32.dp))
            Row {
                Tag(androidChartreuse, "128k bits")
                Tag(androidGreen, "192k bits")
                Tag(androidBlue, "320k bits")
                Tag(androidFawn, "free")
            }
            Media.player!!.DrawFFT(
                Modifier.fillMaxWidth()
                    .padding(16.dp)
                    .height(256.dp)
            )
        }
    }
}