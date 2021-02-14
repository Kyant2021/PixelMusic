package com.kyant.inimate.layer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
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

@Composable
fun BackLayer(
    vararg states: LayerState,
    modifier: Modifier = Modifier,
    content: @Composable BoxWithConstraintsScope.() -> Unit
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
        val darkIcons = if (MaterialTheme.colors.isLight) progress <= 0.5f else false
        Surface(
            Modifier
                .fillMaxSize()
                .padding(top = statusBarHeight / 2 * progress)
                .scale((maxWidth - 24.dp * progress) / maxWidth),
            RoundedCornerShape(16.dp * progress, 16.dp * progress, 0.dp, 0.dp)
        ) {
            BoxWithConstraints(
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
        LaunchedEffect(darkIcons) {
            systemUiController.setSystemBarsColor(Color.Transparent, darkIcons)
        }
    }
}