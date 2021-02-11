package com.kyant.inimate.scroll

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@OptIn(ExperimentalLayout::class)
@Composable
fun BoxScope.ScrollHeader(
    state: LazyListState,
    icon: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ScrollHeader(
        (state.firstVisibleItemIndex == 0 && state.firstVisibleItemScrollOffset != 0) || state.firstVisibleItemIndex != 0,
        icon, contentDescription, modifier, onClick
    )
}

@OptIn(ExperimentalLayout::class)
@Composable
fun BoxScope.ScrollHeader(
    collapsed: Boolean,
    icon: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val transition = updateTransition(collapsed)
    Card(
        modifier
            .padding(8.dp, 4.dp)
            .size(48.dp, transition.animateDp { if (it) 48.dp else 104.dp }.value)
            .align(Alignment.CenterStart)
            .clickable { onClick() }
            .zIndex(1f),
        RoundedCornerShape(transition.animateDp { if (it) 24.dp else 4.dp }.value),
        Color(0xFFE7F0FE),
        elevation = transition.animateDp { if (it) 8.dp else 0.dp }.value
    ) {
        Box(Modifier.fillMaxSize()) {
            Icon(
                icon,
                contentDescription,
                Modifier
                    .size(24.dp)
                    .align(Alignment.Center),
                MaterialTheme.colors.primary
            )
        }
    }
}