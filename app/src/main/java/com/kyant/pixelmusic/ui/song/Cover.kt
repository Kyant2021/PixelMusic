package com.kyant.pixelmusic.ui.song

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import com.kyant.pixelmusic.util.EmptyImage

@SuppressLint("ModifierParameter")
@Composable
fun Cover(
    image: ImageBitmap?,
    key: Any? = null,
    modifier: Modifier = Modifier
) {
    val alpha = remember(key) { Animatable(0f) }.apply {
        LaunchedEffect(image == null) {
            if (image != null) {
                animateTo(1f)
            } else {
                animateTo(0f)
            }
        }
    }
    Image(
        image ?: EmptyImage, null,
        modifier.alpha(alpha.value),
        contentScale = ContentScale.Crop
    )
}