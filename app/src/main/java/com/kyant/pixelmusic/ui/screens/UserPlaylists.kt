package com.kyant.pixelmusic.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.rememberSwipeableState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.ui.layer.BackLayer
import com.kyant.pixelmusic.ui.layer.ForeLayer
import com.kyant.pixelmusic.api.UserId
import com.kyant.pixelmusic.api.findUserPlaylist
import com.kyant.pixelmusic.api.login.LoginResult
import com.kyant.pixelmusic.api.playlist.Playlist
import com.kyant.pixelmusic.ui.song.PlaylistItem
import com.kyant.pixelmusic.util.DataStore
import com.kyant.pixelmusic.util.EmptyImage
import com.kyant.pixelmusic.util.loadImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserPlaylists() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val state = rememberSwipeableState(false)
    var userId: UserId? by remember { mutableStateOf(null) }
    var playlist by remember { mutableStateOf<Playlist?>(null) }
    val playlists = remember(userId) { mutableStateListOf<Playlist>() }
    val images = remember(userId) { mutableStateListOf<ImageBitmap>() }
    val dataStore = DataStore(context, "account")
    val login = dataStore.getJsonOrNull<LoginResult>("login")
    LaunchedEffect(userId) {
        userId = login?.account?.id
        playlists.addAll(userId?.findUserPlaylist()?.playlist ?: emptyList())
        playlists.forEach {
            images += it.coverImgUrl?.loadImage(context) ?: EmptyImage
        }
    }
    BackHandler(state.targetValue) {
        scope.launch {
            state.animateTo(false, spring(stiffness = 700f))
        }
    }
    BackLayer(state) {
        LazyColumn(contentPadding = PaddingValues(top = 24.dp, bottom = 24.dp)) {
            item {
                Text(
                    "${login?.profile?.nickname.orEmpty()}'s playlists",
                    Modifier.padding(16.dp),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.h5
                )
            }
            items(playlists, { it.id?.toString().orEmpty() }) {
                PlaylistItem(it, {
                    playlist = it
                    scope.launch {
                        state.animateTo(true, spring(stiffness = 700f))
                    }
                })
            }
        }
    }
    ForeLayer(state) {
        Playlist(playlist)
    }
}