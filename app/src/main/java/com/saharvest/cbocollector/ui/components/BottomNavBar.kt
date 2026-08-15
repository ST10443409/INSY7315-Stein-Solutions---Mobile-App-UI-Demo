package com.saharvest.cbocollector.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saharvest.cbocollector.data.NavItem
import com.saharvest.cbocollector.data.Screen
import com.saharvest.cbocollector.ui.theme.Figtree
import com.saharvest.cbocollector.ui.theme.SaColors
import com.saharvest.cbocollector.ui.theme.StrokeIcon

@Composable
fun BottomNavBar(
    items: List<NavItem>,
    current: Screen,
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth().background(SaColors.SurfaceAlt)) {
        Box(Modifier.fillMaxWidth().height(1.dp).background(SaColors.inkAlpha(0.1f)))
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp, vertical = 8.dp)) {
            items.forEach { item ->
                val active = item.id == current
                val labelColor = if (active) SaColors.Ink else SaColors.MutedLight
                val pillShape = RoundedCornerShape(999.dp)
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onNavigate(item.id) }
                        .padding(top = 9.dp, bottom = 6.dp, start = 4.dp, end = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .clip(pillShape)
                            .background(if (active) SaColors.inkAlpha(0.08f) else Color.Transparent, pillShape)
                            .padding(horizontal = 14.dp, vertical = 4.dp),
                    ) {
                        StrokeIcon(pathData = item.pathData, tint = labelColor, modifier = Modifier.size(21.dp))
                    }
                    Text(
                        item.label,
                        fontFamily = Figtree,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 11.sp,
                        color = labelColor,
                    )
                }
            }
        }
    }
}
