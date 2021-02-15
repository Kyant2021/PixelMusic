package com.kyant.pixelmusic.ui.screens.startup

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
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
import com.kyant.pixelmusic.ui.theme.googleGreen
import com.kyant.pixelmusic.ui.theme.googleYellow

@Composable
fun BoxWithConstraintsScope.StartupCanvas(start: Int) {
    val density = LocalDensity.current
    val transition = updateTransition(start)
    with(density) {
        val s1 = transition.animateSize({ spring(stiffness = 700f) }) {
            when (it) {
                1 -> Size(128.dp.toPx(), 128.dp.toPx())
                3 -> Size.Zero
                else -> Size(64.dp.toPx(), 64.dp.toPx())
            }
        }.value
        val o1 = transition.animateOffset({ spring(stiffness = 700f) }) {
            when (it) {
                0 -> Offset(-32.dp.toPx() - s1.width / 2, -32.dp.toPx() - s1.height / 2)
                1 -> Offset((constraints.maxWidth - s1.width) / 2f, 128.dp.toPx())
                else -> Offset(32.dp.toPx(), 64.dp.toPx())
            }
        }.value

        val a2 = transition.animateFloat({ spring(stiffness = 700f) }) {
            when (it) {
                1 -> 0f
                else -> -150f
            }
        }.value
        val s2 = transition.animateSize({ spring(stiffness = 700f) }) {
            when (it) {
                0 -> Size(128.dp.toPx(), 128.dp.toPx())
                1 -> Size(256.dp.toPx(), 256.dp.toPx())
                else -> Size(196.dp.toPx(), 196.dp.toPx())
            }
        }.value
        val o2 = transition.animateOffset({ spring(stiffness = 700f) }) {
            when (it) {
                0 -> Offset(64.dp.toPx(), 64.dp.toPx())
                2 -> Offset(64.dp.toPx(), 64.dp.toPx())
                3 -> Offset(64.dp.toPx(), 64.dp.toPx())
                else -> Offset(128.dp.toPx(), 256.dp.toPx())
            }
        }.value
        val t2 = transition.animateColor({ spring(stiffness = 700f) }) {
            when (it) {
                0 -> googleGreen
                1 -> googleGreen
                2 -> googleGreen
                else -> googleBlue
            }
        }.value

        val r3 = transition.animateFloat({ spring(stiffness = 350f) }) {
            when (it) {
                0 -> 64.dp.toPx()
                1 -> 64.dp.toPx()
                2 -> 64.dp.toPx()
                3 -> 128.dp.toPx()
                else -> maxOf(constraints.maxWidth, constraints.maxHeight).toFloat()
            }
        }.value
        val o3 = transition.animateOffset({ spring(stiffness = 350f) }) {
            when (it) {
                0 -> Offset(-r3 / 2, constraints.maxHeight + r3 / 2)
                1 -> Offset(32.dp.toPx(), constraints.maxHeight.toFloat())
                2 -> Offset(48.dp.toPx(), constraints.maxHeight - r3 / 4)
                3 -> Offset(constraints.maxWidth / 2f, constraints.maxHeight / 2f)
                else -> Offset(constraints.maxWidth / 2f, constraints.maxHeight / 2f)
            }
        }.value

        Canvas(Modifier.fillMaxSize()) {
            drawRoundRect(
                googleBlue,
                o1,
                s1,
                CornerRadius(16.dp.toPx())
            )
            drawArc(
                t2,
                150f + a2,
                90f,
                true,
                Offset(constraints.maxWidth.toFloat(), constraints.maxHeight.toFloat()) - o2,
                s2
            )
            drawCircle(
                googleYellow,
                r3,
                o3
            )
        }
    }
}