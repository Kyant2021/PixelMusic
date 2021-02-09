package com.kyant.pixelmusic.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.ui.component.TwoToneCard
import com.kyant.inimate.shape.SuperellipseCornerShape
import com.kyant.pixelmusic.ui.theme.androidBlue

@Composable
fun Home(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier,
        contentPadding = PaddingValues(top = 64.dp, bottom = 64.dp)
    ) {
        item {
            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                SuperellipseCornerShape(16.dp),
                MaterialTheme.colors.secondary,
                elevation = 24.dp
            ) {
                Column(
                    Modifier
                        .clickable {}
                        .padding(32.dp)
                ) {
                    Text(
                        "Pixel Music Alpha",
                        style = MaterialTheme.typography.h5
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Still developing...",
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
        item {
            Text(
                "Try new functions",
                Modifier.padding(16.dp),
                style = MaterialTheme.typography.body1
            )
        }
        item {
            TwoToneCard(
                androidBlue,
                "Universal Search",
                Icons.Outlined.Search, "Search"
            )
        }
    }
}