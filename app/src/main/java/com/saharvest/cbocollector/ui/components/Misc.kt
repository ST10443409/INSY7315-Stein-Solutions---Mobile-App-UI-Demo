package com.saharvest.cbocollector.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saharvest.cbocollector.data.Tone
import com.saharvest.cbocollector.ui.theme.Figtree
import com.saharvest.cbocollector.ui.theme.GlyphPaths
import com.saharvest.cbocollector.ui.theme.Poppins
import com.saharvest.cbocollector.ui.theme.SaColors
import com.saharvest.cbocollector.ui.theme.StrokeIcon

data class TagPalette(val bg: Color, val text: Color)

fun Tone.palette(): TagPalette = when (this) {
    Tone.OK -> TagPalette(SaColors.TagOkBg, SaColors.TagOkText)
    Tone.WARN -> TagPalette(SaColors.TagWarnBg, SaColors.TagWarnText)
    Tone.NEW -> TagPalette(SaColors.TagNewBg, SaColors.TagNewText)
}

@Composable
fun TagBadge(text: String, tone: Tone, modifier: Modifier = Modifier) {
    val palette = tone.palette()
    Text(
        text = text,
        fontFamily = Figtree,
        fontWeight = FontWeight.Bold,
        fontSize = 10.5.sp,
        color = palette.text,
        modifier = modifier
            .clip(PillShape)
            .background(palette.bg, PillShape)
            .padding(horizontal = 9.dp, vertical = 4.dp),
    )
}

@Composable
fun EyebrowLabel(text: String, modifier: Modifier = Modifier, color: Color = SaColors.MutedLight) {
    Text(
        text = text.uppercase(),
        fontFamily = Figtree,
        fontWeight = FontWeight.SemiBold,
        fontSize = 11.sp,
        letterSpacing = 1.76.sp,
        color = color,
        modifier = modifier,
    )
}

@Composable
fun OrDivider(modifier: Modifier = Modifier, label: String = "or") {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.weight(1f).height(1.dp).background(SaColors.inkAlpha(0.12f)))
        Text(
            label,
            fontFamily = Figtree,
            fontSize = 11.5.sp,
            color = SaColors.MutedLight,
            modifier = Modifier.padding(horizontal = 12.dp),
        )
        Box(Modifier.weight(1f).height(1.dp).background(SaColors.inkAlpha(0.12f)))
    }
}

@Composable
fun RememberCheckbox(checked: Boolean, onToggle: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.clickable(onClick = onToggle),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val boxShape = RoundedCornerShape(5.dp)
        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(boxShape)
                .background(if (checked) SaColors.Yellow else SaColors.White, boxShape)
                .border(1.dp, if (checked) SaColors.YellowDark else SaColors.inkAlpha(0.28f), boxShape),
            contentAlignment = Alignment.Center,
        ) {
            if (checked) {
                StrokeIcon(pathData = GlyphPaths.Check, strokeWidth = 3.4f, modifier = Modifier.size(13.dp))
            }
        }
        Spacer(Modifier.size(9.dp))
        Text("Remember me", fontFamily = Figtree, fontSize = 13.sp, color = SaColors.InkSoft)
    }
}

@Composable
fun ScreenHeader(
    title: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    trailing: @Composable () -> Unit = {},
) {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        BackCircleButton(onClick = onBack)
        Spacer(Modifier.size(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                title,
                fontFamily = Poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 19.sp,
                color = SaColors.Ink,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            if (subtitle != null) {
                Text(
                    subtitle,
                    fontFamily = Figtree,
                    fontSize = 12.sp,
                    color = SaColors.MutedLight,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        trailing()
    }
}
