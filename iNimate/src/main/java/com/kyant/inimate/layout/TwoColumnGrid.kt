package com.kyant.inimate.layout

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kyant.inimate.shape.SuperellipseCornerShape

@SuppressLint("ModifierParameter")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> TwoColumnGrid(
    items: List<Triple<String, ImageVector, T>>,
    onClick: ((T) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(GridCells.Fixed(2)) {
        items(items) {
            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                SuperellipseCornerShape(8.dp),
                MaterialTheme.colors.onSurface.copy(0.02f),
                elevation = 0.dp
            ) {
                Row(
                    Modifier
                        .clickable { onClick?.invoke(it.third) }
                        .padding(16.dp, 32.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(it.second, it.first)
                    Spacer(Modifier.width(16.dp))
                    Text(
                        it.first,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = MaterialTheme.typography.h6
                    )
                }
            }
        }
    }
}