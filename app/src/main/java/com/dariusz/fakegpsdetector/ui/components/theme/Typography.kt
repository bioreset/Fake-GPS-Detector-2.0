package com.dariusz.fakegpsdetector.ui.components.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

enum class ThemeTypography(label: String) {
    Main("Main"),
    Sub("Sub")
}

fun ThemeTypography.getTypography(): Typography = when (this) {
    ThemeTypography.Main -> Typography(
        body1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    )
    ThemeTypography.Sub -> Typography(
        body1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Light,
            fontSize = 14.sp
        )
    )
}
