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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saharvest.cbocollector.data.Screen
import com.saharvest.cbocollector.state.AppState
import com.saharvest.cbocollector.state.Signatory
import com.saharvest.cbocollector.ui.components.FilledPillButton
import com.saharvest.cbocollector.ui.components.OutlinePillButton
import com.saharvest.cbocollector.ui.components.SaDashedActionButton
import com.saharvest.cbocollector.ui.components.SaInputField
import com.saharvest.cbocollector.ui.components.SaTextArea
import com.saharvest.cbocollector.ui.components.ScreenHeader
import com.saharvest.cbocollector.ui.components.dashedBorder
import com.saharvest.cbocollector.ui.theme.Figtree
import com.saharvest.cbocollector.ui.theme.GlyphPaths
import com.saharvest.cbocollector.ui.theme.Poppins
import com.saharvest.cbocollector.ui.theme.SaColors
import com.saharvest.cbocollector.ui.theme.StrokeIcon
import java.util.Locale

const val DONOR_SITE_NAME = "Fresh Fields Wholesale · Bay 3"

@Composable
fun CollectScreen(state: AppState) {
    Column(modifier = Modifier.fillMaxSize().background(SaColors.Surface)) {
        ScreenHeader(
            title = "CBO Collection",
            onBack = { state.go(Screen.Home) },
            subtitle = DONOR_SITE_NAME,
            modifier = Modifier.padding(20.dp, 18.dp, 20.dp, 14.dp),
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(20.dp, 0.dp, 20.dp, 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                StatBox(label = "Arrival", value = state.arrivalTime, containerColor = SaColors.SurfaceAlt, textColor = SaColors.Ink, modifier = Modifier.weight(1f))
                StatBox(
                    label = "Departure",
                    value = state.departureTime ?: "—",
                    containerColor = if (state.departureTime != null) SaColors.SurfaceAlt else SaColors.AppBg,
                    textColor = if (state.departureTime != null) SaColors.Ink else SaColors.MutedLight,
                    modifier = Modifier.weight(1f),
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Products", fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 13.5.sp, color = SaColors.Ink)
                    Text(
                        "${state.lines.size} line${if (state.lines.size == 1) "" else "s"}",
                        fontFamily = Figtree,
                        fontSize = 12.sp,
                        color = SaColors.MutedLight,
                    )
                }
                state.lines.forEachIndexed { index, line ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(SaColors.White)
                            .border(1.dp, SaColors.inkAlpha(0.12f), RoundedCornerShape(12.dp))
                            .padding(16.dp, 13.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(line.category, fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = SaColors.Ink)
                            Text(line.notes.ifBlank { "No notes" }, fontFamily = Figtree, fontSize = 11.5.sp, color = SaColors.MutedLight)
                        }
                        Text(
                            "${line.kg} kg",
                            fontFamily = Poppins,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 17.sp,
                            color = SaColors.LinkGold,
                            modifier = Modifier.padding(horizontal = 12.dp),
                        )
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .background(SaColors.AppBg)
                                .clickable { state.removeProductLine(index) },
                            contentAlignment = Alignment.Center,
                        ) {
                            Text("×", fontFamily = Figtree, fontSize = 15.sp, color = SaColors.Muted)
                        }
                    }
                }
                SaDashedActionButton(onClick = { state.openAddProduct() }, modifier = Modifier.fillMaxWidth()) {
                    StrokeIcon(pathData = GlyphPaths.Plus, tint = SaColors.LinkGold, modifier = Modifier.size(17.dp))
                    Text(
                        "Add product",
                        fontFamily = Figtree,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = SaColors.Muted,
                        modifier = Modifier.padding(start = 8.dp),
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(SaColors.Ink)
                        .padding(horizontal = 18.dp, vertical = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("Total weight", fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 13.5.sp, color = SaColors.Cream)
                    Text(
                        String.format(Locale.US, "%.1f kg", state.totalKg()),
                        fontFamily = Poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp,
                        color = SaColors.Cream,
                    )
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
                Row {
                    Text("Donor Signatory Name", fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 13.5.sp, color = SaColors.Ink)
                    Text(" *", fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 13.5.sp, color = SaColors.YellowDark)
                }
                SaInputField(
                    value = state.donorName,
                    onValueChange = { state.donorName = it },
                    placeholder = "Full name of the person releasing stock",
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row {
                    Text("Signatures", fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 13.5.sp, color = SaColors.Ink)
                    Text(" *", fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 13.5.sp, color = SaColors.YellowDark)
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    SignatureButton(
                        title = "Donor",
                        signed = state.donorSigned,
                        onClick = { state.beginSigning(Signatory.Donor) },
                        modifier = Modifier.weight(1f),
                    )
                    SignatureButton(
                        title = "CBO",
                        signed = state.cboSigned,
                        onClick = { state.beginSigning(Signatory.Cbo) },
                        modifier = Modifier.weight(1f),
                    )
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row {
                    Text("Photo of donation", fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 13.5.sp, color = SaColors.Ink)
                    Text(" *", fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 13.5.sp, color = SaColors.YellowDark)
                }
                val shotCount = state.shots.count { it }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(SaColors.White)
                        .border(1.dp, SaColors.inkAlpha(0.12f), RoundedCornerShape(12.dp))
                        .clickable { state.go(Screen.Photos) }
                        .padding(16.dp, 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(checkerBrush()),
                    )
                    Column(modifier = Modifier.weight(1f).padding(horizontal = 12.dp)) {
                        Text(
                            if (shotCount > 0) "$shotCount photos attached" else "Capture donation photos",
                            fontFamily = Figtree,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = SaColors.Ink,
                        )
                        Text("Stock on the vehicle, then on the CBO floor", fontFamily = Figtree, fontSize = 11.5.sp, color = SaColors.MutedLight)
                    }
                    StrokeIcon(pathData = GlyphPaths.ChevronRight, tint = SaColors.Faint, modifier = Modifier.size(18.dp))
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
                Text("Delivery Note", fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 13.5.sp, color = SaColors.Ink)
                SaInputField(
                    value = state.deliveryNote,
                    onValueChange = { state.deliveryNote = it },
                    placeholder = "Delivery note number",
                )
                OutlinePillButton(
                    onClick = { state.attachNote() },
                    modifier = Modifier.padding(top = 4.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                ) {
                    Text(
                        if (state.noteAttached) "delivery-note-4821.jpg attached" else "Attach a photo of the note",
                        fontFamily = Figtree,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        color = SaColors.Ink,
                    )
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
                Text("Notes", fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 13.5.sp, color = SaColors.Ink)
                SaTextArea(
                    value = state.collectNotes,
                    onValueChange = { state.collectNotes = it },
                    placeholder = "Anything the depot should know",
                    minLines = 3,
                )
            }

            OutlinePillButton(onClick = { state.stampDeparture() }, modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(15.dp)) {
                Text(
                    state.departureTime?.let { "Departure stamped at $it" } ?: "Stamp departure time",
                    fontFamily = Figtree,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.5.sp,
                    color = SaColors.Ink,
                )
            }
            FilledPillButton(onClick = { state.submitCollection() }, modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(16.dp)) {
                Text("Complete collection", fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = SaColors.Ink)
            }
        }
    }
}

@Composable
private fun StatBox(
    label: String,
    value: String,
    containerColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(containerColor)
            .padding(16.dp, 13.dp),
    ) {
        Text(
            label.uppercase(),
            fontFamily = Figtree,
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp,
            letterSpacing = 1.1.sp,
            color = textColor.copy(alpha = 0.7f),
        )
        Text(
            value,
            fontFamily = Poppins,
            fontWeight = FontWeight.SemiBold,
            fontSize = 21.sp,
            color = textColor,
            modifier = Modifier.padding(top = 3.dp),
        )
    }
}

@Composable
private fun SignatureButton(title: String, signed: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val shape = RoundedCornerShape(12.dp)
    Column(
        modifier = modifier
            .clip(shape)
            .background(if (signed) SaColors.SurfaceAlt else SaColors.White, shape)
            .let {
                if (signed) it.border(1.dp, SaColors.Divider, shape)
                else it.dashedBorder(SaColors.DashedBorder, cornerRadius = 12.dp)
            }
            .clickable(onClick = onClick)
            .padding(16.dp, 12.dp),
    ) {
        Text(title, fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = SaColors.Ink)
        Text(
            if (signed) "Signed" else "Tap to sign",
            fontFamily = Figtree,
            fontSize = 11.5.sp,
            color = SaColors.Ink.copy(alpha = 0.8f),
            modifier = Modifier.padding(top = 3.dp),
        )
    }
}

private fun checkerBrush(): Brush = Brush.linearGradient(
    colors = listOf(SaColors.AppBg, SaColors.Divider),
)
