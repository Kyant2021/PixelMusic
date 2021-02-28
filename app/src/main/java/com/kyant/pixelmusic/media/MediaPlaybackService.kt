package com.kyant.pixelmusic.media

import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.media.MediaBrowserServiceCompat
import com.kyant.pixelmusic.data.Media

class MediaPlaybackService : MediaBrowserServiceCompat() {
    override fun onCreate() {
        super.onCreate()
        Media.session = MediaSessionCompat(baseContext, "PIXEL_MUSIC").apply {
            setFlags(MediaSessionCompat.FLAG_HANDLES_QUEUE_COMMANDS)
            setPlaybackState(
                PlaybackStateCompat.Builder()
                    .setActions(PlaybackStateCompat.ACTION_PLAY or PlaybackStateCompat.ACTION_PLAY_PAUSE)
                    .build()
            )
            controller?.registerCallback(object : MediaControllerCompat.Callback() {
                override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
                    if (state?.state == PlaybackStateCompat.STATE_PLAYING || state?.state == PlaybackStateCompat.STATE_PAUSED) {
                        startForeground(1, mediaStyleNotification(Media.NOTIFICATION_CHANNEL_ID))
                    }
                }
            })
            setSessionToken(sessionToken)
        }
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot {
        return BrowserRoot(Media.EMPTY_MEDIA_ROOT_ID, null)
    }

    override fun onLoadChildren(
        parentMediaId: String,
        result: Result<List<MediaBrowserCompat.MediaItem>>
    ) {
        if (Media.EMPTY_MEDIA_ROOT_ID == parentMediaId) {
            result.sendResult(null)
            return
        }
        val mediaItems = emptyList<MediaBrowserCompat.MediaItem>()
        result.sendResult(mediaItems)
    }
}