package com.kyant.pixelmusic.ui.iconassets

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Butt
import androidx.compose.ui.graphics.StrokeJoin.Round
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathBuilder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

fun appleIcon(color: Color = Color(0x00000000)) = ImageVector.Builder(
    name = "Icon", defaultWidth = 230.5.dp, defaultHeight = 230.5.dp,
    viewportWidth = 230.5f, viewportHeight = 230.5f
).apply {
    path(fill = SolidColor(color), pathFillType = NonZero) {
        drawAppleIconPath()
    }
}.build()

fun outlinedAppleIcon() = ImageVector.Builder(
    name = "Icon", defaultWidth = 230.5.dp, defaultHeight = 230.5.dp,
    viewportWidth = 230.5f, viewportHeight = 230.5f
).apply {
    path(
        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
        strokeAlpha = 0.1f, strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin
        = Round, strokeLineMiter = 4.0f, pathFillType = NonZero
    ) {
        drawAppleIconPath()
    }
}.build()

fun PathBuilder.drawAppleIconPath() {
    moveTo(158.22f, 230.0f)
    horizontalLineTo(72.28f)
    lineToRelative(-8.22f, -0.01f)
    arcToRelative(320.0f, 320.0f, 0.0f, false, true, -6.93f, -0.11f)
    curveToRelative(-5.05f, -0.06f, -10.09f, -0.5f, -15.07f, -1.32f)
    arcToRelative(50.83f, 50.83f, 0.0f, false, true, -14.33f, -4.73f)
    arcToRelative(48.2f, 48.2f, 0.0f, false, true, -21.07f, -21.06f)
    arcToRelative(50.85f, 50.85f, 0.0f, false, true, -4.72f, -14.34f)
    arcToRelative(100.68f, 100.68f, 0.0f, false, true, -1.33f, -15.07f)
    curveToRelative(-0.06f, -2.31f, -0.1f, -4.62f, -0.1f, -6.93f)
    curveToRelative(-0.02f, -2.74f, -0.02f, -5.48f, -0.02f, -8.22f)
    verticalLineTo(72.29f)
    curveToRelative(0.0f, -2.75f, 0.0f, -5.48f, 0.02f, -8.23f)
    arcToRelative(320.0f, 320.0f, 0.0f, false, true, 0.1f, -6.93f)
    curveToRelative(0.06f, -5.05f, 0.5f, -10.08f, 1.33f, -15.06f)
    arcToRelative(50.74f, 50.74f, 0.0f, false, true, 4.72f, -14.34f)
    arcTo(48.2f, 48.2f, 0.0f, false, true, 27.74f, 6.66f)
    arcToRelative(51.02f, 51.02f, 0.0f, false, true, 14.33f, -4.73f)
    curveTo(47.05f, 1.12f, 52.09f, 0.67f, 57.14f, 0.61f)
    arcToRelative(320.0f, 320.0f, 0.0f, false, true, 6.93f, -0.1f)
    lineTo(72.3f, 0.5f)
    horizontalLineToRelative(85.92f)
    lineToRelative(8.23f, 0.01f)
    arcToRelative(320.0f, 320.0f, 0.0f, false, true, 6.92f, 0.1f)
    curveToRelative(5.06f, 0.07f, 10.1f, 0.5f, 15.08f, 1.33f)
    curveToRelative(4.98f, 0.85f, 9.81f, 2.45f, 14.33f, 4.72f)
    arcToRelative(48.19f, 48.19f, 0.0f, false, true, 21.07f, 21.07f)
    arcToRelative(50.9f, 50.9f, 0.0f, false, true, 4.72f, 14.34f)
    curveToRelative(0.82f, 4.98f, 1.27f, 10.02f, 1.33f, 15.07f)
    arcToRelative(320.0f, 320.0f, 0.0f, false, true, 0.1f, 6.92f)
    lineToRelative(0.01f, 3.48f)
    verticalLineToRelative(94.09f)
    lineToRelative(-0.01f, 4.8f)
    curveToRelative(-0.01f, 2.3f, -0.05f, 4.62f, -0.1f, 6.93f)
    curveToRelative(-0.07f, 5.05f, -0.5f, 10.08f, -1.33f, 15.07f)
    arcToRelative(50.68f, 50.68f, 0.0f, false, true, -4.72f, 14.33f)
    arcToRelative(48.2f, 48.2f, 0.0f, false, true, -21.07f, 21.07f)
    arcToRelative(50.8f, 50.8f, 0.0f, false, true, -14.33f, 4.73f)
    curveToRelative(-4.98f, 0.82f, -10.02f, 1.26f, -15.08f, 1.32f)
    arcToRelative(320.0f, 320.0f, 0.0f, false, true, -6.92f, 0.1f)
    lineToRelative(-8.23f, 0.02f)
    close()
}