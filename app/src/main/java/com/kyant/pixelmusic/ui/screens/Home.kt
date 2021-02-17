package com.kyant.pixelmusic.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.kyant.inimate.layer.BackLayer
import com.kyant.inimate.layer.ForeLayer
import com.kyant.inimate.layer.component1
import com.kyant.inimate.layer.component2
import com.kyant.inimate.shape.SuperellipseCornerShape
import com.kyant.pixelmusic.ui.component.TopBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun Home(navController: NavHostController) {
    // val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = FocusRequester.Default
    val scope = rememberCoroutineScope()
    val (searchState, accountState) = rememberSwipeableState(false)
    BackHandler(accountState.targetValue or searchState.targetValue) {
        scope.launch {
            if (accountState.targetValue)
                accountState.animateTo(false, spring(stiffness = 700f))
            else if (searchState.targetValue)
                searchState.animateTo(false, spring(stiffness = 700f))
        }
    }
    BackLayer(searchState, accountState) {
        TopBar(searchState, accountState, Modifier.zIndex(1f))
        Column(Modifier.padding(top = 64.dp)) {
            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                SuperellipseCornerShape(12.dp),
                MaterialTheme.colors.secondary,
                elevation = 24.dp
            ) {
                Column(
                    Modifier
                        .clickable {}
                        .padding(32.dp)
                ) {
                    Text(
                        "Pixel Music Alpha",
                        style = MaterialTheme.typography.h5
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Still developing...",
                        style = MaterialTheme.typography.body1
                    )
                }
            }
            Spacer(Modifier.height(32.dp))
            val items = listOf(
                Triple("History", Icons.Outlined.History, null),
                Triple("Playlists", Icons.Outlined.FeaturedPlayList, Screens.MyPlaylists.name),
                Triple("Statistics", Icons.Outlined.TrendingUp, null),
                Triple("New releases", Icons.Outlined.NewReleases, Screens.NewReleases.name),
                Triple("Leaderboards", Icons.Outlined.Leaderboard, Screens.Leaderboards.name)
            )
            LazyVerticalGrid(GridCells.Fixed(2)) {
                items(items) {
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        SuperellipseCornerShape(8.dp),
                        MaterialTheme.colors.onSurface.copy(0.02f),
                        elevation = 0.dp
                    ) {
                        Row(
                            Modifier
                                .clickable { it.third?.let { navController.navigate(it) } }
                                .padding(16.dp, 32.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(it.second, it.first)
                            Spacer(Modifier.width(16.dp))
                            Text(
                                it.first,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                style = MaterialTheme.typography.h6
                            )
                        }
                    }
                }
            }
        }
    }
    ForeLayer(searchState) {
        val lazyListState = rememberLazyListState()
        val nestedScrollConnection = remember {
            object : NestedScrollConnection {
                override fun onPostScroll(
                    consumed: Offset,
                    available: Offset,
                    source: NestedScrollSource
                ): Offset {
                    if (consumed.y == 0f && lazyListState.firstVisibleItemIndex == 0 && lazyListState.firstVisibleItemScrollOffset == 0) {
                        scope.launch {
                            searchState.animateTo(false)
                            focusRequester.freeFocus()
                            // keyboardController?.hideSoftwareKeyboard()
                        }
                    }
                    return super.onPostScroll(consumed, available, source)
                }
            }
        }
        Search(
            focusRequester,
            lazyListState,
            nestedScrollConnection
        )
        LaunchedEffect(searchState.targetValue) {
            if (searchState.targetValue) {
                focusRequester.requestFocus()
                // keyboardController?.showSoftwareKeyboard()
            } else {
                focusRequester.freeFocus()
                // keyboardController?.hideSoftwareKeyboard()
            }
        }
    }
    ForeLayer(accountState) {
        Account()
    }
}