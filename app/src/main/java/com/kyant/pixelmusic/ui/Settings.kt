package com.kyant.pixelmusic.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kyant.pixelmusic.ui.screens.settings.About
import com.kyant.pixelmusic.ui.screens.settings.Customize
import com.kyant.pixelmusic.ui.screens.settings.Home
import com.kyant.pixelmusic.ui.screens.settings.Screens
import com.kyant.pixelmusic.ui.theme.PixelMusicTheme

class Settings : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            PixelMusicTheme(window) {
                val navController = rememberNavController()
                BoxWithConstraints(Modifier.fillMaxSize()) {
                    NavHost(navController, Screens.Home.name) {
                        composable(Screens.Home.name) { Home(navController) }
                        composable(Screens.Customize.name) { Customize() }
                        composable(Screens.About.name) { About() }
                    }
                }
            }
        }
    }
}