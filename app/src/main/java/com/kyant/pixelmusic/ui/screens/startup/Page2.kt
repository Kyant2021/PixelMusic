package com.kyant.pixelmusic.ui.screens.startup

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.kyant.inimate.insets.statusBarsPadding
import com.kyant.pixelmusic.api.login
import com.kyant.pixelmusic.util.DataStore
import com.kyant.pixelmusic.util.md5
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Page2(start: Int, setStart: (Int) -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var phone by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }
    var waiting by remember { mutableStateOf(false) }
    val transition = updateTransition(start)
    val dataStore = DataStore(context, "account")
    AnimatedVisibility(
        transition.targetState == 2,
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
                OutlinedTextField(
                    phone,
                    { phone = it },
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    label = { Text("Phone number") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true
                )
                OutlinedTextField(
                    password,
                    { password = it },
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    label = { Text("Password") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true
                )
                Row(Modifier.padding(16.dp)) {
                    Button({
                        scope.launch {
                            waiting = true
                            dataStore.write(
                                "login",
                                Json.encodeToString(login(phone.text, password.text.md5()))
                            )
                            waiting = false
                            setStart(3)
                        }
                    }) {
                        Text("Login")
                    }
                    Spacer(Modifier.width(16.dp))
                    TextButton({
                        scope.launch {
                            waiting = true
                            dataStore.write("login", null)
                            waiting = false
                            setStart(5)
                        }
                    }) {
                        Text("Skip login")
                    }
                }
                if (waiting) {
                    CircularProgressIndicator(Modifier.padding(32.dp))
                }
            }
        }
    }
}