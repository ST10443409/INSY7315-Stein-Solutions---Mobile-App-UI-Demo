package com.saharvest.cbocollector.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saharvest.cbocollector.data.Screen
import com.saharvest.cbocollector.state.AdminDecision
import com.saharvest.cbocollector.state.AppState
import com.saharvest.cbocollector.ui.components.EyebrowLabel
import com.saharvest.cbocollector.ui.components.FilledPillButton
import com.saharvest.cbocollector.ui.components.SaTextArea
import com.saharvest.cbocollector.ui.components.ScreenHeader
import com.saharvest.cbocollector.ui.theme.Figtree
import com.saharvest.cbocollector.ui.theme.SaColors

private data class DecisionOption(val id: AdminDecision, val label: String, val desc: String)

private val DECISION_OPTIONS = listOf(
    DecisionOption(AdminDecision.Approve, "Approve for collection", "CBO becomes active and can be scheduled"),
    DecisionOption(AdminDecision.Return, "Send back to the officer", "Needs a named reason before it goes back"),
    DecisionOption(AdminDecision.Decline, "Decline", "Recorded with a reason, kept on the register"),
)

@Composable
fun AdminDetailScreen(state: AppState) {
    val submission = state.adminSelected
    val flags = submission.flags.ifEmpty { listOf("All required answers present and evidence complete") }
    val flagHeading = if (submission.flags.isNotEmpty()) {
        "${submission.flags.size} item${if (submission.flags.size == 1) "" else "s"} to resolve"
    } else {
        "Nothing flagged"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SaColors.Surface)
            .verticalScroll(rememberScrollState()),
    ) {
        ScreenHeader(
            title = submission.org,
            onBack = { state.go(Screen.AdminApprovals) },
            subtitle = submission.meta,
            modifier = Modifier.padding(20.dp, 18.dp, 20.dp, 14.dp),
        )

        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(SaColors.YellowTickBg)
                    .padding(18.dp, 16.dp),
            ) {
                EyebrowLabel(flagHeading, color = SaColors.LinkGold, modifier = Modifier.padding(bottom = 10.dp))
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    flags.forEach { flag ->
                        Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                            Box(
                                modifier = Modifier
                                    .padding(top = 7.dp)
                                    .size(6.dp)
                                    .background(SaColors.YellowDark, CircleShape),
                            )
                            Text(flag, fontFamily = Figtree, fontSize = 13.sp, lineHeight = 19.sp, color = SaColors.LinkGoldHover)
                        }
                    }
                }
            }

            Column {
                EyebrowLabel("Captured", modifier = Modifier.padding(bottom = 10.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(SaColors.White)
                        .border(1.dp, SaColors.inkAlpha(0.12f), RoundedCornerShape(14.dp)),
                ) {
                    submission.facts.forEachIndexed { index, (key, value) ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(17.dp, 13.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(key, fontFamily = Figtree, fontSize = 12.5.sp, color = SaColors.MutedLight)
                            Text(value, fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 13.5.sp, color = SaColors.Ink)
                        }
                        if (index != submission.facts.lastIndex) {
                            Box(Modifier.fillMaxWidth().height(1.dp).background(SaColors.inkAlpha(0.08f)))
                        }
                    }
                }
            }

            Column {
                EyebrowLabel("Site evidence · 4 photos", modifier = Modifier.padding(bottom = 10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    listOf("kitchen.jpg", "dry-store.jpg").forEach { EvidenceTile(it, Modifier.weight(1f)) }
                }
                Row(modifier = Modifier.padding(top = 10.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    listOf("fridge.jpg", "water.jpg").forEach { EvidenceTile(it, Modifier.weight(1f)) }
                }
            }

            Column {
                EyebrowLabel("Decision", modifier = Modifier.padding(bottom = 10.dp))
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    DECISION_OPTIONS.forEach { option ->
                        val selected = state.adminDecision == option.id
                        val shape = RoundedCornerShape(12.dp)
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(shape)
                                .background(if (selected) SaColors.Yellow else SaColors.White, shape)
                                .border(1.dp, if (selected) SaColors.YellowDark else SaColors.inkAlpha(0.14f), shape)
                                .clickable { state.adminDecision = option.id }
                                .padding(17.dp, 14.dp),
                        ) {
                            Text(option.label, fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = SaColors.Ink)
                            Text(
                                option.desc,
                                fontFamily = Figtree,
                                fontSize = 11.5.sp,
                                color = SaColors.Ink.copy(alpha = 0.8f),
                                modifier = Modifier.padding(top = 1.dp),
                            )
                        }
                    }
                }
            }

            if (state.adminNeedsReason()) {
                Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
                    Row {
                        Text("Reason sent to the officer", fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 13.5.sp, color = SaColors.Ink)
                        Text(" *", fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 13.5.sp, color = SaColors.YellowDark)
                    }
                    SaTextArea(
                        value = state.adminReason,
                        onValueChange = { state.adminReason = it },
                        placeholder = "Name what must change before resubmission",
                        minLines = 3,
                    )
                }
            }

            val decision = state.adminDecision
            val commitLabel = when (decision) {
                AdminDecision.Approve -> "Approve ${submission.org.split(" ").firstOrNull().orEmpty()}"
                AdminDecision.Return, AdminDecision.Decline -> "Send decision"
                null -> "Choose a decision"
            }
            FilledPillButton(
                onClick = { state.adminCommit() },
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                containerColor = if (decision != null) SaColors.Yellow else SaColors.Divider,
                contentColor = if (decision != null) SaColors.Ink else SaColors.Muted,
                contentPadding = PaddingValues(16.dp),
            ) {
                Text(commitLabel, fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = if (decision != null) SaColors.Ink else SaColors.Muted)
            }
        }
    }
}

@Composable
private fun EvidenceTile(filename: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(SaColors.White)
            .border(1.dp, SaColors.inkAlpha(0.1f), RoundedCornerShape(12.dp)),
    ) {
        Box(Modifier.fillMaxWidth().height(78.dp).background(SaColors.Divider))
        Text(
            filename,
            fontFamily = FontFamily.Monospace,
            fontSize = 11.sp,
            color = SaColors.MutedLight,
            modifier = Modifier.padding(11.dp, 9.dp),
        )
    }
}
