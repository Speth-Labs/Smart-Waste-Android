package com.uoi.pratikumwithbunafla.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.uoi.pratikumwithbunafla.R

val InterFont = FontFamily(
    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_semibold, FontWeight.SemiBold),
    Font(R.font.inter_bold, FontWeight.Bold)
)

private fun interStyle(
    size: Int,
    lineHeight: Int,
    weight: FontWeight = FontWeight.Normal
) = TextStyle(
    fontFamily = InterFont,
    fontWeight = weight,
    fontSize = size.sp,
    lineHeight = lineHeight.sp,
    letterSpacing = 0.sp
)

val Typography = Typography(
    displayLarge = interStyle(57, 64, FontWeight.Bold),
    displayMedium = interStyle(45, 52, FontWeight.Bold),
    displaySmall = interStyle(36, 44, FontWeight.Bold),
    headlineLarge = interStyle(32, 40, FontWeight.SemiBold),
    headlineMedium = interStyle(28, 36, FontWeight.SemiBold),
    headlineSmall = interStyle(24, 32, FontWeight.SemiBold),
    titleLarge = interStyle(22, 28, FontWeight.SemiBold),
    titleMedium = interStyle(16, 24, FontWeight.Medium),
    titleSmall = interStyle(14, 20, FontWeight.Medium),
    bodyLarge = interStyle(16, 24),
    bodyMedium = interStyle(14, 20),
    bodySmall = interStyle(12, 18),
    labelLarge = interStyle(14, 20, FontWeight.Medium),
    labelMedium = interStyle(12, 16, FontWeight.Medium),
    labelSmall = interStyle(11, 16, FontWeight.Medium)
)
