package com.kyant.pixelmusic.media

import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.media.MediaBrowserServiceCompat
import com.kyant.pixelmusic.locals.Media

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
                    if (state?.state == PlaybackStateCompat.STATE_PLAYING ||
                        state?.state == PlaybackStateCompat.STATE_PAUSED
                    ) {
                        startForeground(
                            1, buildMediaStyleNotification(Media.NOTIFICATION_CHANNEL_ID).build()
                        )
                    }
                }

                override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
                    Media.nowPlaying = metadata?.description?.toSong()
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
        // // (Optional) Control the level of access for the specified package name.
        // // You'll need to write your own logic to do this.
        // return if (allowBrowsing(clientPackageName, clientUid)) {
        //     // Returns a root ID that clients can use with onLoadChildren() to retrieve
        //     // the content hierarchy.
        //     BrowserRoot(Media.MEDIA_ROOT_ID, null)
        // } else {
        //     // Clients can connect, but this BrowserRoot is an empty hierachy
        //     // so onLoadChildren returns nothing. This disables the ability to browse for content.
        //     BrowserRoot(Media.EMPTY_MEDIA_ROOT_ID, null)
        // }
    }

    override fun onLoadChildren(
        parentMediaId: String,
        result: Result<List<MediaBrowserCompat.MediaItem>>
    ) {
        //  Browsing not allowed
        if (Media.EMPTY_MEDIA_ROOT_ID == parentMediaId) {
            result.sendResult(null)
            return
        }

        // Assume for example that the music catalog is already loaded/cached.

        val mediaItems = emptyList<MediaBrowserCompat.MediaItem>()

        // Check if this is the root menu:
        if (Media.MEDIA_ROOT_ID == parentMediaId) {
            // Build the MediaItem objects for the top level,
            // and put them in the mediaItems list...
        } else {
            // Examine the passed parentMediaId to see which submenu we're at,
            // and put the children of that menu in the mediaItems list...
        }
        result.sendResult(mediaItems)
    }
}