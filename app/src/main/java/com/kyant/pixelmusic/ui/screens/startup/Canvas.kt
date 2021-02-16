package com.kyant.pixelmusic.ui.screens.startup

import androidx.compose.animation.core.animateOffset
import androidx.compose.animation.core.animateSize
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.ui.theme.googleBlue

@Composable
fun BoxWithConstraintsScope.StartupCanvas(start: Int) {
    val density = LocalDensity.current
    val transition = updateTransition(start)
    with(density) {
        val s = transition.animateSize({ spring(stiffness = 700f) }) {
            when (it) {
                1 -> Size(128.dp.toPx(), 128.dp.toPx())
                3 -> Size.Zero
                else -> Size(64.dp.toPx(), 64.dp.toPx())
            }
        }.value
        val o = transition.animateOffset({ spring(dampingRatio = 0.8f, stiffness = 350f) }) {
            when (it) {
                0 -> Offset(-32.dp.toPx() - s.width / 2, -32.dp.toPx() - s.height / 2)
                1 -> Offset((constraints.maxWidth - s.width) / 2f, 128.dp.toPx())
                else -> Offset(32.dp.toPx(), 64.dp.toPx())
            }
        }.value

        Canvas(Modifier.fillMaxSize()) {
            drawRoundRect(
                googleBlue,
                o,
                s,
                CornerRadius(16.dp.toPx())
            )
        }
    }
}