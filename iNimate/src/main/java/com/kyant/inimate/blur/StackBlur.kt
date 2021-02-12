package com.kyant.inimate.blur

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.Executors

@Composable
fun ImageBitmap.blur(radius: Int): ImageBitmap = asAndroidBitmap().blur(radius).asImageBitmap()

fun Bitmap.blur(radius: Int): Bitmap {
    val bitmapOut = copy(Bitmap.Config.ARGB_8888, true)
    val cores = Runtime.getRuntime().availableProcessors()
    val executors = Executors.newFixedThreadPool(cores)
    val horizontal = ArrayList<NativeTask>(cores)
    val vertical = ArrayList<NativeTask>(cores)
    for (i in 0 until cores) {
        horizontal.add(NativeTask(bitmapOut, radius, cores, i, 1))
        vertical.add(NativeTask(bitmapOut, radius, cores, i, 2))
    }
    try {
        executors.invokeAll(horizontal)
    } catch (e: InterruptedException) {
        return bitmapOut
    }
    try {
        executors.invokeAll(vertical)
    } catch (e: InterruptedException) {
        return bitmapOut
    }
    return bitmapOut
}

private external fun blur(
    bitmapOut: Bitmap,
    radius: Int,
    threadCount: Int,
    threadIndex: Int,
    round: Int
)

private class NativeTask(
    private val _bitmapOut: Bitmap,
    private val _radius: Int,
    private val _totalCores: Int,
    private val _coreIndex: Int,
    private val _round: Int
) : Callable<Void?> {
    @Throws(Exception::class)
    override fun call(): Void? {
        blur(_bitmapOut, _radius, _totalCores, _coreIndex, _round)
        return null
    }
}
