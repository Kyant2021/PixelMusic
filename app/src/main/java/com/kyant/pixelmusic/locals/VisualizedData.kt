package com.kyant.pixelmusic.locals

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.platform.LocalContext
import com.kyant.pixelmusic.util.LaunchedIOEffectUnit
import com.kyant.pixelmusic.util.normalize
import linc.com.amplituda.Amplituda
import java.net.URL

typealias Amplitudes = SnapshotStateList<Double>

val LocalAmplitudes = compositionLocalOf<Amplitudes> { error("No Amplitudes was provided.") }

@Composable
fun ProvideAmplitudes(
    enabled: Boolean,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val amplituda = Amplituda(context)
    val song = LocalNowPlaying.current
    val amplitudes: Amplitudes = remember(song.id) { mutableStateListOf() }

    if (enabled) {
        ProvideCacheDataStore("songs") {
            val dataStore = LocalCacheDataStore.current
            song.id.LaunchedIOEffectUnit {
                dataStore.writeWhileNotExist(
                    song.id.toString(),
                    URL(song.mediaUrl.toString()).readBytes()
                )
                song.id?.let {
                    try {
                        amplituda.fromPath(dataStore.requirePath(it.toString()))
                            .amplitudesAsList { list ->
                                var position = 0
                                val accurateStep = 38.3 / 5
                                val step = accurateStep.toInt()
                                for (i in 0 until (list.size / accurateStep).toInt()) {
                                    val mean = list.slice(position..position + step).average()
                                    amplitudes.plusAssign(mean.normalize())
                                    position += step
                                }
                            }
                    } catch (e: NumberFormatException) {
                        println(e)
                    }
                }
            }
        }
    }
    CompositionLocalProvider(LocalAmplitudes provides amplitudes, content = content)
}