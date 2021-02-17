package com.kyant.pixelmusic.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.data.updatePreferences
import com.kyant.pixelmusic.locals.LocalPreferences
import com.kyant.pixelmusic.locals.ProvidePreferences

@Composable
fun Customize() {
    val context = LocalContext.current
    ProvidePreferences {
        val legacyPreferences = LocalPreferences.current
        var preferences by remember { mutableStateOf(legacyPreferences) }
        var screenCornerSizeDp by remember { mutableStateOf(TextFieldValue(preferences.screenCornerSizeDp.toString())) }
        var lyricsFontScale by remember { mutableStateOf(TextFieldValue(preferences.lyricsFontScale.toString())) }
        var lyricsFontWeight by remember { mutableStateOf(preferences.lyricsFontWeight) }
        LaunchedEffect(preferences, screenCornerSizeDp, lyricsFontScale, lyricsFontWeight) {
            context.updatePreferences(
                preferences.copy(
                    screenCornerSizeDp = screenCornerSizeDp.text.toFloatOrNull()
                        ?: legacyPreferences.screenCornerSizeDp,
                    lyricsFontScale = lyricsFontScale.text.toFloatOrNull()
                        ?: legacyPreferences.lyricsFontScale,
                    lyricsFontWeight = lyricsFontWeight
                )
            )
        }
        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 24.dp, bottom = 24.dp)
        ) {
            item {
                Text(
                    "Customize",
                    Modifier.padding(16.dp),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.h5
                )
            }
            item {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .heightIn(min = 56.dp)
                        .padding(horizontal = 16.dp),
                    Arrangement.SpaceBetween,
                    Alignment.CenterVertically
                ) {
                    Text("Improve accessibility")
                    Switch(
                        preferences.improveAccessibility,
                        {
                            preferences = preferences.copy(improveAccessibility = it)
                        }
                    )
                }
            }
            item {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .heightIn(min = 56.dp)
                        .padding(horizontal = 16.dp),
                    Arrangement.SpaceBetween,
                    Alignment.CenterVertically
                ) {
                    Text("Screen corner size in Dp")
                    OutlinedTextField(
                        screenCornerSizeDp,
                        { screenCornerSizeDp = it },
                        Modifier.width(128.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                }
            }
            item {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .heightIn(min = 56.dp)
                        .padding(horizontal = 16.dp)
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        Arrangement.SpaceBetween,
                        Alignment.CenterVertically
                    ) {
                        Text("Lyrics font scale")
                        OutlinedTextField(
                            lyricsFontScale,
                            { lyricsFontScale = it },
                            Modifier.width(128.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true
                        )
                    }
                    Text(
                        "This is a lyric.",
                        Modifier.padding(vertical = 16.dp),
                        MaterialTheme.colors.primary,
                        fontWeight = FontWeight(lyricsFontWeight),
                        style = MaterialTheme.typography.h5
                    )
                }
            }
            item {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .heightIn(min = 56.dp)
                        .padding(horizontal = 16.dp)
                ) {
                    Text("Lyrics font weight")
                    Slider(
                        lyricsFontWeight / 100f,
                        { lyricsFontWeight = it.toInt() * 100 },
                        valueRange = 1f..10f
                    )
                    Row(
                        Modifier.fillMaxWidth(),
                        Arrangement.SpaceBetween,
                        Alignment.CenterVertically
                    ) {
                        Text(
                            "Thin",
                            color = MaterialTheme.colors.primary,
                            fontWeight = FontWeight.Thin,
                            style = MaterialTheme.typography.caption
                        )
                        Text(
                            "Normal",
                            color = MaterialTheme.colors.primary,
                            fontWeight = FontWeight.Normal,
                            style = MaterialTheme.typography.caption
                        )
                        Text(
                            "Black",
                            color = MaterialTheme.colors.primary,
                            fontWeight = FontWeight.Black,
                            style = MaterialTheme.typography.caption
                        )
                    }
                    Text(
                        lyricsFontWeight.toString(),
                        Modifier.padding(vertical = 16.dp)
                    )
                }
            }
        }
    }
}