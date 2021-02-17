package com.kyant.pixelmusic.ui.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils

fun Color.lighten(factor: Float): Color {
    return Color(ColorUtils.blendARGB(Color.White.toArgb(), toArgb(), factor))
}

fun Color.darken(factor: Float): Color {
    return Color(ColorUtils.blendARGB(Color.Black.toArgb(), toArgb(), factor))
}