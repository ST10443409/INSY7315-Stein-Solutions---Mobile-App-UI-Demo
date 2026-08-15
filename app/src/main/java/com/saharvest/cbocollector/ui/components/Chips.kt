package com.saharvest.cbocollector.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saharvest.cbocollector.ui.theme.Figtree
import com.saharvest.cbocollector.ui.theme.SaColors

@Composable
fun SaChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 14.dp, vertical = 9.dp),
) {
    val shape = PillShape
    Text(
        text = label,
        fontFamily = Figtree,
        fontWeight = FontWeight.SemiBold,
        fontSize = 13.sp,
        color = if (selected) SaColors.Ink else SaColors.InkSoft,
        modifier = modifier
            .clip(shape)
            .background(if (selected) SaColors.Yellow else SaColors.White, shape)
            .border(1.dp, if (selected) SaColors.YellowDark else SaColors.inkAlpha(0.16f), shape)
            .clickable(onClick = onClick)
            .padding(contentPadding),
    )
}

@Composable
fun SaYesNoChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SaChip(
        label = label,
        selected = selected,
        onClick = onClick,
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 26.dp, vertical = 11.dp),
    )
}
