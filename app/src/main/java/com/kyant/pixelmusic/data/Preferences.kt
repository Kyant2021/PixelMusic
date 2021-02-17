package com.kyant.pixelmusic.data

import android.content.Context
import com.kyant.pixelmusic.util.DataStore
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class Preferences(
    val improveAccessibility: Boolean = false,
    val screenCornerSizeDp: Float = 0f
)

fun Context.updatePreferences(preferences: Preferences) {
    DataStore(this, "settings").write("preferences", Json.encodeToString(preferences))
}