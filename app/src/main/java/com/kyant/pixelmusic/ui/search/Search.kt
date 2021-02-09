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
import androidx.compose.ui.text.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.locals.LocalSearchResult
import com.kyant.pixelmusic.locals.ProvideSearchResult
import com.kyant.pixelmusic.media.toSong
import com.kyant.pixelmusic.ui.song.Song

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Search(
    focusRequester: FocusRequester,
    softwareKeyboardController: MutableState<SoftwareKeyboardController?>,
    modifier: Modifier = Modifier
) {
    var value by remember { mutableStateOf(TextFieldValue()) }
    Column(modifier) {
        ProvideSearchResult(value.text) {
            val result = LocalSearchResult.current.result?.songs
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
                    onSearch?.let { softwareKeyboardController.value?.hideSoftwareKeyboard() }
                },
                singleLine = true,
                onTextInputStarted = { softwareKeyboardController.value = it },
            )
            LazyColumn {
                result?.let { songs ->
                    items(songs, { it.id?.toString().orEmpty() }) {
                        Song(it.toSong())
                    }
                }
            }
        }
    }
}