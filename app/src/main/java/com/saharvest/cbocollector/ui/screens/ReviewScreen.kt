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
import com.saharvest.cbocollector.ui.theme.Figtree
import com.saharvest.cbocollector.ui.theme.Poppins
import com.saharvest.cbocollector.ui.theme.SaColors
import com.saharvest.cbocollector.util.rememberIsOnline

@Composable
fun ReviewScreen(state: AppState) {
    val online = rememberIsOnline()
    val cbo = state.selectedCbo
    val reqTotal = state.requiredTotal()
    val reqDone = state.requiredDone()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SaColors.Surface)
            .verticalScroll(rememberScrollState())
            .padding(20.dp, 20.dp, 20.dp, 26.dp),
    ) {
        ScreenHeader(
            title = "Review vetting",
            onBack = { state.go(Screen.Vetting) },
            modifier = Modifier.padding(bottom = 18.dp),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(SaColors.SurfaceAlt)
                .padding(18.dp),
        ) {
            Text(cbo.name, fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = SaColors.Ink)
            Text(
                "$reqDone of $reqTotal required answers captured · 6 photos attached",
                fontFamily = Figtree,
                fontSize = 12.5.sp,
                color = SaColors.Muted,
                modifier = Modifier.padding(top = 4.dp),
            )
        }

        Column(modifier = Modifier.fillMaxWidth().padding(top = 18.dp)) {
            VETTING_SECTIONS.forEachIndexed { index, section ->
                val complete = state.isSectionComplete(section)
                val reqSectionTotal = state.sectionRequiredTotal(section)
                val reqSectionDone = state.sectionRequiredDone(section)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { state.jumpToSection(index) }
                        .padding(vertical = 14.dp, horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(if (complete) SaColors.Yellow else SaColors.Divider),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            if (complete) "✓" else "${index + 1}",
                            fontFamily = Figtree,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = if (complete) SaColors.Ink else SaColors.Muted,
                        )
                    }
                    Text(
                        section.name,
                        fontFamily = Figtree,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = SaColors.Ink,
                        modifier = Modifier.weight(1f).padding(start = 12.dp),
                    )
                    Text(
                        if (complete) "Complete" else "$reqSectionDone of $reqSectionTotal",
                        fontFamily = Figtree,
                        fontSize = 11.5.sp,
                        color = SaColors.MutedLight,
                    )
                }
                Box(Modifier.fillMaxWidth().height(1.dp).background(SaColors.inkAlpha(0.1f)))
            }
        }

        Text(
            text = if (online) {
                "Submitting now. The record is also kept on the device until the server confirms."
            } else {
                "No signal. This submission is queued and sent automatically when you are back online."
            },
            fontFamily = Figtree,
            fontSize = 12.5.sp,
            lineHeight = 19.sp,
            color = SaColors.LinkGold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(SaColors.YellowTickBg)
                .padding(14.dp, 14.dp, 18.dp, 14.dp),
        )

        FilledPillButton(
            onClick = { state.submitVetting() },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            contentPadding = PaddingValues(16.dp),
        ) {
            Text("Submit vetting", fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = SaColors.Ink)
        }
    }
}
