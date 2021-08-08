package com.dariusz.fakegpsdetector.domain.model

import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color

data class CurrentTheme(
    val primaryColor: Color? = null,
    val secondaryColor: Color? = null,
    val shapesFamily: Shapes? = null,
    val mainTypography: Typography? = null
)
