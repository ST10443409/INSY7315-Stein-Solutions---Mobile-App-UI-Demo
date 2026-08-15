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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saharvest.cbocollector.data.Screen
import com.saharvest.cbocollector.state.AppState
import com.saharvest.cbocollector.ui.components.FilledPillButton
import com.saharvest.cbocollector.ui.components.ScreenHeader
import com.saharvest.cbocollector.ui.theme.Figtree
import com.saharvest.cbocollector.ui.theme.SaColors

private val VO_SHOT_LABELS = listOf("Kitchen", "Dry storage", "Fridge / freezer", "Toilets & water")

@Composable
fun VoPhotosScreen(state: AppState) {
    val captured = state.officerShots.count { it }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SaColors.Surface)
            .verticalScroll(rememberScrollState())
            .padding(20.dp, 20.dp, 20.dp, 26.dp),
    ) {
        ScreenHeader(
            title = "Photos",
            onBack = { state.go(Screen.VoForm) },
            subtitle = "$captured of 4 captured · queued at full resolution",
            modifier = Modifier.padding(bottom = 16.dp),
        )

        VO_SHOT_LABELS.chunked(2).forEach { rowLabels ->
            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                rowLabels.forEach { label ->
                    val index = VO_SHOT_LABELS.indexOf(label)
                    val shot = state.officerShots[index]
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(SaColors.White)
                            .clickable { state.officerToggleShot(index) },
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(118.dp)
                                .background(
                                    Brush.linearGradient(
                                        colors = if (shot) listOf(SaColors.SurfaceAlt, SaColors.Divider) else listOf(SaColors.SurfaceAlt, SaColors.AppBg),
                                    ),
                                ),
                        )
                        Column(modifier = Modifier.padding(13.dp, 11.dp)) {
                            Text(label, fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 12.5.sp, color = SaColors.Ink)
                            Text(
                                if (shot) "IMG_${3120 + index}.jpg" else "tap to capture",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp,
                                color = SaColors.Faint,
                                modifier = Modifier.padding(top = 2.dp),
                            )
                        }
                    }
                }
            }
        }

        FilledPillButton(
            onClick = { state.officerCaptureAllShots() },
            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
            contentPadding = PaddingValues(15.dp),
        ) {
            Text("Open camera", fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 14.5.sp, color = SaColors.Ink)
        }

        Text(
            "Photos are compressed on device and uploaded only on Wi-Fi unless you force a sync.",
            fontFamily = Figtree,
            fontSize = 12.5.sp,
            lineHeight = 19.sp,
            color = SaColors.LinkGold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(SaColors.YellowTickBg)
                .padding(14.dp, 14.dp, 18.dp, 14.dp),
        )
    }
}
