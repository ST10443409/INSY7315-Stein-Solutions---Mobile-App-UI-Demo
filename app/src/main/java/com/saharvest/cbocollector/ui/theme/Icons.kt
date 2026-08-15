package com.saharvest.cbocollector.ui.theme

import androidx.compose.foundation.Canvas
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.vector.PathParser

/**
 * Path data lifted verbatim from the design's inline SVGs (24x24 viewBox,
 * stroke-only, round caps/joins) so on-screen glyphs match pixel-for-pixel.
 */
object GlyphPaths {
    const val ChevronBack = "M15 6l-6 6 6 6"
    const val ChevronRight = "M9 6l6 6-6 6"
    const val ArrowRight = "M5 12h14M13 6l6 6-6 6"
    const val Check = "M4 12.5 9.5 18 20 6.5"
    const val Plus = "M12 5v14M5 12h14"
    const val Upload = "M12 17V4M6 10l6-6 6 6M4 20h16"
    const val Package = "M3 7.5 12 3l9 4.5v9L12 21l-9-4.5z"
    const val SearchTail = "M20 20l-4-4"
    const val NavHome = "M3 10.5 12 3l9 7.5M5 9.5V21h14V9.5"
    const val NavCollect = "M3 7.5 12 3l9 4.5v9L12 21l-9-4.5zM3 7.5 12 12l9-4.5M12 12v9"
    const val NavHistory = "M12 7v5l4 2M20.5 12a8.5 8.5 0 1 1-8.5-8.5"
    const val NavSync = "M20.5 12a8.5 8.5 0 1 1-2.6-6.1M20 3.5v5h-5"
}

/** Draws one or more SVG-style stroke paths, scaled uniformly from a 24x24 viewBox. */
@Composable
fun StrokeIcon(
    pathData: String,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
    strokeWidth: Float = 2.75f,
    viewport: Float = 24f,
) {
    val path = remember(pathData) { PathParser().parsePathString(pathData).toPath() }
    Canvas(modifier = modifier) {
        val factor = size.minDimension / viewport
        scale(factor, factor, pivot = Offset.Zero) {
            drawPath(
                path = path,
                color = tint,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round),
            )
        }
    }
}

/** The map-field magnifying-glass glyph: a circle plus a short tail stroke. */
@Composable
fun SearchGlyph(
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
    strokeWidth: Float = 2.75f,
) {
    val tail = remember { PathParser().parsePathString(GlyphPaths.SearchTail).toPath() }
    Canvas(modifier = modifier) {
        val factor = size.minDimension / 24f
        scale(factor, factor, pivot = Offset.Zero) {
            drawCircle(
                color = tint,
                radius = 7f,
                center = Offset(11f, 11f),
                style = Stroke(width = strokeWidth),
            )
            drawPath(
                path = tail,
                color = tint,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round),
            )
        }
    }
}
