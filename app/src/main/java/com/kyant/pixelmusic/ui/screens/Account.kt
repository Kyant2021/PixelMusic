package com.kyant.pixelmusic.ui.screens

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.ui.layout.TwoColumnGrid
import com.kyant.pixelmusic.ui.shape.SuperellipseCornerShape
import com.kyant.pixelmusic.util.Quadruple
import com.kyant.pixelmusic.locals.LocalLogin
import com.kyant.pixelmusic.locals.ProvideLogin
import com.kyant.pixelmusic.ui.Settings
import com.kyant.pixelmusic.ui.Startup
import com.kyant.pixelmusic.ui.theme.androidGreen
import com.kyant.pixelmusic.ui.theme.androidNavy
import com.kyant.pixelmusic.ui.theme.androidOrange

@Composable
fun Account(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    ProvideLogin {
        val login = LocalLogin.current
        Column(modifier) {
            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                SuperellipseCornerShape(8.dp),
                MaterialTheme.colors.primary,
                elevation = 0.dp
            ) {
                Column(
                    Modifier
                        .clickable {
                            context.startActivity(Intent(context, Startup::class.java))
                        }
                        .padding(32.dp)
                ) {
                    Text(
                        if (login == null) "Log in to explore more" else "Welcome, ${login.profile?.nickname}!",
                        style = MaterialTheme.typography.h5
                    )
                }
            }
            TwoColumnGrid(
                listOf(
                    Quadruple(
                        "Favorites",
                        Icons.Outlined.Favorite, androidOrange,
                        null
                    ),
                    Quadruple(
                        "History",
                        Icons.Outlined.History, androidNavy,
                        null
                    ),
                    Quadruple(
                        "Statistics",
                        Icons.Outlined.TrendingUp, androidGreen,
                        null
                    ),
                    Quadruple(
                        "Settings",
                        Icons.Outlined.Settings, null,
                        Settings::class.java
                    )
                ),
                { it?.let { context.startActivity(Intent(context, it)) } }
            )
        }
    }
}