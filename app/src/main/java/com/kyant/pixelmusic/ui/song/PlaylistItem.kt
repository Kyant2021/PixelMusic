package com.kyant.pixelmusic.ui.song

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.ui.shape.SuperellipseCornerShape
import com.kyant.pixelmusic.api.playlist.Playlist
import com.kyant.pixelmusic.util.EmptyImage
import com.kyant.pixelmusic.util.loadImage

@Composable
fun PlaylistItem(
    playlist: Playlist,
    onCLick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var cover by remember { mutableStateOf(EmptyImage) }
    LaunchedEffect(playlist.id) {
        cover = playlist.coverImgUrl?.loadImage(context) ?: EmptyImage
    }
    Card(
        modifier
            .fillMaxWidth()
            .padding(8.dp),
        SuperellipseCornerShape(16.dp),
        MaterialTheme.colors.onSurface.copy(0.02f),
        elevation = 0.dp
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .clickable { onCLick() }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Cover(
                cover,
                playlist.id,
                Modifier
                    .size(128.dp)
                    .clip(SuperellipseCornerShape(8.dp))
            )
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text(
                    playlist.name.orEmpty(),
                    fontWeight = FontWeight.Medium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.h6
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    playlist.description.orEmpty(),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}