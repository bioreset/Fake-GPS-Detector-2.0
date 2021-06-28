package com.dariusz.fakegpsdetector.presentation.components.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.Dp

enum class ThemeShapeCornerFamily(label: String) {
    Rounded("Rounded"),
    Cut("Cut")
}

fun ThemeShapeCornerFamily.getShapes(size: Dp): CornerBasedShape = when (this) {
    ThemeShapeCornerFamily.Rounded -> RoundedCornerShape(size = size)
    ThemeShapeCornerFamily.Cut -> CutCornerShape(size = size)
}
