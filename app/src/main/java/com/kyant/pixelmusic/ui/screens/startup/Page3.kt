package com.kyant.pixelmusic.ui.screens.startup

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.kyant.inimate.insets.statusBarsPadding
import com.kyant.pixelmusic.api.login.LoginResult
import com.kyant.pixelmusic.util.DataStore

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Page3(start: Int, setStart: (Int) -> Unit) {
    val context = LocalContext.current
    val transition = updateTransition(start)
    val dataStore = DataStore(context, "account")
    AnimatedVisibility(
        transition.targetState == 3,
        Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        BoxWithConstraints(Modifier.fillMaxSize()) {
            Column(
                Modifier
                    .align(Alignment.Center)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    dataStore.getJsonOrNull<LoginResult>("login")?.profile?.nickname.orEmpty(),
                    color = MaterialTheme.colors.onPrimary,
                    style = MaterialTheme.typography.h5
                )
            }
            TextButton(
                { setStart(5) },
                Modifier
                    .padding(64.dp)
                    .align(Alignment.BottomEnd)
            ) {
                Text("Get started")
            }
        }
    }
}