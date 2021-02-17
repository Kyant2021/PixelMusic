package com.kyant.pixelmusic.ui

import android.content.*
import android.media.AudioManager
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.compose.*
import com.kyant.inimate.layer.*
import com.kyant.pixelmusic.R
import com.kyant.pixelmusic.locals.*
import com.kyant.pixelmusic.media.*
import com.kyant.pixelmusic.ui.nowplaying.NowPlaying
import com.kyant.pixelmusic.ui.player.PlayerPlaylist
import com.kyant.pixelmusic.ui.screens.*
import com.kyant.pixelmusic.ui.theme.PixelMusicTheme
import com.kyant.pixelmusic.util.DataStore
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {
    private val mediaButtonReceiver = MediaButtonReceiver()

    @OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        createNotificationChannel(Media.NOTIFICATION_CHANNEL_ID, getString(R.string.app_name))
        Media.init(this, connectionCallbacks)
        CoroutineScope(Dispatchers.IO).launch {
            if (!DataStore(this@MainActivity, "account").contains("login")) {
                startActivity(Intent(this@MainActivity, Startup::class.java))
            }
        }
        setContent {
            PixelMusicTheme(window) {
                val scope = rememberCoroutineScope()
                val navController = rememberNavController()
                val (playlistState, nowPlayingState) = rememberSwipeableState(false)
                BackHandler(playlistState.targetValue or nowPlayingState.targetValue) {
                    scope.launch {
                        if (playlistState.targetValue)
                            playlistState.animateTo(false, spring(stiffness = 700f))
                        else if (nowPlayingState.targetValue)
                            nowPlayingState.animateTo(false, spring(stiffness = 700f))
                    }
                }
                ProvidePixelPlayer {
                    Media.player = LocalPixelPlayer.current
                    BoxWithConstraints(Modifier.fillMaxSize()) {
                        NavHost(navController, Screens.Home.name) {
                            composable(Screens.Home.name) { Home(navController) }
                            composable(Screens.MyPlaylists.name) { UserPlaylists() }
                            composable(Screens.NewReleases.name) { NewReleases() }
                            composable(Screens.Leaderboards.name) { Leaderboards() }
                        }
                        ProvideNowPlaying(Media.nowPlaying) {
                            NowPlaying(nowPlayingState, playlistState)
                            ForeLayer(playlistState) {
                                PlayerPlaylist()
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
                // ContextCompat.startForegroundService(
                //     this@MainActivity,
                //     Intent(application, MediaPlaybackService::class.java)
                // )
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
        DataStore(this, "playlists")
            .getOrNull<Triple<Int?, Long?, Boolean?>>("playlist_0_state")?.let { triple ->
                triple.first?.let { Media.nowPlaying = it }
                triple.third?.let { Media.player?.playWhenReady = it }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        Media.syncPlaylistsToLocal(this)
        unregisterReceiver(mediaButtonReceiver)
        ContextCompat.getSystemService(this, MediaPlaybackService::class.java)?.stopSelf()
        Media.browser.disconnect()
        Media.restore()
    }
}