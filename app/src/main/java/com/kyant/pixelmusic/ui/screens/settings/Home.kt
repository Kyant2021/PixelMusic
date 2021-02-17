package com.kyant.pixelmusic.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.kyant.pixelmusic.ui.layout.TwoColumnGrid
import com.kyant.pixelmusic.util.Quadruple
import com.kyant.pixelmusic.ui.theme.androidBlue
import com.kyant.pixelmusic.ui.theme.androidOrange

@Composable
fun Home(navController: NavHostController) {
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
                Quadruple(
                    "Customize",
                    Icons.Outlined.Palette, androidOrange,
                    Screens.Customize.name
                ),
                Quadruple(
                    "About",
                    Icons.Outlined.Info, androidBlue,
                    Screens.About.name
                )
            ),
            { navController.navigate(it) }
        )
    }
}