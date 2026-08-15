package com.saharvest.cbocollector.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saharvest.cbocollector.data.Screen
import com.saharvest.cbocollector.state.AppState
import com.saharvest.cbocollector.state.Signatory
import com.saharvest.cbocollector.ui.components.FilledPillButton
import com.saharvest.cbocollector.ui.components.OutlinePillButton
import com.saharvest.cbocollector.ui.components.ScreenHeader
import com.saharvest.cbocollector.ui.theme.Figtree
import com.saharvest.cbocollector.ui.theme.SaColors

@Composable
fun SignScreen(state: AppState) {
    val currentStroke = remember { mutableStateListOf<Offset>() }
    val strokes = remember { mutableStateListOf<List<Offset>>() }

    fun clear() {
        strokes.clear()
        currentStroke.clear()
        state.clearPad()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SaColors.Surface)
            .padding(20.dp, 20.dp, 20.dp, 26.dp),
    ) {
        ScreenHeader(
            title = if (state.signingWho == Signatory.Donor) "Donor signature" else "CBO signature",
            onBack = { state.go(Screen.Collect) },
            modifier = Modifier.padding(bottom = 6.dp),
        )
        Text(
            "Sign inside the box with a finger. The signature is stored with the collection record and the stamped times.",
            fontFamily = Figtree,
            fontSize = 13.sp,
            lineHeight = 20.sp,
            color = SaColors.Muted,
            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(SaColors.White)
                .border(1.dp, SaColors.inkAlpha(0.16f), RoundedCornerShape(14.dp)),
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                currentStroke.clear()
                                currentStroke.add(offset)
                                if (!state.padHasInk) state.padHasInk = true
                            },
                            onDragEnd = {
                                if (currentStroke.isNotEmpty()) strokes.add(currentStroke.toList())
                                currentStroke.clear()
                            },
                            onDrag = { change, _ -> currentStroke.add(change.position) },
                        )
                    },
            ) {
                val stroke = Stroke(width = 2.4.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
                (strokes + listOf(currentStroke.toList())).forEach { points ->
                    if (points.size < 2) return@forEach
                    val path = Path().apply {
                        moveTo(points.first().x, points.first().y)
                        points.drop(1).forEach { lineTo(it.x, it.y) }
                    }
                    drawPath(path = path, color = SaColors.Ink, style = stroke)
                }
            }
            Box(Modifier.fillMaxWidth().height(1.dp).background(SaColors.Divider))
            Text(
                if (state.padHasInk) "Signature captured" else "Draw here",
                fontFamily = Figtree,
                fontSize = 11.5.sp,
                color = SaColors.Faint,
                modifier = Modifier.padding(16.dp, 10.dp),
            )
        }

        Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            OutlinePillButton(onClick = { clear() }, contentPadding = PaddingValues(vertical = 14.dp, horizontal = 22.dp)) {
                Text("Clear", fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = SaColors.Ink)
            }
            FilledPillButton(
                onClick = { state.acceptSignature() },
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(14.dp),
            ) {
                Text("Accept signature", fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 14.5.sp, color = SaColors.Ink)
            }
        }

        Box(Modifier.weight(1f))

        Text(
            text = if (state.signingWho == Signatory.Donor) {
                "${state.donorName.ifBlank { "Donor signatory" }} · 14 Aug 2026, 10:24"
            } else {
                "${state.selectedCbo.name} · 14 Aug 2026, 10:25"
            },
            fontFamily = Figtree,
            fontSize = 12.sp,
            color = SaColors.MutedLight,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
