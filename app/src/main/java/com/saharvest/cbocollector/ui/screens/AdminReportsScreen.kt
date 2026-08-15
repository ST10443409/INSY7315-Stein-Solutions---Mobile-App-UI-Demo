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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saharvest.cbocollector.data.PROVINCE_BARS
import com.saharvest.cbocollector.state.AppState
import com.saharvest.cbocollector.ui.components.OutlinePillButton
import com.saharvest.cbocollector.ui.theme.Figtree
import com.saharvest.cbocollector.ui.theme.Poppins
import com.saharvest.cbocollector.ui.theme.SaColors

private data class ReportRow(val key: String, val value: String)

private val REPORT_ROWS = listOf(
    ReportRow("Collections logged", "96"),
    ReportRow("Average per collection", "192 kg"),
    ReportRow("CBOs approved in August", "11"),
)

@Composable
fun AdminReportsScreen(state: AppState) {
    val maxTonnes = PROVINCE_BARS.maxOf { it.tonnes }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SaColors.Surface)
            .verticalScroll(rememberScrollState())
            .padding(top = 20.dp),
    ) {
        Column(modifier = Modifier.padding(horizontal = 22.dp).padding(bottom = 14.dp)) {
            Text("Reports", fontFamily = Poppins, fontWeight = FontWeight.SemiBold, fontSize = 24.sp, color = SaColors.Ink)
            Text(
                "August · tonnage by province",
                fontFamily = Figtree,
                fontSize = 12.5.sp,
                color = SaColors.MutedLight,
                modifier = Modifier.padding(top = 3.dp),
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp)
                .padding(bottom = 18.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(SaColors.Ink)
                .padding(18.dp, 20.dp),
        ) {
            Row(verticalAlignment = Alignment.Bottom) {
                Text("18.4", fontFamily = Poppins, fontWeight = FontWeight.SemiBold, fontSize = 40.sp, color = SaColors.Cream, lineHeight = 36.sp)
                Text(
                    "tonnes delivered",
                    fontFamily = Figtree,
                    fontSize = 14.sp,
                    color = SaColors.Cream.copy(alpha = 0.7f),
                    modifier = Modifier.padding(start = 8.dp, bottom = 5.dp),
                )
            }
            Column(modifier = Modifier.padding(top = 20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                PROVINCE_BARS.forEach { bar ->
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            bar.label,
                            fontFamily = Figtree,
                            fontSize = 12.sp,
                            color = SaColors.Cream.copy(alpha = 0.8f),
                            modifier = Modifier.width(96.dp),
                        )
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(12.dp)
                                .clip(RoundedCornerShape(999.dp))
                                .background(SaColors.White.copy(alpha = 0.14f)),
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(bar.tonnes / maxTonnes)
                                    .height(12.dp)
                                    .clip(RoundedCornerShape(999.dp))
                                    .background(SaColors.Yellow),
                            )
                        }
                        Text(
                            bar.value,
                            fontFamily = Figtree,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp,
                            color = SaColors.Cream,
                            textAlign = TextAlign.End,
                            modifier = Modifier.width(44.dp),
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 22.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            REPORT_ROWS.forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(SaColors.White)
                        .border(1.dp, SaColors.inkAlpha(0.12f), RoundedCornerShape(12.dp))
                        .padding(17.dp, 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(row.key, fontFamily = Figtree, fontSize = 13.5.sp, color = SaColors.Muted)
                    Text(row.value, fontFamily = Poppins, fontWeight = FontWeight.SemiBold, fontSize = 18.sp, color = SaColors.LinkGold)
                }
            }
        }

        OutlinePillButton(
            onClick = { state.exportFunderPack() },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 22.dp).padding(top = 18.dp),
            contentPadding = PaddingValues(15.dp),
        ) {
            Text(
                if (state.adminExported) "Funder pack emailed to you" else "Export funder pack (PDF)",
                fontFamily = Figtree,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.5.sp,
                color = SaColors.Ink,
            )
        }

        OutlinePillButton(
            onClick = { state.go(com.saharvest.cbocollector.data.Screen.RoleSelection) },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 22.dp).padding(top = 12.dp),
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

        Box(Modifier.size(1.dp, 26.dp))
    }
}
