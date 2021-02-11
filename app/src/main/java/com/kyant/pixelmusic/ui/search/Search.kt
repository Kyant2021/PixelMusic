package com.kyant.pixelmusic.ui.search

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.api.searchSongs
import com.kyant.pixelmusic.media.Song
import com.kyant.pixelmusic.media.toSong
import com.kyant.pixelmusic.ui.song.Song
import com.kyant.pixelmusic.util.EmptyImage
import com.kyant.pixelmusic.util.loadCoverWithCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Search(
    focusRequester: FocusRequester,
    softwareKeyboardController: MutableState<SoftwareKeyboardController?>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var value by remember { mutableStateOf(TextFieldValue()) }
    val songs = remember(value.text) { mutableStateListOf<Song>() }
    val icons = remember(value.text) { mutableStateMapOf<Long, ImageBitmap>() }
    LaunchedEffect(value.text) {
        if (value.text.isNotBlank()) {
            withContext(Dispatchers.IO) {
                value.text.searchSongs()?.result?.songs?.apply {
                    forEach {
                        songs += it.toSong()
                    }
                    parallelStream().forEachOrdered {
                        launch {
                            icons[it.album?.id ?: 0] =
                                it.album?.id?.loadCoverWithCache(context, "covers", 100)
                                    ?: EmptyImage
                        }
                    }
                }
            }
        }
    }
    Column(modifier) {
        OutlinedTextField(
            value,
            { value = it },
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .focusable(true)
                .focusRequester(focusRequester),
            label = { Text("Search songs") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions.Default.apply {
                onSearch?.let {
                    focusRequester.freeFocus()
                    softwareKeyboardController.value?.hideSoftwareKeyboard()
                }
            },
            singleLine = true,
            onTextInputStarted = { softwareKeyboardController.value = it },
        )
        LazyColumn {
            items(songs, { it.id?.toString().orEmpty() }) {
                Song(it.copy(icon = icons.getOrElse(it.albumId ?: 0) { EmptyImage }))
            }
        }
    }
}