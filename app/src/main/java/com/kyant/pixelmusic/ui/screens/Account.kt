package com.kyant.pixelmusic.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BugReport
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.History
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kyant.inimate.shape.SuperellipseCornerShape
import com.kyant.pixelmusic.api.login
import com.kyant.pixelmusic.api.login.LoginResult
import com.kyant.pixelmusic.ui.component.TwoToneCard
import com.kyant.pixelmusic.ui.theme.androidBlue
import com.kyant.pixelmusic.ui.theme.androidOrange
import com.kyant.pixelmusic.ui.theme.googleRed
import com.kyant.pixelmusic.util.DataStore
import com.kyant.pixelmusic.util.md5
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun Account(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var openDialog by remember { mutableStateOf(false) }
    val dataStore = DataStore(context, "account")
    LazyColumn(modifier) {
        if (dataStore.getOrNull<String>("login") == null) {
            item {
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
                            .clickable { openDialog = true }
                            .padding(32.dp)
                    ) {
                        Text(
                            "Log in to explore more",
                            style = MaterialTheme.typography.h5
                        )
                    }
                }
            }
        } else {
            item {
                Card(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    SuperellipseCornerShape(8.dp),
                    MaterialTheme.colors.secondary,
                    elevation = 0.dp
                ) {
                    Column(
                        Modifier
                            .clickable { openDialog = true }
                            .padding(32.dp)
                    ) {
                        Text(
                            "Welcome, ${dataStore.getJsonOrNull<LoginResult>("login")?.profile?.nickname.orEmpty()}!",
                            style = MaterialTheme.typography.h5
                        )
                    }
                }
            }
        }
        item {
            TwoToneCard(
                androidBlue,
                "History",
                Icons.Outlined.History, "History"
            )
        }
        item {
            TwoToneCard(
                androidOrange,
                "Favorites",
                Icons.Outlined.Favorite, "Favorites"
            )
        }
        item {
            TwoToneCard(
                googleRed,
                "Join QQ group to feedback: 1026441579",
                Icons.Outlined.BugReport, "Report bug"
            )
        }
    }
    if (openDialog) {
        Dialog({ openDialog = false }) {
            var phone by remember { mutableStateOf(TextFieldValue()) }
            var password by remember { mutableStateOf(TextFieldValue()) }
            Surface {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
                    Button({
                        scope.launch {
                            dataStore.write(
                                "login",
                                Json.encodeToString(login(phone.text, password.text.md5()))
                            )
                            // openDialog = false
                        }
                    }) {
                        Text("Login")
                    }
                }
            }
        }
    }
}