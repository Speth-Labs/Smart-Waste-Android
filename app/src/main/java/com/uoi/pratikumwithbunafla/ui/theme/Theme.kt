package com.uoi.pratikumwithbunafla.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class SmartWasteAppearance(
    val darkMode: Boolean,
    val onToggleTheme: () -> Unit
)

val LocalSmartWasteAppearance = staticCompositionLocalOf {
    SmartWasteAppearance(darkMode = false, onToggleTheme = {})
}

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF79CA8F),
    onPrimary = Color.White,
    secondary = Color(0xFF6BC7A2),
    onSecondary = Color(0xFF17392E),
    tertiary = Color(0xFFB1D7AC),
    background = DarkSmartWastePalette.background,
    onBackground = DarkSmartWastePalette.text,
    surface = DarkSmartWastePalette.surface,
    onSurface = DarkSmartWastePalette.text,
    surfaceVariant = DarkSmartWastePalette.surfaceMuted,
    onSurfaceVariant = DarkSmartWastePalette.textMuted,
    outline = DarkSmartWastePalette.border,
    error = Color(0xFFFF9AA8)
)

private val LightColorScheme = lightColorScheme(
    primary = WasteGreen,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFDCEEDF),
    onPrimaryContainer = WasteGreenDark,
    secondary = WasteTeal,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFDDF0E7),
    onSecondaryContainer = Color(0xFF18533F),
    tertiary = WasteOrange,
    onTertiary = Color(0xFF4D2700),
    background = LightSmartWastePalette.background,
    onBackground = LightSmartWastePalette.text,
    surface = LightSmartWastePalette.surface,
    onSurface = LightSmartWastePalette.text,
    surfaceVariant = LightSmartWastePalette.surfaceMuted,
    onSurfaceVariant = LightSmartWastePalette.textMuted,
    outline = LightSmartWastePalette.border,
    error = WasteRed,
    onError = Color.White
)

@Composable
fun SmartWasteTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    onToggleTheme: () -> Unit = {},
    content: @Composable () -> Unit
) {
    val palette = if (darkTheme) DarkSmartWastePalette else LightSmartWastePalette
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    CompositionLocalProvider(
        LocalSmartWastePalette provides palette,
        LocalSmartWasteAppearance provides SmartWasteAppearance(darkTheme, onToggleTheme)
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
