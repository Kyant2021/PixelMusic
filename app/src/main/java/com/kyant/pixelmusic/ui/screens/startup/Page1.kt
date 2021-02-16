package com.kyant.pixelmusic.ui.screens.startup

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.kyant.pixelmusic.ui.theme.googleBlue
import com.kyant.pixelmusic.ui.theme.googleGreen
import com.kyant.pixelmusic.ui.theme.googleYellow

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
                1 -> IntOffset(0, 288.dp.roundToPx())
                else -> IntOffset(0, 64.dp.roundToPx())
            }
        }.value
        val at = transition.animateFloat({ spring(stiffness = 700f) }) {
            when (it) {
                1 -> 1f
                2 -> 1f
                else -> 0f
            }
        }.value

        val sn = transition.animateDp({ spring(stiffness = 700f) }) {
            when (it) {
                0 -> 0.dp
                1 -> 48.dp
                2 -> 128.dp
                3 -> 256.dp
                else -> maxOf(maxWidth, maxHeight)
            }
        }.value
        val on = transition.animateIntOffset({ spring(stiffness = 700f) }) {
            when (it) {
                0 -> IntOffset(-sn.roundToPx() / 2, constraints.maxHeight + sn.roundToPx() / 2)
                1 -> IntOffset((constraints.maxWidth - sn.roundToPx()) / 2, -128.dp.roundToPx())
                2 -> IntOffset(-sn.roundToPx() / 4, sn.roundToPx() / 4)
                3 -> IntOffset(
                    (constraints.maxWidth - sn.roundToPx()) / 2,
                    -(constraints.maxHeight - sn.roundToPx()) / 2
                )
                else -> IntOffset(0, -(constraints.maxHeight - sn.roundToPx()) / 2)
            }
        }.value
        val rn = transition.animateInt({ spring(stiffness = 350f) }) {
            when (it) {
                0 -> 50
                1 -> 50
                2 -> 50
                3 -> 50
                else -> 0
            }
        }.value
        val cn = transition.animateColor({ spring(stiffness = 350f) }) {
            when (it) {
                0 -> googleGreen
                1 -> googleGreen
                2 -> googleYellow
                else -> googleBlue
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
                    .align(Alignment.TopCenter)
                    .offset { ot }
                    .alpha(animateFloatAsState(at).value),
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Black,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.h4
            )
            Box(
                Modifier
                    .size(sn)
                    .align(Alignment.BottomStart)
                    .offset { on }
                    .background(cn, RoundedCornerShape(rn))
            ) {
                if (transition.targetState == 1) {
                    IconButton(
                        { setStart(2) },
                        Modifier.align(Alignment.Center)
                    ) {
                        Icon(
                            Icons.Outlined.NavigateNext, "Next",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            }
        }
    }
}