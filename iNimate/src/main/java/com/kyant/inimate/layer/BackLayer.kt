package com.kyant.inimate.layer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.SwipeableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.kyant.inimate.insets.LocalSysUiController
import com.kyant.inimate.insets.LocalWindowInsets
import com.kyant.inimate.insets.statusBarsPadding

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BackLayer(
    states: List<SwipeableState<Boolean>>,
    darkIcons: (progress: Float, statusBarHeightRatio: Float) -> Boolean,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val density = LocalDensity.current
    val systemUiController = LocalSysUiController.current
    val statusBarHeight = with(density) { LocalWindowInsets.current.statusBars.top.toDp() }
    BoxWithConstraints(
        Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        val progress = states.map { it.progress(constraints) }.maxByOrNull { it } ?: 0f
        val isDarkIcons = darkIcons(progress, statusBarHeight / maxHeight)
        Surface(
            modifier
                .fillMaxSize()
                .padding(top = statusBarHeight / 2 * progress)
                .scale((maxWidth - 24.dp * progress) / maxWidth),
            RoundedCornerShape(
                (16.dp * progress).coerceAtLeast(0.dp),
                (16.dp * progress).coerceAtLeast(0.dp),
                0.dp,
                0.dp
            )
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
            ) {
                content()
            }
        }
        LaunchedEffect(isDarkIcons) {
            systemUiController.setSystemBarsColor(Color.Transparent, isDarkIcons)
        }
    }
}