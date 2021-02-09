package com.kyant.pixelmusic.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ChipGroup(
    items: List<Triple<String, String, ImageVector>>,
    selected: @Composable (String) -> Boolean,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier
            .background(
                MaterialTheme.colors.onSurface.copy(0.02f),
                RoundedCornerShape(50)
            )
            .padding(2.dp)
    ) {
        items(items.toList()) { (route, label, icon) ->
            val color = animateColorAsState(
                if (selected(route)) MaterialTheme.colors.primary
                else MaterialTheme.colors.onSurface.copy(ContentAlpha.medium)
            ).value
            Row(
                Modifier
                    .height(32.dp)
                    .clip(RoundedCornerShape(50))
                    .background(
                        MaterialTheme.colors.primary.copy(
                            animateFloatAsState(if (selected(route)) 0.08f else 0f).value
                        )
                    )
                    .clickable { onClick(route) }
                    .then(
                        if (!MaterialTheme.colors.isLight && selected(route))
                            Modifier.border(
                                BorderStroke(1.dp, MaterialTheme.colors.primary),
                                RoundedCornerShape(50)
                            )
                        else Modifier
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    icon,
                    label,
                    Modifier
                        .padding(start = 12.dp, end = 4.dp)
                        .size(20.dp),
                    color
                )
                Text(
                    label,
                    Modifier.padding(start = 4.dp, end = 12.dp),
                    color,
                    style = MaterialTheme.typography.subtitle2
                )
            }
            Spacer(Modifier.width(8.dp))
        }
    }
}