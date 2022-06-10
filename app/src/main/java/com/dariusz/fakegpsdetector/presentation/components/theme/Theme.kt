package com.dariusz.fakegpsdetector.presentation.components.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.dariusz.fakegpsdetector.utils.ColorUtils.onColor

@Composable
fun MainTheme(
    content: @Composable () -> Unit
) {
    val darkTheme = isSystemInDarkTheme()
    val themePrimaryColor = getColors(darkTheme, AppColors.BLUE)
    val themeSecondaryColor = getColors(darkTheme, AppColors.PURPLE)

    val primaryColor = animateColorAsState(themePrimaryColor)
    val onPrimaryColor = animateColorAsState(themePrimaryColor.onColor())
    val secondaryColor = animateColorAsState(themeSecondaryColor)
    val onSecondaryColor = animateColorAsState(themeSecondaryColor.onColor())

    val colors = if (!darkTheme) {
        lightColorScheme(
            primary = primaryColor.value,
            onPrimary = onPrimaryColor.value,
            secondary = secondaryColor.value,
            onSecondary = onSecondaryColor.value
        )
    } else {
        darkColorScheme(
            primary = primaryColor.value,
            onPrimary = onPrimaryColor.value,
            secondary = secondaryColor.value,
            onSecondary = onSecondaryColor.value
        )
    }

    MaterialTheme(
        colorScheme = colors,
        typography = MaterialTheme.typography,
        shapes = MaterialTheme.shapes,
        content = content
    )
}
