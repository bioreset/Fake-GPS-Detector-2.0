package com.dariusz.fakegpsdetector.model

import com.dariusz.fakegpsdetector.ui.components.theme.ThemeColor
import com.dariusz.fakegpsdetector.ui.components.theme.ThemeShapeCornerFamily
import com.dariusz.fakegpsdetector.ui.components.theme.ThemeTypography

data class CurrentTheme(
    val primaryColor: ThemeColor = ThemeColor.Blue,
    val secondaryColor: ThemeColor = ThemeColor.Yellow,
    val shapeCornerFamily: ThemeShapeCornerFamily = ThemeShapeCornerFamily.Rounded,
    val smallShapeCornerSize: Int = 4,
    val mediumShapeCornerSize: Int = 4,
    val largeShapeCornerSize: Int = 0,
    val mainTypography: ThemeTypography = ThemeTypography.Main
)
