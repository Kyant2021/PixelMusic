package com.kyant.pixelmusic.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.kyant.inimate.layer.BackLayer
import com.kyant.inimate.layer.ForeLayer
import com.kyant.inimate.layer.component1
import com.kyant.inimate.layer.component2
import com.kyant.inimate.scroll.ScrollCard
import com.kyant.inimate.scroll.ScrollFixedCard
import com.kyant.inimate.scroll.ScrollHeader
import com.kyant.inimate.scroll.ScrollMoreCard
import com.kyant.inimate.shape.SuperellipseCornerShape
import com.kyant.pixelmusic.ui.Screens
import com.kyant.pixelmusic.ui.component.TopBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun Home(navController: NavHostController) {
    val keyboardController = LocalSoftwareKeyboardController.current
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
        LazyColumn(contentPadding = PaddingValues(top = 64.dp, bottom = 128.dp)) {
            item {
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
            }
            item {
                val state = rememberLazyListState()
                Box {
                    ScrollHeader(state, Icons.Outlined.AccountCircle, "My") {}
                    LazyRow(state = state, contentPadding = PaddingValues(start = 64.dp)) {
                        item {
                            ScrollCard(
                                "History", Icons.Outlined.History, "History",
                                Color(0xFF3F51B5)
                            ) {}
                        }
                        item {
                            ScrollCard(
                                "Favorites", Icons.Outlined.Favorite, "Favorites",
                                Color(0xFFE91E63)
                            ) {}
                        }
                        item {
                            ScrollCard(
                                "Playlists", Icons.Outlined.FeaturedPlayList, "Playlists",
                                Color(0xFF009688)
                            ) { navController.navigate(Screens.MY_PLAYLISTS.name) }
                        }
                        item {
                            ScrollCard(
                                "Statistics", Icons.Outlined.TrendingUp, "Statistics",
                                Color(0xFF4CAF50)
                            ) {}
                        }
                        item {
                            ScrollMoreCard {}
                        }
                    }
                }
            }
            item {
                val state = rememberLazyListState()
                Box {
                    ScrollHeader(state, Icons.Outlined.NewReleases, "New songs") {}
                    LazyRow(state = state, contentPadding = PaddingValues(start = 64.dp)) {
                        item {
                            ScrollCard("全部", Icons.Outlined.NewReleases, "全部") {
                                navController.navigate(Screens.NEW_SONGS.name)
                            }
                        }
                        item {
                            ScrollCard("华语", Icons.Outlined.NewReleases, "华语") {}
                        }
                        item {
                            ScrollCard("欧美", Icons.Outlined.NewReleases, "欧美") {}
                        }
                        item {
                            ScrollCard("日本", Icons.Outlined.NewReleases, "日本") {}
                        }
                        item {
                            ScrollCard("韩国", Icons.Outlined.NewReleases, "韩国") {}
                        }
                    }
                }
            }
            item {
                Box {
                    ScrollHeader(false, Icons.Outlined.Leaderboard, "Leaderboards") {}
                    ScrollFixedCard("Leaderboards", Icons.Outlined.Leaderboard, "Leaderboards") {
                        navController.navigate(Screens.EXPLORE.name)
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
                            keyboardController?.hideSoftwareKeyboard()
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
                keyboardController?.showSoftwareKeyboard()
            } else {
                focusRequester.freeFocus()
                keyboardController?.hideSoftwareKeyboard()
            }
        }
    }
    ForeLayer(accountState) {
        Account()
    }
}