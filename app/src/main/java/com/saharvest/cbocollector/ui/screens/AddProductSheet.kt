package com.saharvest.cbocollector.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saharvest.cbocollector.data.PRODUCT_CATEGORIES
import com.saharvest.cbocollector.state.AppState
import com.saharvest.cbocollector.ui.components.FilledPillButton
import com.saharvest.cbocollector.ui.components.OutlinePillButton
import com.saharvest.cbocollector.ui.components.SaInputField
import com.saharvest.cbocollector.ui.components.SaSelectField
import com.saharvest.cbocollector.ui.components.SaTextArea
import com.saharvest.cbocollector.ui.theme.Figtree
import com.saharvest.cbocollector.ui.theme.Poppins
import com.saharvest.cbocollector.ui.theme.SaColors

@Composable
fun AddProductSheet(state: AppState) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SaColors.inkAlpha(0.42f))
            .clickable(onClick = { state.closeAddProduct() }),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 28.dp))
                .background(SaColors.Surface)
                .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) {}
                .padding(22.dp, 22.dp, 22.dp, 26.dp),
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(width = 44.dp, height = 4.dp)
                    .clip(RoundedCornerShape(999.dp))
                    .background(SaColors.Divider),
            )
            Text(
                "Add product",
                fontFamily = Poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 21.sp,
                color = SaColors.Ink,
                modifier = Modifier.padding(top = 18.dp, bottom = 16.dp),
            )

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
                    Text("Category", fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = SaColors.Ink)
                    SaSelectField(
                        value = state.draftCategory,
                        options = PRODUCT_CATEGORIES,
                        onSelect = { state.draftCategory = it },
                    )
                }
                Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
                    Text("Weight in KG", fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = SaColors.Ink)
                    SaInputField(
                        value = state.draftKg,
                        onValueChange = { state.draftKg = it },
                        placeholder = "0.0",
                        keyboardType = KeyboardType.Decimal,
                        modifier = Modifier.width(140.dp),
                    )
                }
                Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
                    Text("Notes", fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = SaColors.Ink)
                    SaTextArea(
                        value = state.draftNotes,
                        onValueChange = { state.draftNotes = it },
                        placeholder = "Condition, pallet count, expiry",
                        minLines = 2,
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinePillButton(onClick = { state.closeAddProduct() }, contentPadding = PaddingValues(vertical = 14.dp, horizontal = 22.dp)) {
                        Text("Cancel", fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = SaColors.Ink)
                    }
                    FilledPillButton(
                        onClick = { state.addProductLine() },
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(14.dp),
                    ) {
                        Text("Add to collection", fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 14.5.sp, color = SaColors.Ink)
                    }
                }
            }
        }
    }
}
