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
import androidx.compose.foundation.layout.height
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
import com.saharvest.cbocollector.state.DoneKind
import com.saharvest.cbocollector.ui.components.FilledPillButton
import com.saharvest.cbocollector.ui.components.OutlinePillButton
import com.saharvest.cbocollector.ui.theme.Figtree
import com.saharvest.cbocollector.ui.theme.GlyphPaths
import com.saharvest.cbocollector.ui.theme.Poppins
import com.saharvest.cbocollector.ui.theme.SaColors
import com.saharvest.cbocollector.ui.theme.StrokeIcon
import com.saharvest.cbocollector.util.rememberIsOnline
import java.util.Locale

private data class ReceiptRow(val key: String, val value: String)

@Composable
fun DoneScreen(state: AppState) {
    val online = rememberIsOnline()
    val cbo = state.selectedCbo
    val isVet = state.doneKind == DoneKind.Vet

    val title = if (isVet) "Vetting captured" else "Collection recorded"
    val blurb = if (online) {
        "Sent to the depot. You will get a confirmation once it is reviewed."
    } else {
        "Saved on the device and queued. It uploads by itself as soon as you have signal."
    }
    val receipt = if (isVet) {
        listOf(
            ReceiptRow("Organisation", cbo.name),
            ReceiptRow("Sections complete", "${state.requiredDone()}/${state.requiredTotal()}"),
            ReceiptRow("Photos", "6"),
            ReceiptRow("Reference", "VET-2026-0841"),
        )
    } else {
        listOf(
            ReceiptRow("Donor site", DONOR_SITE_NAME),
            ReceiptRow("Collected for", cbo.name),
            ReceiptRow("Total weight", String.format(Locale.US, "%.1f kg", state.totalKg())),
            ReceiptRow("Arrival · departure", "${state.arrivalTime} · ${state.departureTime ?: "10:26"}"),
            ReceiptRow("Signatures", "2 of 2"),
            ReceiptRow("Reference", "COL-2026-1174"),
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SaColors.Surface)
            .verticalScroll(rememberScrollState())
            .padding(26.dp, 32.dp, 26.dp, 32.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier.size(72.dp).clip(CircleShape).background(SaColors.Yellow),
            contentAlignment = Alignment.Center,
        ) {
            StrokeIcon(pathData = GlyphPaths.Check, tint = SaColors.Ink, strokeWidth = 2.75f, modifier = Modifier.size(34.dp))
        }
        Text(
            title,
            fontFamily = Poppins,
            fontWeight = FontWeight.SemiBold,
            fontSize = 30.sp,
            color = SaColors.Ink,
            modifier = Modifier.padding(top = 24.dp, bottom = 10.dp),
        )
        Text(blurb, fontFamily = Figtree, fontSize = 14.5.sp, lineHeight = 23.sp, color = SaColors.Muted)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 26.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(SaColors.White)
                .border(1.dp, SaColors.inkAlpha(0.12f), RoundedCornerShape(14.dp)),
        ) {
            receipt.forEachIndexed { index, row ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(18.dp, 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(row.key, fontFamily = Figtree, fontSize = 13.sp, color = SaColors.MutedLight)
                    Text(row.value, fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 13.5.sp, color = SaColors.Ink)
                }
                if (index != receipt.lastIndex) {
                    Box(Modifier.fillMaxWidth().height(1.dp).background(SaColors.inkAlpha(0.08f)))
                }
            }
        }

        FilledPillButton(
            onClick = { state.go(Screen.Sync) },
            modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
            contentPadding = PaddingValues(16.dp),
        ) {
            Text("View sync queue", fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = SaColors.Ink)
        }
        OutlinePillButton(onClick = { state.go(Screen.Home) }, modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(15.dp)) {
            Text("Back to today", fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 14.5.sp, color = SaColors.Ink)
        }
    }
}
