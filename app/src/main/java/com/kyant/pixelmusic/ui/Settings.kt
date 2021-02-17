package com.kyant.pixelmusic.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.kyant.inimate.layout.TwoColumnGrid
import com.kyant.inimate.util.Rectple
import com.kyant.pixelmusic.ui.theme.PixelMusicTheme
import com.kyant.pixelmusic.ui.theme.androidBlue
import com.kyant.pixelmusic.ui.theme.androidOrange

class Settings : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            PixelMusicTheme(window) {
                Column(Modifier.padding(top = 24.dp, bottom = 24.dp)) {
                    Text(
                        "Settings",
                        Modifier.padding(16.dp),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = MaterialTheme.typography.h5
                    )
                    TwoColumnGrid(
                        listOf(
                            Rectple(
                                "Customize",
                                Icons.Outlined.Palette, androidOrange,
                                null
                            ),
                            Rectple(
                                "About",
                                Icons.Outlined.Info, androidBlue,
                                null
                            )
                        )
                    )
                }
            }
        }
    }
}