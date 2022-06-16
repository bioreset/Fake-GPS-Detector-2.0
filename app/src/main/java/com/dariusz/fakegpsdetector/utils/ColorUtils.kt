package com.dariusz.fakegpsdetector.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils

object ColorUtils {

    fun Color.onColor(): Color {
        return if (isLightColor()) Color.Black else Color.White
    }

    private fun Color.isLightColor(): Boolean {
        val contrastForBlack = calculateContrast(foreground = Color.Black)
        val contrastForWhite = calculateContrast(foreground = Color.White)
        return contrastForBlack > contrastForWhite
    }

    private fun Color.calculateContrast(foreground: Color): Double {
        return ColorUtils.calculateContrast(foreground.toArgb(), toArgb())
    }
}
