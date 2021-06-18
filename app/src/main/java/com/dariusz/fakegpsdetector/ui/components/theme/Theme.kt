package com.dariusz.fakegpsdetector.ui.components.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.dariusz.fakegpsdetector.model.CurrentTheme
import com.dariusz.fakegpsdetector.utils.ColorUtils.onColor
import com.dariusz.fakegpsdetector.utils.ColorUtils.variantColor

@Composable
fun MainTheme(
    theme: CurrentTheme,
    content: @Composable () -> Unit
) {
    val darkTheme = isSystemInDarkTheme()
    val themePrimaryColor = theme.primaryColor.getColors(darkTheme)
    val themeSecondaryColor = theme.secondaryColor.getColors(darkTheme)
    val primaryColor = animateColorAsState(themePrimaryColor)
    val primaryVariantColor = animateColorAsState(themePrimaryColor.variantColor())
    val onPrimaryColor = animateColorAsState(themePrimaryColor.onColor())
    val secondaryColor = animateColorAsState(themeSecondaryColor)
    val secondaryVariantColor = animateColorAsState(themeSecondaryColor.variantColor())
    val onSecondaryColor = animateColorAsState(themeSecondaryColor.onColor())
    val smallShapeSize = animateDpAsState(theme.smallShapeCornerSize.dp)
    val mediumShapeSize = animateDpAsState(theme.mediumShapeCornerSize.dp)
    val largeShapeSize = animateDpAsState(theme.largeShapeCornerSize.dp)
    val mainTypography = theme.mainTypography.getTypography()
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
        shapes = Shapes(
            small = theme.shapeCornerFamily.getShapes(size = smallShapeSize.value),
            medium = theme.shapeCornerFamily.getShapes(size = mediumShapeSize.value),
            large = theme.shapeCornerFamily.getShapes(size = largeShapeSize.value),
        ),
        content = content
    )
}
