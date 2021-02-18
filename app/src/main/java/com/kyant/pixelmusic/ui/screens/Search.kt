package com.kyant.pixelmusic.ui.screens

import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.api.searchSongs
import com.kyant.pixelmusic.media.Song
import com.kyant.pixelmusic.media.toSong
import com.kyant.pixelmusic.ui.insets.ExperimentalAnimatedInsets
import com.kyant.pixelmusic.ui.insets.navigationBarsWithImePadding
import com.kyant.pixelmusic.ui.insets.rememberImeNestedScrollConnection
import com.kyant.pixelmusic.ui.sharedelements.*
import com.kyant.pixelmusic.ui.song.ExpandedSong
import com.kyant.pixelmusic.ui.song.Song

@OptIn(ExperimentalAnimatedInsets::class)
@Composable
fun Search(focusRequester: FocusRequester, modifier: Modifier = Modifier) {
    // val keyboardController = LocalSoftwareKeyboardController.current
    var value by remember { mutableStateOf(TextFieldValue()) }
    val songs = remember(value.text) { mutableStateListOf<Song>() }
    LaunchedEffect(value.text) {
        if (value.text.isNotBlank()) {
            value.text.searchSongs()?.result?.songs?.map { it.toSong() }?.let { songs.addAll(it) }
        }
    }
    BoxWithConstraints(Modifier.fillMaxSize()) {
        SharedElementsRoot {
            var song: Song? by remember { mutableStateOf(null) }
            val (fraction, setFraction) = remember { mutableStateOf(0f) }
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
                LazyColumn(Modifier.nestedScroll(rememberImeNestedScrollConnection())) {
                    items(songs, { it.id?.toString().orEmpty() }) {
                        Box(Modifier.height(64.dp)) {
                            DelayExit(song?.id != it.id) {
                                SharedMaterialContainer(
                                    it.id?.toString().orEmpty(), "song",
                                    shape = RoundedCornerShape(24.dp * fraction),
                                    transitionSpec = MaterialContainerTransformSpec(
                                        pathMotionFactory = MaterialArcMotionFactory,
                                        durationMillis = 600,
                                        fadeMode = FadeMode.In
                                    ),
                                    onFractionChanged = setFraction
                                ) {
                                    Song(it, onLongClick = { song = it })
                                }
                            }
                        }
                    }
                    item {
                        Spacer(Modifier.navigationBarsWithImePadding())
                    }
                }
            }
            var offset by remember { mutableStateOf(0f) }
            val state = rememberDraggableState { offset += it }
            with(LocalDensity.current) {
                DelayExit(song != null) {
                    BoxWithConstraints(Modifier.align(Alignment.Center)
                        .offset(y = offset.toDp() / 2)
                        .draggable(
                            state,
                            Orientation.Vertical,
                            onDragStopped = {
                                if (!(-48.dp.toPx()..48.dp.toPx()).contains(offset)) {
                                    song = null
                                    offset = 0f
                                }
                            }
                        )
                    ) {
                        SharedMaterialContainer(
                            song?.id?.toString().orEmpty(), "expanded_song",
                            shape = RoundedCornerShape(24.dp * (1 - fraction)),
                            elevation = 24.dp,
                            transitionSpec = MaterialContainerTransformSpec(
                                pathMotionFactory = MaterialArcMotionFactory,
                                durationMillis = 600,
                                fadeMode = FadeMode.Out
                            ),
                            onFractionChanged = setFraction
                        ) {
                            ExpandedSong(song ?: Song(), 1 - fraction)
                        }
                    }
                }
            }
        }
    }
}