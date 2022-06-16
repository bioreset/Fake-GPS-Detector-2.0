package com.dariusz.fakegpsdetector.presentation.components.theme

import android.os.Build
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.dariusz.fakegpsdetector.utils.ColorUtils.onColor

@Composable
fun MainTheme(
    content: @Composable () -> Unit
) {
    val darkTheme = isSystemInDarkTheme()
    val dynamic = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val context = LocalContext.current

    val themePrimaryColor = getColors(darkTheme, AppColors.BLUE)
    val themeSecondaryColor = getColors(darkTheme, AppColors.PURPLE)

    val primaryColor = animateColorAsState(themePrimaryColor)
    val onPrimaryColor = animateColorAsState(themePrimaryColor.onColor())
    val secondaryColor = animateColorAsState(themeSecondaryColor)
    val onSecondaryColor = animateColorAsState(themeSecondaryColor.onColor())

    val colors = if (dynamic) {
        if (!darkTheme) {
            dynamicLightColorScheme(context)
        } else {
            dynamicDarkColorScheme(context)
        }
    } else {
        if (!darkTheme) {
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
    }

    MaterialTheme(
        colorScheme = colors,
        typography = MaterialTheme.typography,
        shapes = MaterialTheme.shapes,
        content = content
    )
}
