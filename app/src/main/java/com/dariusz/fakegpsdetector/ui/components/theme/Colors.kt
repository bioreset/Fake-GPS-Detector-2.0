package com.dariusz.fakegpsdetector.ui.components.theme

import androidx.compose.ui.graphics.Color

enum class ThemeColor {
    Blue,
    Green,
    Purple,
    Red,
    Yellow
}

fun ThemeColor.getColors(darkTheme: Boolean): Color = when (this) {
    ThemeColor.Blue -> if (!darkTheme) Color(0xFF2196F3) else Color(0xFF90CAF9)
    ThemeColor.Green -> if (!darkTheme) Color(0xFF43A047) else Color(0xFFA5D6A7)
    ThemeColor.Purple -> if (!darkTheme) Color(0xFF6200EE) else Color(0xFFBB86FC)
    ThemeColor.Red -> if (!darkTheme) Color(0xFFB00020) else Color(0xFFCF6679)
    ThemeColor.Yellow -> if (!darkTheme) Color(0xFFFFEB3B) else Color(0xFFFFF59D)
}

val primaryColor: ThemeColor = ThemeColor.Purple
val secondaryColor: ThemeColor = ThemeColor.Blue
