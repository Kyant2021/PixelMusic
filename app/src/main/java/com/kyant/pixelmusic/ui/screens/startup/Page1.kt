package com.kyant.pixelmusic.ui.screens.startup

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NavigateNext
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.R

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BoxWithConstraintsScope.Page1(start: Int, setStart: (Int) -> Unit) {
    val density = LocalDensity.current
    val transition = updateTransition(start)
    with(density) {
        val si = transition.animateDp({ spring(stiffness = 700f) }) {
            when (it) {
                1 -> 128.dp
                else -> 64.dp
            }
        }.value
        val oi = transition.animateIntOffset({ spring(stiffness = 700f) }) {
            when (it) {
                1 -> IntOffset((constraints.minWidth - si.roundToPx()) / 2, 128.dp.roundToPx())
                else -> IntOffset(32.dp.roundToPx(), 64.dp.roundToPx())
            }
        }.value

        val ot = transition.animateIntOffset({ spring(stiffness = 700f) }) {
            when (it) {
                1 -> IntOffset(128.dp.roundToPx(), 288.dp.roundToPx())
                else -> IntOffset(128.dp.roundToPx(), 64.dp.roundToPx())
            }
        }.value
        val at = transition.animateFloat({ spring(stiffness = 700f) }) {
            when (it) {
                1 -> 1f
                2 -> 1f
                else -> 0f
            }
        }.value

        BoxWithConstraints(Modifier.fillMaxSize()) {
            AnimatedVisibility(
                transition.targetState == 1 || transition.targetState == 2,
                Modifier.offset { oi },
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Image(
                    painterResource(R.drawable.ic_launcher_foreground), null,
                    Modifier.size(si)
                )
            }
            Text(
                stringResource(R.string.app_name),
                Modifier
                    .offset { ot }
                    .alpha(animateFloatAsState(at).value),
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Black,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.h4
            )
            AnimatedVisibility(
                transition.targetState == 1,
                Modifier
                    .align(Alignment.BottomEnd)
                    .offset((-48).dp, (-128).dp)
            ) {
                IconButton({ setStart(2) }) {
                    Icon(Icons.Outlined.NavigateNext, "Next")
                }
            }
        }
    }
}