package com.saharvest.cbocollector.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.saharvest.cbocollector.data.FIELD_TEAM
import com.saharvest.cbocollector.ui.theme.Figtree
import com.saharvest.cbocollector.ui.theme.Poppins
import com.saharvest.cbocollector.ui.theme.SaColors

@Composable
fun AdminTeamScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SaColors.Surface)
            .verticalScroll(rememberScrollState())
            .padding(top = 20.dp),
    ) {
        Column(modifier = Modifier.padding(horizontal = 22.dp).padding(bottom = 16.dp)) {
            Text("Field team", fontFamily = Poppins, fontWeight = FontWeight.SemiBold, fontSize = 24.sp, color = SaColors.Ink)
            Text(
                "Workload this week",
                fontFamily = Figtree,
                fontSize = 12.5.sp,
                color = SaColors.MutedLight,
                modifier = Modifier.padding(top = 3.dp),
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 22.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            FIELD_TEAM.forEach { member ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(SaColors.White)
                        .border(1.dp, SaColors.inkAlpha(0.12f), RoundedCornerShape(14.dp))
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier.size(40.dp).clip(CircleShape).background(SaColors.Divider),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(member.initials, fontFamily = Poppins, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = SaColors.Ink)
                    }
                    Column(modifier = Modifier.weight(1f).padding(horizontal = 13.dp)) {
                        Text(member.name, fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 14.5.sp, color = SaColors.Ink)
                        Text(member.role, fontFamily = Figtree, fontSize = 11.5.sp, color = SaColors.MutedLight)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp)
                                .height(6.dp)
                                .clip(RoundedCornerShape(999.dp))
                                .background(SaColors.AppBg),
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(member.loadPct / 100f)
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(999.dp))
                                    .background(SaColors.Yellow),
                            )
                        }
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(member.count, fontFamily = Poppins, fontWeight = FontWeight.SemiBold, fontSize = 17.sp, color = SaColors.LinkGold)
                        Text("visits", fontFamily = Figtree, fontSize = 10.5.sp, color = SaColors.Faint)
                    }
                }
            }
        }
    }
}
