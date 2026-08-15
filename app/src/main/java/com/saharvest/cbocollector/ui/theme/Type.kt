package com.saharvest.cbocollector.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import com.saharvest.cbocollector.R

/** Headline face — used for h1/h2/h3 titles and big numbers. */
val Poppins = FontFamily(
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
    Font(R.font.poppins_bold, FontWeight.Bold),
)

/** Body / UI face — used for almost everything else. Variable font (wght). */
val Figtree = FontFamily(
    Font(
        R.font.figtree, FontWeight.Normal,
        variationSettings = FontVariation.Settings(FontVariation.weight(400)),
    ),
    Font(
        R.font.figtree, FontWeight.Medium,
        variationSettings = FontVariation.Settings(FontVariation.weight(500)),
    ),
    Font(
        R.font.figtree, FontWeight.SemiBold,
        variationSettings = FontVariation.Settings(FontVariation.weight(600)),
    ),
    Font(
        R.font.figtree, FontWeight.Bold,
        variationSettings = FontVariation.Settings(FontVariation.weight(700)),
    ),
)

/** Splash / auth screen face. Variable font (wdth, wght). */
val Roboto = FontFamily(
    Font(
        R.font.roboto, FontWeight.Light,
        variationSettings = FontVariation.Settings(FontVariation.weight(300), FontVariation.width(100f)),
    ),
    Font(
        R.font.roboto, FontWeight.Normal,
        variationSettings = FontVariation.Settings(FontVariation.weight(400), FontVariation.width(100f)),
    ),
    Font(
        R.font.roboto, FontWeight.Medium,
        variationSettings = FontVariation.Settings(FontVariation.weight(500), FontVariation.width(100f)),
    ),
    Font(
        R.font.roboto, FontWeight.Bold,
        variationSettings = FontVariation.Settings(FontVariation.weight(700), FontVariation.width(100f)),
    ),
)
