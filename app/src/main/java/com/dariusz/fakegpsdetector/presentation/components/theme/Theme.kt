package com.dariusz.fakegpsdetector.presentation.components.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.dariusz.fakegpsdetector.domain.model.CurrentTheme
import com.dariusz.fakegpsdetector.utils.ColorUtils.onColor
import com.dariusz.fakegpsdetector.utils.ColorUtils.variantColor

@Composable
fun MainTheme(
    theme: CurrentTheme,
    content: @Composable () -> Unit
) {
    val darkTheme = isSystemInDarkTheme()
    val appThemeSetter = theme.copy(
        primaryColor = getColors(darkTheme, "blue"),
        secondaryColor = getColors(darkTheme, "purple"),
        shapesFamily = ThemeShapes,
        mainTypography = ThemeTypography
    )
    val themePrimaryColor = appThemeSetter.primaryColor
    val themeSecondaryColor = appThemeSetter.secondaryColor
    val mainShapes = appThemeSetter.shapesFamily
    val mainTypography = appThemeSetter.mainTypography
    if (themePrimaryColor != null && themeSecondaryColor != null && mainTypography != null && mainShapes != null) {
        val primaryColor = animateColorAsState(themePrimaryColor)
        val primaryVariantColor = animateColorAsState(themePrimaryColor.variantColor())
        val onPrimaryColor = animateColorAsState(themePrimaryColor.onColor())
        val secondaryColor = animateColorAsState(themeSecondaryColor)
        val secondaryVariantColor = animateColorAsState(themeSecondaryColor.variantColor())
        val onSecondaryColor = animateColorAsState(themeSecondaryColor.onColor())
        val colors = if (!darkTheme) {
            lightColors(
                primary = primaryColor.value,
                primaryVariant = primaryVariantColor.value,
                onPrimary = onPrimaryColor.value,
                secondary = secondaryColor.value,
                secondaryVariant = secondaryVariantColor.value,
                onSecondary = onSecondaryColor.value
            )
        } else {
            darkColors(
                primary = primaryColor.value,
                primaryVariant = primaryVariantColor.value,
                onPrimary = onPrimaryColor.value,
                secondary = secondaryColor.value,
                onSecondary = onSecondaryColor.value
            )
        }
        MaterialTheme(
            colors = colors,
            typography = mainTypography,
            shapes = mainShapes,
            content = content
        )

    }
}
