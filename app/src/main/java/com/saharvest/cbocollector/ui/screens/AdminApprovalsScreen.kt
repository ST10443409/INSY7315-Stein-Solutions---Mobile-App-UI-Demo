package com.saharvest.cbocollector.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import com.saharvest.cbocollector.data.SUBMISSIONS
import com.saharvest.cbocollector.state.AppState
import com.saharvest.cbocollector.ui.components.CardButton
import com.saharvest.cbocollector.ui.components.PillShape
import com.saharvest.cbocollector.ui.components.TagBadge
import com.saharvest.cbocollector.ui.theme.Figtree
import com.saharvest.cbocollector.ui.theme.Poppins
import com.saharvest.cbocollector.ui.theme.SaColors

private val APPROVAL_FILTERS = listOf("Awaiting", "Returned", "Approved")

@Composable
fun AdminApprovalsScreen(state: AppState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SaColors.Surface)
            .verticalScroll(rememberScrollState())
            .padding(top = 20.dp),
    ) {
        Column(modifier = Modifier.padding(horizontal = 22.dp).padding(bottom = 12.dp)) {
            Text("Approvals", fontFamily = Poppins, fontWeight = FontWeight.SemiBold, fontSize = 24.sp, color = SaColors.Ink)
            Text(
                "${SUBMISSIONS.size} submissions · oldest 4 days",
                fontFamily = Figtree,
                fontSize = 12.5.sp,
                color = SaColors.MutedLight,
                modifier = Modifier.padding(top = 3.dp),
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 22.dp).padding(bottom = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(7.dp),
        ) {
            APPROVAL_FILTERS.forEach { label ->
                val selected = state.adminFilter == label
                Text(
                    label,
                    fontFamily = Figtree,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.5.sp,
                    color = if (selected) SaColors.Yellow else SaColors.InkSoft,
                    modifier = Modifier
                        .clip(PillShape)
                        .let {
                            if (selected) it.background(SaColors.Ink, PillShape) else it.border(1.dp, SaColors.inkAlpha(0.2f), PillShape)
                        }
                        .clickable { state.adminFilter = label }
                        .padding(horizontal = 14.dp, vertical = 8.dp),
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 22.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            SUBMISSIONS.forEachIndexed { index, submission ->
                CardButton(
                    onClick = { state.openSubmission(index) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    borderColor = SaColors.inkAlpha(0.12f),
                    contentPadding = PaddingValues(16.dp),
                ) {
                    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top,
                        ) {
                            Text(
                                submission.org,
                                fontFamily = Figtree,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.5.sp,
                                lineHeight = 20.sp,
                                color = SaColors.Ink,
                                modifier = Modifier.weight(1f).padding(end = 10.dp),
                            )
                            TagBadge(submission.state, submission.tone)
                        }
                        Text(submission.meta, fontFamily = Figtree, fontSize = 12.5.sp, color = SaColors.MutedLight)
                        if (submission.flags.isNotEmpty()) {
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                submission.flags.forEach { flag ->
                                    Text(
                                        flag,
                                        fontFamily = Figtree,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.5.sp,
                                        color = SaColors.LinkGold,
                                        modifier = Modifier
                                            .clip(PillShape)
                                            .background(SaColors.YellowTickBg, PillShape)
                                            .padding(horizontal = 9.dp, vertical = 4.dp),
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
