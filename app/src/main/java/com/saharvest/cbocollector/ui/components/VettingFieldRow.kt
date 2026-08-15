package com.saharvest.cbocollector.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saharvest.cbocollector.data.FieldType
import com.saharvest.cbocollector.data.VettingField
import com.saharvest.cbocollector.ui.theme.Figtree
import com.saharvest.cbocollector.ui.theme.GlyphPaths
import com.saharvest.cbocollector.ui.theme.SaColors
import com.saharvest.cbocollector.ui.theme.SearchGlyph
import com.saharvest.cbocollector.ui.theme.StrokeIcon

@Composable
fun VettingFieldRow(
    field: VettingField,
    valueOf: (String) -> Any?,
    setValue: (String, Any) -> Unit,
    toggleChip: (String, String) -> Unit,
) {
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

        val current = valueOf(field.key)
        when (field.type) {
            FieldType.TEXT -> SaInputField(
                value = current as? String ?: "",
                onValueChange = { setValue(field.key, it) },
                placeholder = field.placeholder ?: "",
            )

            FieldType.AREA -> {
                val text = current as? String ?: ""
                SaTextArea(
                    value = text,
                    onValueChange = { setValue(field.key, it) },
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
                onValueChange = { setValue(field.key, it) },
                placeholder = "0",
                keyboardType = KeyboardType.Number,
                modifier = Modifier.width(120.dp),
            )

            FieldType.SELECT -> SaSelectField(
                value = current as? String ?: "",
                options = field.options,
                onSelect = { setValue(field.key, it) },
            )

            FieldType.CHIPS -> {
                @Suppress("UNCHECKED_CAST")
                val selected = current as? List<String> ?: emptyList()
                FlowRow(horizontalArrangement = Arrangement.spacedBy(7.dp), verticalArrangement = Arrangement.spacedBy(7.dp)) {
                    field.options.forEach { option ->
                        SaChip(
                            label = option,
                            selected = option in selected,
                            onClick = { toggleChip(field.key, option) },
                        )
                    }
                }
            }

            FieldType.YES_NO -> Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Yes", "No").forEach { option ->
                    SaYesNoChip(
                        label = option,
                        selected = current == option,
                        onClick = { setValue(field.key, option) },
                    )
                }
            }

            FieldType.DATE -> SaInputField(
                value = current as? String ?: "",
                onValueChange = { setValue(field.key, it) },
                placeholder = "yyyy/mm/dd",
                modifier = Modifier.width(170.dp),
            )

            FieldType.MAP -> SaDashedActionButton(onClick = { setValue(field.key, "12 Ndaba Street, Diepsloot Ext 4") }) {
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
                onClick = { setValue(field.key, "certificate-scan.pdf") },
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
