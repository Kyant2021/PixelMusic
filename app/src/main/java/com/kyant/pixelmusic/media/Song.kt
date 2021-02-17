package com.kyant.pixelmusic.media

import android.content.Context
import android.support.v4.media.MediaDescriptionCompat
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.core.net.toUri
import com.kyant.pixelmusic.api.AlbumId
import com.kyant.pixelmusic.api.Data
import com.kyant.pixelmusic.api.SongId
import com.kyant.pixelmusic.api.findUrl
import com.kyant.pixelmusic.api.playlist.Track
import com.kyant.pixelmusic.util.loadCoverWithCache
import java.io.Serializable

data class SerializedSong(
    val id: SongId? = null,
    val albumId: AlbumId? = null,
    val title: String? = null,
    val subtitle: String? = null,
    val description: String? = null
) : Serializable

data class Song(
    val id: SongId? = null,
    val albumId: AlbumId? = null,
    val title: String? = null,
    val subtitle: String? = null,
    val description: String? = null,
    val icon: ImageBitmap? = null,
    val mediaUrl: String? = null
)

fun Song.serialize(): SerializedSong = SerializedSong(
    id,
    albumId,
    title,
    subtitle,
    description
)

suspend fun Song.fix(context: Context, size: Int = 100): Song = copy(
    icon = albumId?.loadCoverWithCache(context, size),
    mediaUrl = id?.findUrl()
)

suspend fun SerializedSong.toSong(context: Context): Song = Song(
    id,
    albumId,
    title,
    subtitle,
    description
).fix(context)

fun Song.toMediaDescription(): MediaDescriptionCompat = MediaDescriptionCompat.Builder()
    .setMediaId(id.toString())
    .setTitle(title)
    .setSubtitle(subtitle)
    .setDescription(description)
    .setIconBitmap(icon?.asAndroidBitmap())
    .setMediaUri(mediaUrl?.toUri())
    .build()

fun Track.toSong(): Song = Song(
    id,
    al?.id,
    name,
    ar?.map { it.name }?.joinToString(),
    al?.name
)

fun com.kyant.pixelmusic.api.Song.toSong(): Song = Song(
    id,
    album?.id,
    name,
    artists?.map { it.name }?.joinToString(),
    album?.name
)

fun Data.toSong(): Song = Song(
    id,
    album?.id,
    name,
    artists?.map { it.name }?.joinToString(),
    album?.name
)