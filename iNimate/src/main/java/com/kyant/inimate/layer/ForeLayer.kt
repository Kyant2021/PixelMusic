package com.kyant.inimate.layer

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.kyant.inimate.insets.statusBarsPadding

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ForeLayer(
    state: SwipeableState<Boolean>,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    BoxWithConstraints {
        val progress = state.progress(constraints)
        Card(
            modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(top = 32.dp)
                .offset(y = maxHeight * (1f - progress))
                .swipeable(
                    state,
                    mapOf(
                        0f to true,
                        constraints.maxHeight.toFloat() to false
                    ),
                    Orientation.Vertical
                )
                .pointerInput(Unit) { detectTapGestures {} },
            RoundedCornerShape(
                (16.dp * progress).coerceAtLeast(0.dp),
                (16.dp * progress).coerceAtLeast(0.dp),
                0.dp,
                0.dp
            ),
            elevation = 24.dp * progress
        ) {
            Column(Modifier.fillMaxSize()) {
                Divider(
                    Modifier.preferredWidth(40.dp)
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