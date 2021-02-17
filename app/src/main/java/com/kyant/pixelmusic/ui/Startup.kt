package com.kyant.pixelmusic.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.kyant.pixelmusic.ui.screens.startup.Page1
import com.kyant.pixelmusic.ui.screens.startup.Page2
import com.kyant.pixelmusic.ui.screens.startup.Page3
import com.kyant.pixelmusic.ui.theme.PixelMusicTheme
import kotlinx.coroutines.delay

class Startup : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            PixelMusicTheme(window) {
                val (start, setStart) = remember { mutableStateOf(0) }
                BackHandler(start != 1) {
                    setStart(1)
                }
                BoxWithConstraints(Modifier.fillMaxSize()) {
                    Page1(start, setStart)
                    Page2(start, setStart)
                    Page3(start, setStart)
                }
                LaunchedEffect(Unit) {
                    delay(50)
                    setStart(1)
                }
                LaunchedEffect(start == 5) {
                    if (start == 5) {
                        delay(500)
                        finish()
                    }
                }
            }
        }
    }
}