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
import com.saharvest.cbocollector.ui.components.RememberCheckbox
import com.saharvest.cbocollector.ui.components.SaInputField
import com.saharvest.cbocollector.ui.theme.Figtree
import com.saharvest.cbocollector.ui.theme.Poppins
import com.saharvest.cbocollector.ui.theme.SaColors

private data class OfficerRoleOption(val name: String, val desc: String, val initial: String)

private val OFFICER_ROLE_OPTIONS = listOf(
    OfficerRoleOption("Thandi Mokoena", "Field vetting officer · Gauteng North", "TM"),
    OfficerRoleOption("Use a different device profile", "Sign in with your SA Harvest email", "@"),
)

@Composable
fun VoLoginScreen(state: AppState) {
    fun enterAsOfficer() {
        state.role = "Vetting officer"
        state.go(Screen.VoHome)
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
            "Vetting",
            fontFamily = Poppins,
            fontWeight = FontWeight.SemiBold,
            fontSize = 32.sp,
            color = SaColors.Ink,
            modifier = Modifier.padding(top = 26.dp, bottom = 10.dp),
        )
        Text(
            "Sign in to start or resume a site vetting. Drafts and photos stay on the phone until there is signal.",
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
                onClick = { enterAsOfficer() },
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
            OFFICER_ROLE_OPTIONS.forEach { role ->
                CardButton(
                    onClick = { enterAsOfficer() },
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
                                .background(SaColors.SurfaceAlt),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(role.initial, fontFamily = Poppins, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = SaColors.Ink)
                        }
                        Column(modifier = Modifier.padding(start = 14.dp)) {
                            Text(role.name, fontFamily = Figtree, fontWeight = FontWeight.SemiBold, fontSize = 15.5.sp, color = SaColors.Ink)
                            Text(role.desc, fontFamily = Figtree, fontSize = 12.5.sp, color = SaColors.MutedLight)
                        }
                    }
                }
            }
        }
    }
}
