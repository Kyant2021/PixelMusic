package com.kyant.inimate.scroll

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@SuppressLint("ModifierParameter")
@Composable
fun BoxScope.ScrollCard(
    label: String,
    icon: ImageVector,
    contentDescription: String?,
    tint: Color = LocalContentColor.current,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier
            .padding(4.dp)
            .size(104.dp)
            .align(Alignment.CenterStart)
            .clickable { onClick() },
        RoundedCornerShape(4.dp),
        border = BorderStroke(1.dp, MaterialTheme.colors.onSurface.copy(0.12f)),
        elevation = 0.dp
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            Arrangement.Center,
            Alignment.CenterHorizontally
        ) {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Icon(icon, contentDescription, tint = tint)
                Spacer(Modifier.height(8.dp))
                Text(
                    label,
                    color = tint,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.subtitle2
                )
            }
        }
    }
}

@SuppressLint("ModifierParameter")
@Composable
fun BoxScope.ScrollFixedCard(
    label: String,
    icon: ImageVector,
    contentDescription: String?,
    tint: Color = LocalContentColor.current,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier
            .fillMaxWidth()
            .padding(4.dp)
            .padding(start = 64.dp)
            .height(104.dp)
            .align(Alignment.CenterStart)
            .clickable { onClick() },
        RoundedCornerShape(4.dp),
        border = BorderStroke(1.dp, MaterialTheme.colors.onSurface.copy(0.12f)),
        elevation = 0.dp
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            Arrangement.Center,
            Alignment.CenterHorizontally
        ) {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Icon(icon, contentDescription, tint = tint)
                Spacer(Modifier.height(8.dp))
                Text(
                    label,
                    color = tint,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.subtitle2
                )
            }
        }
    }
}

@Composable
fun BoxScope.ScrollMoreCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier
            .padding(4.dp)
            .size(48.dp, 104.dp)
            .align(Alignment.CenterStart)
            .clickable { onClick() },
        RoundedCornerShape(4.dp),
        border = BorderStroke(1.dp, MaterialTheme.colors.onSurface.copy(0.12f)),
        elevation = 0.dp
    ) {
        Column(
            Modifier.fillMaxSize(),
            Arrangement.Center,
            Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Outlined.MoreVert,
                "More",
                tint = MaterialTheme.colors.primary
            )
        }
    }
}