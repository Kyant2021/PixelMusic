package com.kyant.pixelmusic

import android.app.Application

class PixelMusicApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        System.loadLibrary("blur")
    }
}