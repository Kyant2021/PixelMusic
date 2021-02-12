package com.kyant.pixelmusic.locals

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import com.kyant.pixelmusic.util.CacheDataStore
import com.kyant.pixelmusic.util.DataStore

val LocalDataStore =
    staticCompositionLocalOf<DataStore> { error("CompositionLocal LocalDataStore not present") }
val LocalCacheDataStore =
    staticCompositionLocalOf<CacheDataStore> { error("CompositionLocal LocalCacheDataStore not present") }

@Composable
fun ProvideDataStore(name: String, content: @Composable () -> Unit) {
    val dataStore = DataStore(LocalContext.current, name)
    CompositionLocalProvider(LocalDataStore provides dataStore, content = content)
}

@Composable
fun ProvideCacheDataStore(name: String, content: @Composable () -> Unit) {
    val dataStore = CacheDataStore(LocalContext.current, name)
    CompositionLocalProvider(LocalCacheDataStore provides dataStore, content = content)
}