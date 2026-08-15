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
import com.saharvest.cbocollector.state.AdminDecision
import com.saharvest.cbocollector.state.AppState
import com.saharvest.cbocollector.ui.components.FilledPillButton
import com.saharvest.cbocollector.ui.components.OutlinePillButton
import com.saharvest.cbocollector.ui.theme.Figtree
import com.saharvest.cbocollector.ui.theme.GlyphPaths
import com.saharvest.cbocollector.ui.theme.Poppins
import com.saharvest.cbocollector.ui.theme.SaColors
import com.saharvest.cbocollector.ui.theme.StrokeIcon

private data class AdminReceiptRow(val key: String, val value: String)

@Composable
fun AdminDoneScreen(state: AppState) {
    val submission = state.adminSelected
    val title = when (state.adminDoneKind) {
        AdminDecision.Approve -> "Approved"
        AdminDecision.Return -> "Sent back"
        AdminDecision.Decline -> "Declined"
    }
    val blurb = if (state.adminDoneKind == AdminDecision.Approve) {
        "${submission.org} is now active and can be scheduled for collections."
    } else {
        "The officer gets your reason on their next sync, with the vetting reopened at the flagged sections."
    }
    val defaultReason = if (state.adminDoneKind == AdminDecision.Approve) "Evidence complete" else "Not recorded"
    val receipt = listOf(
        AdminReceiptRow("Organisation", submission.org),
        AdminReceiptRow("Decided by", "Naledi Khumalo"),
        AdminReceiptRow("Reason", state.adminReason.ifBlank { defaultReason }),
        AdminReceiptRow("Reference", "DEC-2026-0312"),
    )

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
            onClick = { state.go(Screen.AdminApprovals) },
            modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
            contentPadding = PaddingValues(16.dp),
        ) {
            Text("Next in the queue", fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = SaColors.Ink)
        }
        OutlinePillButton(onClick = { state.go(Screen.AdminHome) }, modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(15.dp)) {
            Text("Back to overview", fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 14.5.sp, color = SaColors.Ink)
        }
    }
}
