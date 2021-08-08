package com.dariusz.fakegpsdetector.presentation.components.theme

import androidx.compose.ui.graphics.Color

fun getColors(darkTheme: Boolean, color: String): Color = when (color) {
    "blue" -> if (!darkTheme) Color(0xFF2196F3) else Color(0xFF90CAF9)
    "green" -> if (!darkTheme) Color(0xFF43A047) else Color(0xFFA5D6A7)
    "purple" -> if (!darkTheme) Color(0xFF6200EE) else Color(0xFFBB86FC)
    "red" -> if (!darkTheme) Color(0xFFB00020) else Color(0xFFCF6679)
    "yellow" -> if (!darkTheme) Color(0xFFFFEB3B) else Color(0xFFFFF59D)
    else -> Color.Black
}