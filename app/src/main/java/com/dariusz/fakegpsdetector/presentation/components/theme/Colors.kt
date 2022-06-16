package com.dariusz.fakegpsdetector.presentation.components.theme

import androidx.compose.ui.graphics.Color

enum class AppColors {
    BLUE,
    GREEN,
    PURPLE,
    RED,
    YELLOW
}

fun getColors(darkTheme: Boolean, color: AppColors): Color = when (color) {
    AppColors.BLUE -> if (!darkTheme) Color(0xFF2196F3) else Color(0xFF90CAF9)
    AppColors.GREEN -> if (!darkTheme) Color(0xFF43A047) else Color(0xFFA5D6A7)
    AppColors.PURPLE -> if (!darkTheme) Color(0xFF6200EE) else Color(0xFFBB86FC)
    AppColors.RED -> if (!darkTheme) Color(0xFFB00020) else Color(0xFFCF6679)
    AppColors.YELLOW -> if (!darkTheme) Color(0xFFFFEB3B) else Color(0xFFFFF59D)
}