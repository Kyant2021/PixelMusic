package com.kyant.pixelmusic.ui.shape

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.abs
import kotlin.math.pow

class SmoothRoundedCornerShape(private val radius: Double = 5.0) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            val m = radius.coerceIn(0.000_000_000_01..100.0)
            val r = minOf(size.width, size.height).toDouble() / 2
            val w = size.width.toDouble() / 2
            val h = size.height.toDouble() / 2
            for (i in 0 until (2 * r + 1).toInt()) {
                val x = (i - r) + w
                val y = abs(r.pow(m) - abs(i - r).pow(m)).pow(1 / m) + h
                if (i == 0) moveTo(x.toFloat(), y.toFloat())
                else lineTo(x.toFloat(), y.toFloat())
            }
            for (i in 2 * r.toInt() until (4 * r + 1).toInt()) {
                val x = (3 * r - i) + w
                val y = -abs(r.pow(m) - abs(3 * r - i).pow(m)).pow(1 / m) + h
                lineTo(x.toFloat(), y.toFloat())
            }
            close()
        }
        return Outline.Generic(path)
    }
}