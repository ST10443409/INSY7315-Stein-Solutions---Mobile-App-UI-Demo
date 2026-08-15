package com.saharvest.cbocollector.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.PaddingValues
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
import com.saharvest.cbocollector.data.VETTING_SECTIONS
import com.saharvest.cbocollector.state.AppState
import com.saharvest.cbocollector.ui.components.FilledPillButton
import com.saharvest.cbocollector.ui.components.ScreenHeader
import com.saharvest.cbocollector.ui.components.VettingFieldRow
import com.saharvest.cbocollector.ui.theme.Figtree
import com.saharvest.cbocollector.ui.theme.SaColors

@Composable
fun VoFormScreen(state: AppState) {
    val pct = state.officerProgressPct()

    Column(modifier = Modifier.fillMaxSize().background(SaColors.Surface)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(SaColors.Surface)
                .padding(20.dp, 18.dp, 20.dp, 12.dp),
        ) {
            ScreenHeader(
                title = "New vetting form",
                onBack = { state.go(Screen.VoHome) },
                subtitle = state.officerFormRef,
                trailing = {
                    Text(
                        "Draft",
                        fontFamily = Figtree,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        color = SaColors.LinkGold,
                        modifier = Modifier
                            .clip(RoundedCornerShape(999.dp))
                            .background(SaColors.YellowTickBg, RoundedCornerShape(999.dp))
                            .padding(horizontal = 10.dp, vertical = 5.dp),
                    )
                },
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(8.dp)
                        .clip(RoundedCornerShape(999.dp))
                        .background(SaColors.Divider),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(pct / 100f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(999.dp))
                            .background(SaColors.Yellow),
                    )
                }
                Text(
                    "$pct%",
                    fontFamily = Figtree,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = SaColors.Muted,
                    modifier = Modifier.padding(start = 10.dp),
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(20.dp, 14.dp, 20.dp, 24.dp),
        ) {
            VETTING_SECTIONS.forEachIndexed { index, section ->
                val isOpen = state.officerOpenSectionIndex == index
                val complete = state.officerIsSectionComplete(section)
                val containerColor = when {
                    isOpen -> SaColors.White
                    complete -> SaColors.Ink
                    else -> SaColors.SurfaceAlt
                }
                val borderColor = when {
                    isOpen -> SaColors.Yellow
                    complete -> SaColors.Divider
                    else -> SaColors.inkAlpha(0.12f)
                }
                val textColor = if (complete && !isOpen) SaColors.Cream else SaColors.Ink
                val numBg = if (complete || isOpen) SaColors.Yellow else SaColors.Divider
                val numText = if (complete || isOpen) SaColors.Ink else SaColors.Muted

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(containerColor)
                        .border(1.dp, borderColor, RoundedCornerShape(14.dp)),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { state.officerToggleSection(index) }
                            .padding(16.dp, 15.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = Modifier.size(26.dp).clip(CircleShape).background(numBg),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                if (complete) "✓" else "${index + 1}",
                                fontFamily = Figtree,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                color = numText,
                            )
                        }
                        Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
                            Text(section.name, fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 14.5.sp, color = textColor)
                            val reqTotal = state.sectionRequiredTotal(section)
                            val reqDone = state.officerSectionRequiredDone(section)
                            Text(
                                if (complete) "Complete" else "$reqDone of $reqTotal required answered",
                                fontFamily = Figtree,
                                fontSize = 11.5.sp,
                                color = if (complete && !isOpen) SaColors.Cream.copy(alpha = 0.6f) else SaColors.MutedLight,
                            )
                        }
                        Text(
                            if (isOpen) "▲" else "▼",
                            fontFamily = Figtree,
                            fontSize = 13.sp,
                            color = if (complete && !isOpen) SaColors.Cream.copy(alpha = 0.6f) else SaColors.MutedLight,
                        )
                    }
                    if (isOpen) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(16.dp, 4.dp, 16.dp, 20.dp),
                            verticalArrangement = Arrangement.spacedBy(18.dp),
                        ) {
                            section.fields.forEach { field ->
                                VettingFieldRow(
                                    field = field,
                                    valueOf = state::officerValueOf,
                                    setValue = state::officerSetValue,
                                    toggleChip = state::officerToggleChip,
                                )
                            }
                        }
                    }
                }
            }
            FilledPillButton(
                onClick = { state.go(Screen.VoReview) },
                modifier = Modifier.fillMaxWidth().padding(top = 6.dp),
                contentPadding = PaddingValues(15.dp),
            ) {
                Text("Review & submit", fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = SaColors.Ink)
            }
        }
    }
}
