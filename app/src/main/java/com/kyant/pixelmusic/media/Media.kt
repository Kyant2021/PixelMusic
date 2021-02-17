package com.kyant.pixelmusic.media

import android.content.ComponentName
import android.content.Context
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.core.net.toUri
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.kyant.pixelmusic.util.DataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

object Media {
    const val NOTIFICATION_CHANNEL_ID = "Pixel Music"
    const val MEDIA_ROOT_ID = "media_root_id"
    const val EMPTY_MEDIA_ROOT_ID = "empty_root_id"

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    var player: PixelPlayer? by mutableStateOf(null)
    lateinit var browser: MediaBrowserCompat
    var session: MediaSessionCompat? by mutableStateOf(null)

    private lateinit var dataSourceFactory: DataSource.Factory
    val songs: SnapshotStateList<Song> = mutableStateListOf<Song>().onEach {
        session?.controller?.addQueueItem(it.toMediaDescription())
    }
    var nowPlaying: Int? by mutableStateOf(null)

    fun init(
        context: Context,
        connectionCallbacks: MediaBrowserCompat.ConnectionCallback
    ) {
        browser = MediaBrowserCompat(
            context,
            ComponentName(context, MediaPlaybackService::class.java),
            connectionCallbacks,
            null
        )
        dataSourceFactory = DefaultDataSourceFactory(
            context,
            Util.getUserAgent(context, "Pixel Music")
        )
    }

    fun syncPlaylistsToLocal(context: Context) {
        scope.launch {
            val dataStore = DataStore(context, "playlists")
            val songIndex = player?.currentWindowIndex
            val position = player?.currentPosition
            dataStore.write("playlist_0", songs.map { it.serialize() })
            if (songIndex != null && position != null) {
                dataStore.write(
                    "playlist_0_state",
                    Triple(songIndex, position, player?.isPlayingState ?: false)
                )
            }
        }
    }

    fun syncWithPlaylists(context: Context) {
        scope.launch {
            val dataStore = DataStore(context, "playlists")
            dataStore.getOrNull<List<SerializedSong>>("playlist_0")?.forEach {
                addSongToPlaylist(it.toSong(context))
            }
            dataStore.getOrNull<Triple<Int?, Long?, Boolean?>>("playlist_0_state")?.let {
                player?.seekTo(it.first ?: 0, it.second ?: 0)
                player?.position?.snapTo(it.second?.toFloat() ?: 0f)
                player?.playWhenReady = it.third ?: false
            }
        }
    }

    fun restore() {
        player?.stop()
        player = null
        session?.isActive = false
        session = null
        songs.clear()
        nowPlaying = null
    }

    private fun addSongToPlaylist(song: Song) {
        song.mediaUrl?.let {
            songs += song
            val source = ProgressiveMediaSource
                .Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(it.toUri()))
            player?.addMediaSource(source)
        }
    }

    fun addSongToPlaylist(index: Int, song: Song) {
        song.mediaUrl?.let {
            songs.add(index, song)
            val source = ProgressiveMediaSource
                .Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(it.toUri()))
            player?.addMediaSource(index, source)
        }
    }

    fun clearPlaylists() {
        songs.clear()
        player?.clearMediaItems()
    }
}