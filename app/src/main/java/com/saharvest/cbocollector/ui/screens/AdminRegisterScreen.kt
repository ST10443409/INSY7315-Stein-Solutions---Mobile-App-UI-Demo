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
import com.saharvest.cbocollector.data.CBO_REGISTER
import com.saharvest.cbocollector.state.AppState
import com.saharvest.cbocollector.ui.components.SaInputField
import com.saharvest.cbocollector.ui.components.TagBadge
import com.saharvest.cbocollector.ui.theme.Figtree
import com.saharvest.cbocollector.ui.theme.Poppins
import com.saharvest.cbocollector.ui.theme.SaColors

@Composable
fun AdminRegisterScreen(state: AppState) {
    val query = state.adminQuery.trim().lowercase()
    val visible = CBO_REGISTER.filter {
        query.isEmpty() || "${it.name} ${it.meta} ${it.status}".lowercase().contains(query)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SaColors.Surface)
            .verticalScroll(rememberScrollState())
            .padding(top = 20.dp),
    ) {
        Column(modifier = Modifier.padding(horizontal = 22.dp).padding(bottom = 12.dp)) {
            Text("CBO register", fontFamily = Poppins, fontWeight = FontWeight.SemiBold, fontSize = 24.sp, color = SaColors.Ink)
            Text(
                "142 organisations · 6 shown",
                fontFamily = Figtree,
                fontSize = 12.5.sp,
                color = SaColors.MutedLight,
                modifier = Modifier.padding(top = 3.dp),
            )
        }

        SaInputField(
            value = state.adminQuery,
            onValueChange = { state.adminQuery = it },
            placeholder = "Search name, province or status",
            modifier = Modifier.padding(horizontal = 22.dp).padding(bottom = 14.dp),
        )

        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 22.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            visible.forEach { entry ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(SaColors.White)
                        .border(1.dp, SaColors.inkAlpha(0.12f), RoundedCornerShape(14.dp))
                        .padding(16.dp),
                ) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            entry.name,
                            fontFamily = Figtree,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp,
                            lineHeight = 20.sp,
                            color = SaColors.Ink,
                            modifier = Modifier.weight(1f).padding(end = 10.dp),
                        )
                        TagBadge(entry.status, entry.tone)
                    }
                    Text(
                        entry.meta,
                        fontFamily = Figtree,
                        fontSize = 12.5.sp,
                        color = SaColors.MutedLight,
                        modifier = Modifier.padding(top = 6.dp),
                    )
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                            .height(1.dp)
                            .background(SaColors.inkAlpha(0.08f)),
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                    ) {
                        RegisterStat(entry.kg, "this month")
                        RegisterStat(entry.people, "people served")
                        RegisterStat(entry.docs, "documents")
                    }
                }
            }
        }
    }
}

@Composable
private fun RegisterStat(value: String, label: String) {
    Column {
        Text(value, fontFamily = Poppins, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = SaColors.LinkGold)
        Text(label, fontFamily = Figtree, fontSize = 10.5.sp, color = SaColors.Faint)
    }
}
