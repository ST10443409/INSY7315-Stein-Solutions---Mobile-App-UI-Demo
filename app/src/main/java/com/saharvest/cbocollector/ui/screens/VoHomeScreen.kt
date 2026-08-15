package com.saharvest.cbocollector.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.saharvest.cbocollector.data.VO_TODAY_TASKS
import com.saharvest.cbocollector.state.AppState
import com.saharvest.cbocollector.ui.components.CardButton
import com.saharvest.cbocollector.ui.components.EyebrowLabel
import com.saharvest.cbocollector.ui.components.TagBadge
import com.saharvest.cbocollector.ui.components.palette
import com.saharvest.cbocollector.ui.theme.Figtree
import com.saharvest.cbocollector.ui.theme.GlyphPaths
import com.saharvest.cbocollector.ui.theme.Poppins
import com.saharvest.cbocollector.ui.theme.SaColors
import com.saharvest.cbocollector.ui.theme.StrokeIcon
import com.saharvest.cbocollector.util.rememberIsOnline

@Composable
fun VoHomeScreen(state: AppState) {
    val online = rememberIsOnline()

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
                    "Sawubona, Thandi",
                    fontFamily = Poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 25.sp,
                    color = SaColors.Ink,
                    modifier = Modifier.padding(top = 4.dp),
                )
                Text(
                    state.role ?: "Vetting officer",
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
                Text("TM", fontFamily = Poppins, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = SaColors.Ink)
            }
        }

        val netBg = if (online) SaColors.SurfaceAlt else SaColors.TagWarnBg
        val netText = if (online) SaColors.Ink else SaColors.LinkGold
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp)
                .padding(bottom = 18.dp)
                .clip(RoundedCornerShape(999.dp))
                .background(netBg, RoundedCornerShape(999.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(Modifier.size(9.dp).clip(CircleShape).background(SaColors.YellowDark))
            Text(
                if (online) "Online · syncing in background" else "Offline · working from the device",
                fontFamily = Figtree,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                color = netText,
                modifier = Modifier.weight(1f).padding(start = 10.dp),
            )
            Text(
                if (online) "View queue" else "Queue",
                fontFamily = Figtree,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.5.sp,
                color = SaColors.LinkGold,
                modifier = Modifier.clickable { state.go(Screen.VoSync) },
            )
        }

        CardButton(
            onClick = { state.startNewVettingForm() },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 22.dp),
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
                    Text("New vetting form", fontFamily = Poppins, fontWeight = FontWeight.SemiBold, fontSize = 20.sp, color = SaColors.Ink)
                    StrokeIcon(pathData = GlyphPaths.ArrowRight, tint = SaColors.Ink, modifier = Modifier.size(22.dp))
                }
                Text(
                    "11 sections · every field starts empty",
                    fontFamily = Figtree,
                    fontSize = 13.sp,
                    color = SaColors.Ink.copy(alpha = 0.85f),
                    modifier = Modifier.padding(top = 6.dp),
                )
            }
        }

        EyebrowLabel(
            "Today · ${VO_TODAY_TASKS.size} stops",
            modifier = Modifier.padding(24.dp, 24.dp, 24.dp, 8.dp),
        )

        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 22.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            VO_TODAY_TASKS.forEach { task ->
                val badge = task.badgeTone.palette()
                CardButton(
                    onClick = { state.go(Screen.VoForm) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    borderColor = SaColors.inkAlpha(0.12f),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
                ) {
                    Box(
                        modifier = Modifier.size(38.dp).clip(CircleShape).background(badge.bg),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(task.time, fontFamily = Figtree, fontWeight = FontWeight.Bold, fontSize = 11.5.sp, color = badge.text)
                    }
                    Column(modifier = Modifier.weight(1f).padding(start = 13.dp)) {
                        Text(task.org, fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 14.5.sp, color = SaColors.Ink)
                        Text(task.meta, fontFamily = Figtree, fontSize = 12.sp, color = SaColors.MutedLight)
                    }
                    TagBadge(task.kind, task.tagTone)
                }
            }
        }

        Box(Modifier.size(1.dp, 26.dp))
    }
}
