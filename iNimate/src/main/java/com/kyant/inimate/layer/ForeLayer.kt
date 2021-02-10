package com.kyant.inimate.layer

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.kyant.inimate.insets.LocalWindowInsets
import com.kyant.inimate.insets.statusBarsPadding

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ForeLayer(
    state: SwipeableState<Boolean>,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val density = LocalDensity.current
    val statusBarHeight = with(density) { LocalWindowInsets.current.statusBars.top.toDp() }
    BoxWithConstraints {
        val progress = state.progressOf(constraints.maxHeight.toFloat())
        Card(
            modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(top = statusBarHeight * progress)
                .offset(y = maxHeight * (1f - progress))
                .swipeable(
                    state,
                    mapOf(
                        0f to true,
                        constraints.maxHeight.toFloat() to false
                    ),
                    Orientation.Vertical
                ),
            RoundedCornerShape(16.dp * progress, 16.dp * progress, 0.dp, 0.dp),
            elevation = 24.dp * progress
        ) {
            Column(Modifier.fillMaxSize()) {
                Divider(
                    Modifier.width(40.dp)
                        .padding(vertical = 8.dp)
                        .clip(RoundedCornerShape(50))
                        .align(Alignment.CenterHorizontally),
                    thickness = 3.dp
                )
                content()
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FloatingForeLayer(
    state: SwipeableState<Boolean>,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val density = LocalDensity.current
    BoxWithConstraints(Modifier.fillMaxSize()) {
        var height by remember { mutableStateOf(constraints.maxHeight) }
        val progress = state.progressOf(height.toFloat())
        Card(
            modifier
                .onSizeChanged { height = it.height }
                .align(Alignment.BottomCenter)
                .offset(y = with(density) { height.toDp() } * (1f - progress))
                .swipeable(
                    state,
                    mapOf(
                        0f to true,
                        height.toFloat() to false
                    ),
                    Orientation.Vertical
                ),
            RoundedCornerShape(16.dp * progress, 16.dp * progress, 0.dp, 0.dp),
            elevation = 24.dp * progress
        ) {
            Column(Modifier.fillMaxWidth()) {
                Divider(
                    Modifier.width(40.dp)
                        .padding(vertical = 8.dp)
                        .clip(RoundedCornerShape(50))
                        .align(Alignment.CenterHorizontally),
                    thickness = 3.dp
                )
                content()
            }
        }
    }
}