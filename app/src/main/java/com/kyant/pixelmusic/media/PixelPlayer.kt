package com.kyant.pixelmusic.media

import android.content.Context
import android.os.Handler
import android.support.v4.media.MediaDescriptionCompat
import androidx.compose.animation.core.*
import androidx.compose.runtime.*
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.audio.*
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.ext.mediasession.TimelineQueueNavigator
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.kyant.pixelmusic.data.Media
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.fixedRateTimer

class PixelPlayer(context: Context) :
    SimpleExoPlayer(Builder(context, object : DefaultRenderersFactory(context) {
        override fun buildAudioRenderers(
            context: Context,
            extensionRendererMode: Int,
            mediaCodecSelector: MediaCodecSelector,
            enableDecoderFallback: Boolean,
            audioSink: AudioSink,
            eventHandler: Handler,
            eventListener: AudioRendererEventListener,
            out: ArrayList<Renderer>
        ) {
            super.buildAudioRenderers(
                context,
                extensionRendererMode,
                mediaCodecSelector,
                enableDecoderFallback,
                DefaultAudioSink(
                    null,
                    arrayOf(Media.fftAudioProcessor)
                ),
                eventHandler,
                eventListener,
                out
            )
        }
    })) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val animationPeriod = AnimationConstants.DefaultDurationMillis.toLong()
    var isPlayingState: Boolean by mutableStateOf(false)
    lateinit var position: Animatable<Float, AnimationVector1D>
    lateinit var bufferedPositionState: Animatable<Float, AnimationVector1D>
    inline val progress: Float get() = position.value / duration.toFloat()
    inline val bufferedProgress: Float get() = bufferedPositionState.value / duration.toFloat()

    init {
        setThrowsWhenUsingWrongThread(false)
        Media.session?.let { MediaSessionConnector(it) }?.apply {
            setPlayer(this@PixelPlayer)
            Media.session?.let {
                setQueueNavigator(object : TimelineQueueNavigator(it) {
                    override fun getMediaDescription(
                        player: Player,
                        windowIndex: Int
                    ): MediaDescriptionCompat {
                        return Media.songs.getOrNull(windowIndex)?.toMediaDescription()
                            ?: MediaDescriptionCompat.Builder().build()
                    }
                })
            }
        }
        setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(C.USAGE_MEDIA)
                .setContentType(C.CONTENT_TYPE_MUSIC)
                .build(),
            true
        )
        addListener(object : Player.EventListener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                isPlayingState = playbackState == STATE_READY && playWhenReady
                when (playbackState) {
                    STATE_IDLE -> {
                    }
                    STATE_BUFFERING -> {
                        fixedRateTimer(
                            "buffered_position", false,
                            animationPeriod, animationPeriod
                        ) {
                            scope.launch {
                                bufferedPositionState.animateTo(
                                    bufferedPosition.toFloat(),
                                    tween(animationPeriod.toInt(), easing = LinearEasing)
                                )
                            }
                        }
                    }
                    STATE_READY -> {
                        if (playWhenReady) {
                            fixedRateTimer(
                                "position", false,
                                animationPeriod, animationPeriod
                            ) {
                                scope.launch {
                                    position.animateTo(
                                        contentPosition.toFloat(),
                                        tween(animationPeriod.toInt(), easing = LinearEasing)
                                    )
                                }
                            }
                        } else {
                            scope.launch {
                                position.stop()
                            }
                        }
                    }
                    STATE_ENDED -> {
                    }
                }
            }

            override fun onTracksChanged(
                trackGroups: TrackGroupArray,
                trackSelections: TrackSelectionArray
            ) {
                Media.nowPlaying = currentWindowIndex
            }
        })
        prepare()
    }

    fun playOrPause() {
        playWhenReady = !isPlayingState
    }

    fun seekToNext(position: Long = 0) {
        next()
        seekToPosition(position)
    }

    fun seekToPrevious(position: Long = 0) {
        previous()
        seekToPosition(position)
    }

    fun seekToPosition(position: Long) {
        CoroutineScope(SupervisorJob() + Dispatchers.Main).launch {
            this@PixelPlayer.position.snapTo(position.toFloat())
            seekTo(position)
        }
    }
}