package com.kyant.pixelmusic.ui.theme

import android.view.Window
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import com.kyant.inimate.insets.ExperimentalAnimatedInsets
import com.kyant.inimate.insets.LocalSysUiController
import com.kyant.inimate.insets.ProvideWindowInsets
import com.kyant.inimate.insets.SystemUiController

private val DarkColorPalette = darkColors(
    primary = googleBlueDark,
    primaryVariant = googleBlue,
    secondary = androidGreen
)

private val LightColorPalette = lightColors(
    primary = googleBlue,
    primaryVariant = googleBlue,
    secondary = androidGreen
)

@OptIn(ExperimentalAnimatedInsets::class)
@Composable
fun PixelMusicTheme(
    window: Window,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette
    MaterialTheme(colors, typography, shapes) {
        CompositionLocalProvider(LocalContentColor provides colors.onSurface) {
            ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
                CompositionLocalProvider(LocalSysUiController provides SystemUiController(window)) {
                    content()
                }
            }
        }
    }
}

@Composable
fun NowPlayingTheme(
    color: Color,
    onColor: Color,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = (if (darkTheme) DarkColorPalette else LightColorPalette).copy(
        primary = animateColorAsState(color).value,
        onPrimary = animateColorAsState(if (color.luminance() <= 0.5f) Color.White else Color.Black).value
    )
    MaterialTheme(colors, typography, shapes) {
        CompositionLocalProvider(LocalContentColor provides animateColorAsState(onColor).value) {
            content()
        }
    }
}