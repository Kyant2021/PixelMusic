package com.kyant.pixelmusic.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.kyant.pixelmusic.ui.shape.SuperellipseCornerShape

@SuppressLint("ModifierParameter")
@Composable
fun Tag(
    color: Color,
    text: String,
    icon: ImageVector? = null,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier.padding(4.dp),
        SuperellipseCornerShape(4.dp),
        color,
        elevation = 0.dp
    ) {
        Row(
            Modifier.padding(8.dp, 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon?.let {
                Icon(it, contentDescription)
                Spacer(Modifier.width(8.dp))
            }
            Text(
                text,
                style = MaterialTheme.typography.caption
            )
        }
    }
}