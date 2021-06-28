package com.dariusz.fakegpsdetector.presentation.components.theme

import androidx.compose.runtime.saveable.Saver
import com.dariusz.fakegpsdetector.domain.model.CurrentTheme

val ThemeSaver = Saver<CurrentTheme, Map<String, Int>>(
    save = { theme ->
        mapOf(
            PrimaryColorKey to theme.primaryColor.ordinal,
            SecondaryColorKey to theme.secondaryColor.ordinal,
            ShapeCornerFamilyKey to theme.shapeCornerFamily.ordinal,
            SmallShapeCornerSizeKey to theme.smallShapeCornerSize,
            MediumShapeCornerSizeKey to theme.mediumShapeCornerSize,
            LargeShapeCornerSizeKey to theme.largeShapeCornerSize
        )
    },
    restore = { map ->
        CurrentTheme(
            primaryColor = ThemeColor.values()[map[PrimaryColorKey]!!],
            secondaryColor = ThemeColor.values()[map[SecondaryColorKey]!!],
            shapeCornerFamily = ThemeShapeCornerFamily.values()[map[ShapeCornerFamilyKey]!!],
            smallShapeCornerSize = map[SmallShapeCornerSizeKey]!!,
            mediumShapeCornerSize = map[MediumShapeCornerSizeKey]!!,
            largeShapeCornerSize = map[LargeShapeCornerSizeKey]!!
        )
    }
)

private const val PrimaryColorKey = "primaryColor"
private const val SecondaryColorKey = "secondaryColor"
private const val ShapeCornerFamilyKey = "shapeCornerFamily"
private const val SmallShapeCornerSizeKey = "smallShapeCornerSize"
private const val MediumShapeCornerSizeKey = "mediumShapeCornerSize"
private const val LargeShapeCornerSizeKey = "largeShapeCornerSize"
