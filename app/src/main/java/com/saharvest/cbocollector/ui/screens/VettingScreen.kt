package com.saharvest.cbocollector.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saharvest.cbocollector.data.FieldType
import com.saharvest.cbocollector.data.Screen
import com.saharvest.cbocollector.data.VETTING_SECTIONS
import com.saharvest.cbocollector.data.VettingField
import com.saharvest.cbocollector.state.AppState
import com.saharvest.cbocollector.ui.components.SaDashedActionButton
import com.saharvest.cbocollector.ui.components.SaInputField
import com.saharvest.cbocollector.ui.components.SaSelectField
import com.saharvest.cbocollector.ui.components.SaTextArea
import com.saharvest.cbocollector.ui.components.SaChip
import com.saharvest.cbocollector.ui.components.SaYesNoChip
import com.saharvest.cbocollector.ui.components.ScreenHeader
import com.saharvest.cbocollector.ui.theme.Figtree
import com.saharvest.cbocollector.ui.theme.GlyphPaths
import com.saharvest.cbocollector.ui.theme.SaColors
import com.saharvest.cbocollector.ui.theme.SearchGlyph
import com.saharvest.cbocollector.ui.theme.StrokeIcon
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun VettingScreen(state: AppState) {
    val cbo = state.selectedCbo
    val pct = state.vettingProgressPct()

    Column(modifier = Modifier.fillMaxSize().background(SaColors.Surface)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(SaColors.Surface)
                .padding(20.dp, 18.dp, 20.dp, 12.dp),
        ) {
            ScreenHeader(
                title = "CBO Vetting",
                onBack = { state.go(Screen.Cbos) },
                subtitle = cbo.name,
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
                val isOpen = state.openSectionIndex == index
                val complete = state.isSectionComplete(section)
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
                            .clickable { state.toggleSection(index) }
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
                            val reqDone = state.sectionRequiredDone(section)
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
                                VettingFieldRow(field = field, state = state)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun VettingFieldRow(field: VettingField, state: AppState) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(7.dp)) {
        Row {
            Text(field.label, fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 13.5.sp, color = SaColors.Ink)
            if (field.required) {
                Text(" *", fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 13.5.sp, color = SaColors.YellowDark)
            }
        }
        if (field.hint != null) {
            Text(field.hint, fontFamily = Figtree, fontSize = 11.5.sp, lineHeight = 17.sp, color = SaColors.MutedLight)
        }

        val current = state.valueOf(field.key)
        when (field.type) {
            FieldType.TEXT -> SaInputField(
                value = current as? String ?: "",
                onValueChange = { state.setValue(field.key, it) },
                placeholder = field.placeholder ?: "",
            )

            FieldType.AREA -> {
                val text = current as? String ?: ""
                SaTextArea(
                    value = text,
                    onValueChange = { state.setValue(field.key, it) },
                    minLines = 3,
                )
                Text(
                    "${text.length}/2000",
                    fontFamily = Figtree,
                    fontSize = 11.sp,
                    color = SaColors.Faint,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                )
            }

            FieldType.NUM -> SaInputField(
                value = current as? String ?: "",
                onValueChange = { state.setValue(field.key, it) },
                placeholder = "0",
                keyboardType = KeyboardType.Number,
                modifier = Modifier.width(120.dp),
            )

            FieldType.SELECT -> SaSelectField(
                value = current as? String ?: "",
                options = field.options,
                onSelect = { state.setValue(field.key, it) },
            )

            FieldType.CHIPS -> {
                @Suppress("UNCHECKED_CAST")
                val selected = current as? List<String> ?: emptyList()
                FlowRow(horizontalArrangement = Arrangement.spacedBy(7.dp), verticalArrangement = Arrangement.spacedBy(7.dp)) {
                    field.options.forEach { option ->
                        SaChip(
                            label = option,
                            selected = option in selected,
                            onClick = { state.toggleChip(field.key, option) },
                        )
                    }
                }
            }

            FieldType.YES_NO -> Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Yes", "No").forEach { option ->
                    SaYesNoChip(
                        label = option,
                        selected = current == option,
                        onClick = { state.setValue(field.key, option) },
                    )
                }
            }

            FieldType.DATE -> SaInputField(
                value = current as? String ?: "",
                onValueChange = { state.setValue(field.key, it) },
                placeholder = "yyyy/mm/dd",
                modifier = Modifier.width(170.dp),
            )

            FieldType.MAP -> SaDashedActionButton(onClick = { state.setValue(field.key, "12 Ndaba Street, Diepsloot Ext 4") }) {
                SearchGlyph(tint = SaColors.MutedLight, modifier = Modifier.size(17.dp))
                Text(
                    current as? String ?: "Search for a location",
                    fontFamily = Figtree,
                    fontSize = 14.sp,
                    color = SaColors.Muted,
                    modifier = Modifier.padding(start = 10.dp),
                )
            }

            FieldType.UPLOAD -> SaDashedActionButton(
                onClick = { state.setValue(field.key, "certificate-scan.pdf") },
                contentPadding = PaddingValues(vertical = 20.dp, horizontal = 16.dp),
            ) {
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier.size(34.dp).clip(CircleShape).background(SaColors.YellowTickBg),
                        contentAlignment = Alignment.Center,
                    ) {
                        StrokeIcon(pathData = GlyphPaths.Upload, tint = SaColors.LinkGold, modifier = Modifier.size(17.dp))
                    }
                    Text(
                        text = (current as? String)?.let { "$it · replace" } ?: "Choose a file to upload or drag and drop here",
                        fontFamily = Figtree,
                        fontSize = 13.sp,
                        color = SaColors.Muted,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp),
                    )
                }
            }
        }
    }
}
