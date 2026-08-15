package com.saharvest.cbocollector.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saharvest.cbocollector.data.Screen
import com.saharvest.cbocollector.state.AppState
import com.saharvest.cbocollector.ui.components.CardButton
import com.saharvest.cbocollector.ui.components.EyebrowLabel
import com.saharvest.cbocollector.ui.theme.AlertCircleGlyph
import com.saharvest.cbocollector.ui.theme.Figtree
import com.saharvest.cbocollector.ui.theme.GlyphPaths
import com.saharvest.cbocollector.ui.theme.Poppins
import com.saharvest.cbocollector.ui.theme.SaColors
import com.saharvest.cbocollector.ui.theme.StrokeIcon

private data class AdminAlert(val title: String, val meta: String, val warn: Boolean, val onOpen: (AppState) -> Unit)

private val ADMIN_ALERTS = listOf(
    AdminAlert("11 CBOs missing an NPO certificate", "Blocks funder reporting for August", true) { it.go(Screen.AdminRegister) },
    AdminAlert("Bokamoso served totals inconsistent", "Demographic split does not sum to 410", true) { it.openSubmission(1) },
    AdminAlert("Lerato Dube has 9 visits booked this week", "Field team · consider reassigning two", false) { it.go(Screen.AdminTeam) },
)

@Composable
fun AdminHomeScreen(state: AppState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SaColors.Surface)
            .verticalScroll(rememberScrollState()),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(22.dp, 22.dp, 22.dp, 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Text("Friday, 14 August", fontFamily = Figtree, fontSize = 12.5.sp, color = SaColors.MutedLight)
                Text(
                    "Overview",
                    fontFamily = Poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 25.sp,
                    color = SaColors.Ink,
                    modifier = Modifier.padding(top = 4.dp),
                )
                Text(
                    "National · all provinces",
                    fontFamily = Figtree,
                    fontSize = 12.5.sp,
                    color = SaColors.MutedLight,
                    modifier = Modifier.padding(top = 3.dp),
                )
            }
            Box(
                modifier = Modifier.size(42.dp).clip(CircleShape).background(SaColors.Divider),
                contentAlignment = Alignment.Center,
            ) {
                Text("NK", fontFamily = Poppins, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = SaColors.Ink)
            }
        }

        CardButton(
            onClick = { state.go(Screen.AdminApprovals) },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 22.dp).padding(bottom = 14.dp),
            containerColor = SaColors.Yellow,
            borderColor = null,
            shape = RoundedCornerShape(16.dp),
            contentPadding = PaddingValues(20.dp),
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("4 vettings awaiting your decision", fontFamily = Poppins, fontWeight = FontWeight.SemiBold, fontSize = 20.sp, color = SaColors.Ink)
                    StrokeIcon(pathData = GlyphPaths.ArrowRight, tint = SaColors.Ink, modifier = Modifier.size(22.dp))
                }
                Text(
                    "2 new · 1 returned · 1 ready to approve",
                    fontFamily = Figtree,
                    fontSize = 13.sp,
                    color = SaColors.Ink.copy(alpha = 0.85f),
                    modifier = Modifier.padding(top = 6.dp),
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 22.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(14.dp))
                    .background(SaColors.Ink)
                    .padding(16.dp),
            ) {
                Text("18.4 t", fontFamily = Poppins, fontWeight = FontWeight.SemiBold, fontSize = 26.sp, color = SaColors.Cream)
                Text("delivered in August", fontFamily = Figtree, fontSize = 11.5.sp, color = SaColors.Cream.copy(alpha = 0.72f), modifier = Modifier.padding(top = 2.dp))
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(14.dp))
                    .background(SaColors.White)
                    .padding(16.dp),
            ) {
                Text("142", fontFamily = Poppins, fontWeight = FontWeight.SemiBold, fontSize = 26.sp, color = SaColors.LinkGold)
                Text("active CBOs", fontFamily = Figtree, fontSize = 11.5.sp, color = SaColors.MutedLight, modifier = Modifier.padding(top = 2.dp))
            }
        }

        EyebrowLabel("Needs attention", modifier = Modifier.padding(24.dp, 24.dp, 24.dp, 8.dp))

        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 22.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            ADMIN_ALERTS.forEach { alert ->
                CardButton(
                    onClick = { alert.onOpen(state) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    borderColor = SaColors.inkAlpha(0.12f),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
                ) {
                    val iconBg = if (alert.warn) SaColors.YellowTickBg else SaColors.AppBg
                    val iconTint = if (alert.warn) SaColors.LinkGold else SaColors.Muted
                    Box(
                        modifier = Modifier.size(34.dp).clip(CircleShape).background(iconBg),
                        contentAlignment = Alignment.Center,
                    ) {
                        AlertCircleGlyph(tint = iconTint, modifier = Modifier.size(16.dp))
                    }
                    Column(modifier = Modifier.weight(1f).padding(start = 13.dp)) {
                        Text(alert.title, fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = SaColors.Ink)
                        Text(alert.meta, fontFamily = Figtree, fontSize = 11.5.sp, color = SaColors.MutedLight)
                    }
                    StrokeIcon(pathData = GlyphPaths.ChevronRight, tint = SaColors.Faint, modifier = Modifier.size(18.dp))
                }
            }
        }

        Box(Modifier.size(1.dp, 26.dp))
    }
}
