package com.kyant.pixelmusic.ui.layer

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableState
import androidx.compose.material.rememberSwipeableState
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterialApi::class)
typealias LayerState = SwipeableState<Boolean>

@OptIn(ExperimentalMaterialApi::class)
fun LayerState.progressOf(to: Float): Float =
    (if (offset.value.isNaN()) 0f else 1f - offset.value / to).coerceIn(0f..1f)

@OptIn(ExperimentalMaterialApi::class)
@Composable
operator fun LayerState.component1() = rememberSwipeableState(false)

@OptIn(ExperimentalMaterialApi::class)
@Composable
operator fun LayerState.component2() = rememberSwipeableState(false)

@OptIn(ExperimentalMaterialApi::class)
@Composable
operator fun LayerState.component3() = rememberSwipeableState(false)

@OptIn(ExperimentalMaterialApi::class)
@Composable
operator fun LayerState.component4() = rememberSwipeableState(false)

@OptIn(ExperimentalMaterialApi::class)
@Composable
operator fun LayerState.component5() = rememberSwipeableState(false)

@OptIn(ExperimentalMaterialApi::class)
@Composable
operator fun LayerState.component6() = rememberSwipeableState(false)

@OptIn(ExperimentalMaterialApi::class)
@Composable
operator fun LayerState.component7() = rememberSwipeableState(false)

@OptIn(ExperimentalMaterialApi::class)
@Composable
operator fun LayerState.component8() = rememberSwipeableState(false)

@OptIn(ExperimentalMaterialApi::class)
@Composable
operator fun LayerState.component9() = rememberSwipeableState(false)

@OptIn(ExperimentalMaterialApi::class)
@Composable
operator fun LayerState.component10() = rememberSwipeableState(false)

@OptIn(ExperimentalMaterialApi::class)
@Composable
operator fun LayerState.component11() = rememberSwipeableState(false)

@OptIn(ExperimentalMaterialApi::class)
@Composable
operator fun LayerState.component12() = rememberSwipeableState(false)

@OptIn(ExperimentalMaterialApi::class)
@Composable
operator fun LayerState.component13() = rememberSwipeableState(false)

@OptIn(ExperimentalMaterialApi::class)
@Composable
operator fun LayerState.component14() = rememberSwipeableState(false)

@OptIn(ExperimentalMaterialApi::class)
@Composable
operator fun LayerState.component15() = rememberSwipeableState(false)

@OptIn(ExperimentalMaterialApi::class)
@Composable
operator fun LayerState.component16() = rememberSwipeableState(false)