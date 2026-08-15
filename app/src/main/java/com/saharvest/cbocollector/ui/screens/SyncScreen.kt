package com.saharvest.cbocollector.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.saharvest.cbocollector.data.Tone
import com.saharvest.cbocollector.state.AppState
import com.saharvest.cbocollector.ui.components.FilledPillButton
import com.saharvest.cbocollector.ui.components.TagBadge
import com.saharvest.cbocollector.ui.theme.Figtree
import com.saharvest.cbocollector.ui.theme.GlyphPaths
import com.saharvest.cbocollector.ui.theme.Poppins
import com.saharvest.cbocollector.ui.theme.SaColors
import com.saharvest.cbocollector.ui.theme.StrokeIcon
import com.saharvest.cbocollector.util.rememberIsOnline

private data class QueueRow(val title: String, val meta: String, val state: String, val tone: Tone)

@Composable
fun SyncScreen(state: AppState) {
    val online = rememberIsOnline()
    val synced = state.synced

    val queue = listOf(
        QueueRow("Collection · Fresh Fields Wholesale", "60.5 kg · 2 signatures · 1.1 MB", if (synced) "Sent" else "Queued", if (synced) Tone.OK else Tone.WARN),
        QueueRow("Collection · Bay Harvest Bakery", "312.0 kg · delivery note 4821", if (synced) "Sent" else "Queued", if (synced) Tone.OK else Tone.WARN),
        QueueRow("Photos · Cold Chain Depot", "4 images · 3.8 MB", if (synced) "Sent" else "Waiting for Wi-Fi", if (synced) Tone.OK else Tone.NEW),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SaColors.Surface)
            .verticalScroll(rememberScrollState())
            .padding(20.dp, 20.dp, 20.dp, 26.dp),
    ) {
        Text("Sync", fontFamily = Poppins, fontWeight = FontWeight.SemiBold, fontSize = 24.sp, color = SaColors.Ink)
        Text(
            if (synced) "Everything on this device is on the server" else "3 items waiting · 7.3 MB",
            fontFamily = Figtree,
            fontSize = 12.5.sp,
            color = SaColors.MutedLight,
            modifier = Modifier.padding(top = 4.dp, bottom = 18.dp),
        )

        val panelBg = if (online) SaColors.SurfaceAlt else SaColors.Ink
        val panelText = if (online) SaColors.Ink else SaColors.Cream
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(panelBg)
                .padding(18.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(10.dp).clip(CircleShape).background(SaColors.YellowDark))
                Text(
                    if (online) "Online · syncing in background" else "Offline · working from the device",
                    fontFamily = Figtree,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.5.sp,
                    color = panelText,
                    modifier = Modifier.padding(start = 10.dp),
                )
            }
            Text(
                if (online) {
                    "Connected over mobile data. Records go first, photos follow when you reach Wi-Fi."
                } else {
                    "Work carries on offline. Nothing is lost — items leave the device the moment a connection returns."
                },
                fontFamily = Figtree,
                fontSize = 12.5.sp,
                lineHeight = 19.sp,
                color = panelText.copy(alpha = 0.85f),
                modifier = Modifier.padding(top = 8.dp),
            )
        }

        Column(modifier = Modifier.fillMaxWidth().padding(top = 18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            queue.forEach { item ->
                val palette = item.tone.let { if (it == Tone.OK) SaColors.SurfaceAlt to SaColors.Ink else SaColors.YellowTickBg to SaColors.LinkGold }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(SaColors.White)
                        .border(1.dp, SaColors.inkAlpha(0.12f), RoundedCornerShape(12.dp))
                        .padding(16.dp, 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier.size(34.dp).clip(CircleShape).background(palette.first),
                        contentAlignment = Alignment.Center,
                    ) {
                        StrokeIcon(pathData = GlyphPaths.Package, tint = palette.second, modifier = Modifier.size(16.dp))
                    }
                    Column(modifier = Modifier.weight(1f).padding(horizontal = 12.dp)) {
                        Text(item.title, fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = SaColors.Ink)
                        Text(item.meta, fontFamily = Figtree, fontSize = 11.5.sp, color = SaColors.MutedLight)
                    }
                    TagBadge(item.state, item.tone)
                }
            }
        }

        FilledPillButton(
            onClick = { if (online && !synced) state.sync() },
            modifier = Modifier.fillMaxWidth().padding(top = 18.dp),
            containerColor = if (online && !synced) SaColors.Yellow else SaColors.Divider,
            contentColor = if (online && !synced) SaColors.Ink else SaColors.Muted,
            contentPadding = PaddingValues(16.dp),
        ) {
            Text(
                if (synced) "All synced" else if (online) "Sync 3 items now" else "Retry when online",
                fontFamily = Figtree,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = if (online && !synced) SaColors.Ink else SaColors.Muted,
            )
        }

        com.saharvest.cbocollector.ui.components.OutlinePillButton(
            onClick = { state.go(com.saharvest.cbocollector.data.Screen.RoleSelection) },
            modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
            contentPadding = PaddingValues(15.dp),
        ) {
            Text(
                "Return to role selection",
                fontFamily = Figtree,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.5.sp,
                color = SaColors.Ink,
            )
        }
    }
}
