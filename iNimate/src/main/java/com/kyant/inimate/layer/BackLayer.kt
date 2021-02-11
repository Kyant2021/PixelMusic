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
import androidx.compose.ui.graphics.lerp
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
        val progress = states.map { it.progressOf(constraints.maxHeight.toFloat()) }
            .maxByOrNull { it } ?: 0f
        val isDarkIcons = darkIcons(progress, statusBarHeight / maxHeight)
        Surface(
            Modifier
                .fillMaxSize()
                .padding(top = statusBarHeight / 2 * progress)
                .scale((maxWidth - 24.dp * progress) / maxWidth),
            RoundedCornerShape(16.dp * progress, 16.dp * progress, 0.dp, 0.dp)
        ) {
            Box(
                modifier
                    .fillMaxSize()
                    .statusBarsPadding()
            ) {
                content()
            }
            Surface(
                Modifier.fillMaxSize(),
                color = lerp(Color.Transparent, Color.Black.copy(0.04f), progress)
            ) {}
        }
        LaunchedEffect(isDarkIcons) {
            systemUiController.setSystemBarsColor(Color.Transparent, isDarkIcons)
        }
    }
}