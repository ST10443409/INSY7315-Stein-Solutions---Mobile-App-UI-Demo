package com.saharvest.cbocollector.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saharvest.cbocollector.R
import com.saharvest.cbocollector.data.Screen
import com.saharvest.cbocollector.state.AppState
import com.saharvest.cbocollector.ui.components.CardButton
import com.saharvest.cbocollector.ui.components.EyebrowLabel
import com.saharvest.cbocollector.ui.components.FilledPillButton
import com.saharvest.cbocollector.ui.components.OrDivider
import com.saharvest.cbocollector.ui.components.OutlinePillButton
import com.saharvest.cbocollector.ui.components.RememberCheckbox
import com.saharvest.cbocollector.ui.components.SaInputField
import com.saharvest.cbocollector.ui.theme.Figtree
import com.saharvest.cbocollector.ui.theme.Poppins
import com.saharvest.cbocollector.ui.theme.SaColors

@Composable
fun AdminLoginScreen(state: AppState) {
    fun enterAsAdmin() {
        state.role = "Admin"
        state.go(Screen.AdminHome)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SaColors.Surface)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 26.dp, vertical = 44.dp),
    ) {
        Image(
            painter = painterResource(R.drawable.saharvest_logo),
            contentDescription = "SA Harvest",
            modifier = Modifier.size(width = 150.dp, height = 71.dp),
        )
        Text(
            "Admin console",
            fontFamily = Poppins,
            fontWeight = FontWeight.SemiBold,
            fontSize = 32.sp,
            color = SaColors.Ink,
            modifier = Modifier.padding(top = 26.dp, bottom = 10.dp),
        )
        Text(
            "Approvals, the CBO register, and where the food went. Read-only until you choose to act.",
            fontFamily = Figtree,
            fontSize = 15.sp,
            lineHeight = 23.sp,
            color = SaColors.Muted,
        )

        Column(
            modifier = Modifier.padding(top = 34.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("Email", fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 12.5.sp, color = SaColors.InkSoft)
                SaInputField(
                    value = state.email,
                    onValueChange = { state.email = it },
                    placeholder = "you@saharvest.org.za",
                    keyboardType = KeyboardType.Email,
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("Password", fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 12.5.sp, color = SaColors.InkSoft)
                SaInputField(
                    value = state.password,
                    onValueChange = { state.password = it },
                    placeholder = "••••••••",
                    visualTransformation = if (state.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailing = {
                        Text(
                            if (state.passwordVisible) "Hide" else "Show",
                            fontFamily = Figtree,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp,
                            color = SaColors.Muted,
                            modifier = Modifier
                                .clickable { state.passwordVisible = !state.passwordVisible }
                                .padding(horizontal = 10.dp, vertical = 8.dp),
                        )
                    },
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RememberCheckbox(checked = state.rememberMe, onToggle = { state.rememberMe = !state.rememberMe })
                Text(
                    "Forgot password?",
                    fontFamily = Figtree,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.5.sp,
                    color = SaColors.LinkGold,
                )
            }
            FilledPillButton(
                onClick = { enterAsAdmin() },
                modifier = Modifier.fillMaxWidth().padding(top = 6.dp),
                contentPadding = PaddingValues(15.dp),
            ) {
                Text("Sign in", fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = SaColors.Ink)
            }
        }

        OrDivider(modifier = Modifier.padding(vertical = 18.dp))

        EyebrowLabel("Or continue as", modifier = Modifier.padding(bottom = 2.dp))

        Column(
            modifier = Modifier.padding(top = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            CardButton(
                onClick = { enterAsAdmin() },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                borderColor = SaColors.inkAlpha(0.14f),
                contentPadding = PaddingValues(horizontal = 18.dp, vertical = 14.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(SaColors.Divider),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text("NK", fontFamily = Poppins, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = SaColors.Ink)
                    }
                    Column(modifier = Modifier.padding(start = 14.dp)) {
                        Text("Naledi Khumalo", fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 15.5.sp, color = SaColors.Ink)
                        Text("Programmes administrator · national", fontFamily = Figtree, fontSize = 12.5.sp, color = SaColors.MutedLight)
                    }
                }
            }
            OutlinePillButton(
                onClick = { enterAsAdmin() },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(15.dp),
            ) {
                Text("Use a different account", fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = SaColors.Ink)
            }
        }
    }
}
