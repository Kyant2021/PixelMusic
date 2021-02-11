package com.kyant.pixelmusic.util

import android.content.Context
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import coil.ImageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.kyant.pixelmusic.api.AlbumId
import com.kyant.pixelmusic.api.findCoverUrl

val EmptyImage = ImageBitmap(1, 1)

suspend fun Any.loadImage(context: Context): ImageBitmap? = ImageLoader.Builder(context)
    .build()
    .execute(
        ImageRequest.Builder(context)
            .data(this)
            .diskCachePolicy(CachePolicy.DISABLED)
            .build()
    ).drawable?.toBitmap()?.asImageBitmap()

suspend fun AlbumId.loadCoverWithCache(
    context: Context,
    name: String,
    size: Int = 500
): ImageBitmap {
    val dataStore = CacheDataStore(context, name)
    val path = "${this}_$size.jpg"
    return if (dataStore.contains(path)) {
        dataStore.getBitmapOrNull(path)?.asImageBitmap()
    } else {
        findCoverUrl(size).toUri().loadImage(context)?.asAndroidBitmap()?.let {
            dataStore.writeBitmap(path, it)
        }
        dataStore.getBitmapOrNull(path)?.asImageBitmap()
    } ?: EmptyImage
}