package com.kyant.pixelmusic.ui

import android.content.*
import android.media.AudioManager
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.SoftwareKeyboardController
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.compose.*
import com.kyant.inimate.layer.*
import com.kyant.pixelmusic.R
import com.kyant.pixelmusic.api.toplist.TopList
import com.kyant.pixelmusic.locals.*
import com.kyant.pixelmusic.media.*
import com.kyant.pixelmusic.ui.component.BottomNav
import com.kyant.pixelmusic.ui.component.TopBar
import com.kyant.pixelmusic.ui.my.My
import com.kyant.pixelmusic.ui.nowplaying.NowPlaying
import com.kyant.pixelmusic.ui.player.PlayerPlaylist
import com.kyant.pixelmusic.ui.playlist.Playlist
import com.kyant.pixelmusic.ui.screens.*
import com.kyant.pixelmusic.ui.search.Search
import com.kyant.pixelmusic.ui.theme.PixelMusicTheme
import com.kyant.pixelmusic.util.currentRoute
import kotlinx.coroutines.*

enum class Screens { HOME, EXPLORE }

class MainActivity : AppCompatActivity() {
    private val mediaButtonReceiver = MediaButtonReceiver()

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        createNotificationChannel(Media.NOTIFICATION_CHANNEL_ID, getString(R.string.app_name))
        Media.init(this, connectionCallbacks)
        setContent {
            PixelMusicTheme(window) {
                val coroutineScope = rememberCoroutineScope()
                val navController = rememberNavController()
                val (myState, playerPlaylistState, nowPlayingState, playlistState, searchState) =
                    rememberSwipeableState(false)
                val topList = remember { mutableStateOf<TopList?>(null) }
                val isLight = MaterialTheme.colors.isLight
                val focusRequester = FocusRequester.Default
                val softwareKeyboardController =
                    remember { mutableStateOf<SoftwareKeyboardController?>(null) }
                val items = listOf(
                    Triple(Screens.HOME.name, "Home", Icons.Outlined.Home),
                    Triple(Screens.EXPLORE.name, "Explore", Icons.Outlined.Explore)
                )
                BackHandler(
                    myState.targetValue or
                            playerPlaylistState.targetValue or
                            nowPlayingState.targetValue or
                            playlistState.targetValue or
                            searchState.targetValue
                ) {
                    coroutineScope.launch {
                        when {
                            myState.targetValue ->
                                myState.animateTo(false, spring(stiffness = 700f))
                            playerPlaylistState.targetValue ->
                                playerPlaylistState.animateTo(false, spring(stiffness = 700f))
                            nowPlayingState.targetValue ->
                                nowPlayingState.animateTo(false, spring(stiffness = 700f))
                            playlistState.targetValue ->
                                playlistState.animateTo(false, spring(stiffness = 700f))
                            searchState.targetValue ->
                                searchState.animateTo(false, spring(stiffness = 700f))
                        }
                    }
                }
                ProvidePixelPlayer {
                    Media.player = LocalPixelPlayer.current
                    ProvideJsonParser {
                        BoxWithConstraints(Modifier.fillMaxSize()) {
                            BackLayer(
                                listOf(myState, playerPlaylistState, playlistState, searchState),
                                darkIcons = { progress, statusBarHeightRatio ->
                                    when {
                                        nowPlayingState.progressOf(constraints.maxHeight.toFloat()) >= 1f - statusBarHeightRatio / 2 -> isLight
                                        isLight -> progress <= 0.5f
                                        else -> false
                                    }
                                }
                            ) {
                                NavHost(navController, Screens.HOME.name) {
                                    composable(Screens.HOME.name) { Home() }
                                    composable(Screens.EXPLORE.name) {
                                        Explore(
                                            playlistState,
                                            topList
                                        )
                                    }
                                }
                                TopBar(searchState, myState)
                            }
                            BottomNav(
                                items,
                                { navController.currentRoute() == it },
                                { navController.navigate(it) },
                                Modifier.align(Alignment.BottomCenter)
                            )
                            ForeLayer(searchState) {
                                Search(focusRequester, softwareKeyboardController)
                                LaunchedEffect(searchState.targetValue) {
                                    if (searchState.targetValue) {
                                        focusRequester.requestFocus()
                                        softwareKeyboardController.value?.showSoftwareKeyboard()
                                    } else {
                                        focusRequester.freeFocus()
                                        softwareKeyboardController.value?.hideSoftwareKeyboard()
                                    }
                                }
                            }
                            ForeLayer(playlistState) {
                                Playlist(topList)
                            }
                            ProvideNowPlaying(Media.nowPlaying) {
                                NowPlaying(nowPlayingState, playerPlaylistState)
                                ForeLayer(playerPlaylistState) {
                                    PlayerPlaylist()
                                }
                            }
                            ForeLayer(myState) {
                                My()
                            }
                        }
                    }
                }
            }
        }
    }

    private val connectionCallbacks = object : MediaBrowserCompat.ConnectionCallback() {
        override fun onConnected() {
            Media.browser.sessionToken.also { token ->
                val mediaController = MediaControllerCompat(this@MainActivity, token)
                MediaControllerCompat.setMediaController(this@MainActivity, mediaController)
                Media.syncWithPlaylists(this@MainActivity)
            }
        }

        override fun onConnectionSuspended() {}

        override fun onConnectionFailed() {}
    }

    public override fun onStart() {
        super.onStart()
        registerReceiver(mediaButtonReceiver, IntentFilter(Intent.ACTION_MEDIA_BUTTON))
        try {
            if (!Media.browser.isConnected) {
                Media.browser.connect()
            }
        } catch (e: IllegalStateException) {
            println(e)
        }
    }

    override fun onPause() {
        super.onPause()
        Media.syncPlaylistsToLocal(this)
    }

    public override fun onResume() {
        super.onResume()
        volumeControlStream = AudioManager.STREAM_MUSIC
    }

    override fun onDestroy() {
        super.onDestroy()
        Media.syncPlaylistsToLocal(this)
        unregisterReceiver(mediaButtonReceiver)
        ContextCompat.getSystemService(this, MediaPlaybackService::class.java)?.stopSelf()
        Media.restore()
    }
}