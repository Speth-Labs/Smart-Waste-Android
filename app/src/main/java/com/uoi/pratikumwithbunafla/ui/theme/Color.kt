package com.uoi.pratikumwithbunafla.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val WasteGreen = Color(0xFF168253)
val WasteGreenDark = Color(0xFF14583D)
val WasteTeal = Color(0xFF17886D)
val WasteOrange = Color(0xFFD88C35)
val WasteRed = Color(0xFFCA596D)
val WastePurple = Color(0xFF788B55)
val WasteNavy = Color(0xFF304A3B)
val WasteLime = Color(0xFFE5EFCB)
val WasteMint = Color(0xFFCDE8D4)
val WasteBlue = Color(0xFFD7EAE1)
val WasteAmber = Color(0xFFF1E0BD)
val WasteCoral = Color(0xFFF0DDE4)

data class SmartWastePalette(
    val background: Color,
    val surface: Color,
    val surfaceMuted: Color,
    val border: Color,
    val text: Color,
    val textMuted: Color
)

val LightSmartWastePalette = SmartWastePalette(
    background = Color(0xFFEBF1DF),
    surface = Color(0xFFFAF8EC),
    surfaceMuted = Color(0xFFDDEAD5),
    border = Color(0xFFB2D0B2),
    text = Color(0xFF1D3527),
    textMuted = Color(0xFF566F5E)
)

val DarkSmartWastePalette = SmartWastePalette(
    background = Color(0xFF151E19),
    surface = Color(0xFF213128),
    surfaceMuted = Color(0xFF2B3B30),
    border = Color(0xFF3D5646),
    text = Color(0xFFEAF2EB),
    textMuted = Color(0xFFB3C4B7)
)

val LocalSmartWastePalette = staticCompositionLocalOf { LightSmartWastePalette }

val AppBackground: Color @Composable get() = LocalSmartWastePalette.current.background
val AppSurface: Color @Composable get() = LocalSmartWastePalette.current.surface
val AppSurfaceMuted: Color @Composable get() = LocalSmartWastePalette.current.surfaceMuted
val AppBorder: Color @Composable get() = LocalSmartWastePalette.current.border
val AppText: Color @Composable get() = LocalSmartWastePalette.current.text
val AppTextMuted: Color @Composable get() = LocalSmartWastePalette.current.textMuted
