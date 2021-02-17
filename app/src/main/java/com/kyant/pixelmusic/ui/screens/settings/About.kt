package com.kyant.pixelmusic.ui.screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.ui.iconassets.outlinedAppleIcon

@Composable
fun About() {
    Column {
        Image(outlinedAppleIcon(), null, Modifier.size(256.dp))
    }
}