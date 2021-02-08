package com.kyant.inimate.layer

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableState
import androidx.compose.ui.unit.Constraints

@OptIn(ExperimentalMaterialApi::class)
fun SwipeableState<Boolean>.progress(constraints: Constraints): Float =
    (if (offset.value.isNaN()) 0f
    else 1f - (if (offset.value.isNaN()) 0f else offset.value) / constraints.maxHeight.toFloat())
        .coerceIn(0f..1f)