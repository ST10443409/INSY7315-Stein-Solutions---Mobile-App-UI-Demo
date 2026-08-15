package com.saharvest.cbocollector.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saharvest.cbocollector.R
import com.saharvest.cbocollector.data.Screen
import com.saharvest.cbocollector.state.AppState
import com.saharvest.cbocollector.ui.theme.Figtree
import com.saharvest.cbocollector.ui.theme.Poppins
import com.saharvest.cbocollector.ui.theme.SaColors

@Composable
fun RoleSelectionScreen(state: AppState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SaColors.Surface)
            .padding(horizontal = 26.dp, vertical = 44.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.saharvest_logo),
            contentDescription = "SA Harvest",
            modifier = Modifier
                .size(width = 150.dp, height = 71.dp)
                .align(Alignment.Start),
        )
        
        Spacer(modifier = Modifier.height(40.dp))
        
        Text(
            "Select a role",
            fontFamily = Poppins,
            fontWeight = FontWeight.SemiBold,
            fontSize = 32.sp,
            color = SaColors.Ink,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        
        Spacer(modifier = Modifier.height(30.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            RoleRectangle(
                modifier = Modifier.fillMaxWidth(),
                title = "CBO Collection",
                onClick = { state.go(Screen.Login) }
            )
            RoleRectangle(
                modifier = Modifier.fillMaxWidth(),
                title = "Vetting",
                onClick = { state.go(Screen.VoLogin) }
            )
            RoleRectangle(
                modifier = Modifier.fillMaxWidth(),
                title = "Admin",
                onClick = { state.go(Screen.AdminLogin) }
            )
        }
    }
}

@Composable
fun RoleRectangle(modifier: Modifier = Modifier, title: String, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .height(80.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(SaColors.SurfaceAlt.copy(alpha = 0.5f))
            .clickable { onClick() }
            .padding(16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = title,
            fontFamily = Figtree,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            color = SaColors.Ink,
            textAlign = TextAlign.Start
        )
    }
}
