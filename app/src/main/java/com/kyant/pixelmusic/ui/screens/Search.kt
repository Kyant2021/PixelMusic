package com.kyant.pixelmusic.ui.screens

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.ui.insets.ExperimentalAnimatedInsets
import com.kyant.pixelmusic.ui.insets.navigationBarsWithImePadding
import com.kyant.pixelmusic.ui.insets.rememberImeNestedScrollConnection
import com.kyant.pixelmusic.api.searchSongs
import com.kyant.pixelmusic.media.Song
import com.kyant.pixelmusic.media.toSong
import com.kyant.pixelmusic.ui.song.Song

@OptIn(ExperimentalAnimatedInsets::class)
@Composable
fun Search(
    focusRequester: FocusRequester,
    state: LazyListState,
    nestedScrollConnection: NestedScrollConnection,
    modifier: Modifier = Modifier
) {
    // val keyboardController = LocalSoftwareKeyboardController.current
    var value by remember { mutableStateOf(TextFieldValue()) }
    val songs = remember(value.text) { mutableStateListOf<Song>() }
    LaunchedEffect(value.text) {
        if (value.text.isNotBlank()) {
            value.text.searchSongs()?.result?.songs?.map { it.toSong() }?.let { songs.addAll(it) }
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
            keyboardActions = KeyboardActions(
                onSearch = {
                    focusRequester.freeFocus()
                    // keyboardController?.hideSoftwareKeyboard()
                }
            ),
            singleLine = true
        )
        LazyColumn(
            Modifier
                .nestedScroll(rememberImeNestedScrollConnection())
                .nestedScroll(nestedScrollConnection),
            state
        ) {
            items(songs, { it.id?.toString().orEmpty() }) {
                Song(it)
            }
            item {
                Spacer(Modifier.navigationBarsWithImePadding())
            }
        }
    }
}