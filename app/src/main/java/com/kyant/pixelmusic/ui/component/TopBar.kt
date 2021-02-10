package com.kyant.pixelmusic.ui.component

import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TopBar(
    searchState: SwipeableState<Boolean>,
    myState: SwipeableState<Boolean>,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    Row(
        modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(MaterialTheme.colors.surface.copy(0.92f))
            .padding(8.dp),
        Arrangement.SpaceBetween,
        Alignment.CenterVertically
    ) {
        Row {
            Image(
                painterResource(R.drawable.ic_launcher_foreground), null,
                Modifier
                    .height(56.dp)
                    .padding(end = 8.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
            )
            Text(
                stringResource(R.string.app_name),
                Modifier.padding(top = 4.dp),
                color = LocalContentColor.current.copy(ContentAlpha.high),
                style = MaterialTheme.typography.h5
            )
        }
        Row {
            IconButton({
                scope.launch {
                    searchState.animateTo(true, spring(stiffness = 700f))
                }
            }) {
                Icon(
                    Icons.Outlined.Search, "Search",
                    tint = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium)
                )
            }
            IconButton({
                scope.launch {
                    myState.animateTo(true, spring(stiffness = 700f))
                }
            }) {
                Icon(
                    Icons.Outlined.AccountCircle, "My",
                    tint = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium)
                )
            }
        }
    }
}