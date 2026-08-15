package com.saharvest.cbocollector.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saharvest.cbocollector.data.CBOS
import com.saharvest.cbocollector.state.AppState
import com.saharvest.cbocollector.ui.components.CardButton
import com.saharvest.cbocollector.ui.components.SaInputField
import com.saharvest.cbocollector.ui.components.TagBadge
import com.saharvest.cbocollector.ui.theme.Figtree
import com.saharvest.cbocollector.ui.theme.Poppins
import com.saharvest.cbocollector.ui.theme.SaColors

@Composable
fun CbosScreen(state: AppState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SaColors.Surface)
            .verticalScroll(rememberScrollState())
            .padding(top = 20.dp),
    ) {
        Column(modifier = Modifier.padding(horizontal = 22.dp, vertical = 0.dp).padding(bottom = 12.dp)) {
            Text("Organisations", fontFamily = Poppins, fontWeight = FontWeight.SemiBold, fontSize = 24.sp, color = SaColors.Ink)
            Text(
                "${CBOS.size} in your area",
                fontFamily = Figtree,
                fontSize = 12.5.sp,
                color = SaColors.MutedLight,
                modifier = Modifier.padding(top = 3.dp),
            )
        }

        SaInputField(
            value = state.cboQuery,
            onValueChange = { state.cboQuery = it },
            placeholder = "Search by name, province or ward",
            modifier = Modifier.padding(horizontal = 22.dp).padding(bottom = 14.dp),
        )

        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 22.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            val filtered = state.filteredCbos()
            filtered.forEach { cbo ->
                CardButton(
                    onClick = { state.openCbo(CBOS.indexOf(cbo)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    borderColor = SaColors.inkAlpha(0.12f),
                    contentPadding = PaddingValues(16.dp),
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top,
                        ) {
                            Text(
                                cbo.name,
                                fontFamily = Figtree,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.5.sp,
                                lineHeight = 20.sp,
                                color = SaColors.Ink,
                                modifier = Modifier.weight(1f).padding(end = 10.dp),
                            )
                            TagBadge(cbo.status, cbo.tone)
                        }
                        Text(
                            cbo.meta,
                            fontFamily = Figtree,
                            fontSize = 12.5.sp,
                            color = SaColors.MutedLight,
                            modifier = Modifier.padding(top = 8.dp),
                        )
                    }
                }
            }
        }
        Box(Modifier.size(1.dp, 26.dp))
    }
}
