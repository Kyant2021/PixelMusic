package com.kyant.pixelmusic.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
        LaunchedEffect(preferences, screenCornerSizeDp) {
            context.updatePreferences(
                preferences.copy(
                    screenCornerSizeDp = screenCornerSizeDp.text.toFloatOrNull()
                        ?: legacyPreferences.screenCornerSizeDp
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
                        .height(56.dp)
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
                        .height(56.dp)
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
        }
    }
}