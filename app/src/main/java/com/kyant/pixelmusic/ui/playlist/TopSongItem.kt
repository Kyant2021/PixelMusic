package com.kyant.pixelmusic.ui.playlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.api.toplist.TopList

@Composable
fun TopSongItem(
    topList: TopList,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        Modifier
            .width(192.dp)
            .padding(4.dp),
        RoundedCornerShape(8.dp),
        elevation = 3.dp
    ) {
        Column(modifier
            .clickable { onClick() }
            .padding(16.dp)) {
            Text(
                topList.name.toString(),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.h5
            )
            Spacer(Modifier.height(8.dp))
            Text(
                topList.updateFrequency.toString(),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.caption
            )
        }
    }
}