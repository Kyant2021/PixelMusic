package com.kyant.pixelmusic.locals

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.beust.klaxon.Klaxon

typealias JsonParser = Klaxon

val LocalJsonParser = staticCompositionLocalOf { Klaxon() }

@Composable
fun ProvideJsonParser(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalJsonParser provides JsonParser(), content = content)
}