package com.saharvest.cbocollector.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saharvest.cbocollector.R
import com.saharvest.cbocollector.ui.components.FilledPillButton
import com.saharvest.cbocollector.ui.theme.Roboto
import com.saharvest.cbocollector.ui.theme.SaColors

@Composable
fun SplashScreen(onContinue: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SaColors.SplashBg)
            .padding(18.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(30.dp))
                .background(SaColors.SplashCard),
        ) {
            Box(
                Modifier
                    .align(Alignment.TopStart)
                    .offset(x = (-46).dp, y = (-40).dp)
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(SaColors.SplashBlobA),
            )
            Box(
                Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = 58.dp, y = (-120).dp)
                    .size(190.dp)
                    .clip(CircleShape)
                    .background(SaColors.SplashBlobB),
            )
            Box(
                Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = (-30).dp, y = 34.dp)
                    .size(width = 170.dp, height = 120.dp)
                    .clip(CircleShape)
                    .background(SaColors.SplashBlobA),
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 26.dp, vertical = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp, bottom = 10.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        painter = painterResource(R.drawable.saharvest_truck),
                        contentDescription = "SA Harvest collection truck",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.widthIn(max = 330.dp).fillMaxWidth(),
                    )
                }

                Column(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "Beneficiary Direct Collection & Vetting Application",
                        fontFamily = Roboto,
                        fontWeight = FontWeight.Light,
                        fontSize = 40.sp,
                        lineHeight = 42.sp,
                        letterSpacing = (-1.12).sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                    )
                    Text(
                        text = "Capture vettings and collections on site. Works without signal.",
                        fontFamily = Roboto,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        color = SaColors.Cream.copy(alpha = 0.6f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 16.dp).widthIn(max = 285.dp),
                    )
                }

                FilledPillButton(
                    onClick = onContinue,
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(17.dp),
                ) {
                    Text(
                        "Continue",
                        fontFamily = Roboto,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = SaColors.Ink,
                    )
                }
                Text(
                    text = "Field operations",
                    fontFamily = Roboto,
                    fontSize = 11.5.sp,
                    color = SaColors.Cream.copy(alpha = 0.34f),
                    modifier = Modifier.padding(top = 14.dp),
                )
            }
        }
    }
}
