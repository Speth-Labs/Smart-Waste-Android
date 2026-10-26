package com.uoi.pratikumwithbunafla.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.fadeIn
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.sin
import com.uoi.pratikumwithbunafla.ui.theme.AppBackground
import com.uoi.pratikumwithbunafla.ui.theme.AppBorder
import com.uoi.pratikumwithbunafla.ui.theme.AppSurface
import com.uoi.pratikumwithbunafla.ui.theme.AppText
import com.uoi.pratikumwithbunafla.ui.theme.AppTextMuted
import com.uoi.pratikumwithbunafla.ui.theme.WasteGreen
import com.uoi.pratikumwithbunafla.ui.theme.WasteGreenDark
import com.uoi.pratikumwithbunafla.ui.theme.WasteMint
import com.uoi.pratikumwithbunafla.ui.theme.WasteBlue
import com.uoi.pratikumwithbunafla.ui.theme.WasteAmber
import com.uoi.pratikumwithbunafla.ui.theme.WasteCoral
import com.uoi.pratikumwithbunafla.ui.theme.WastePurple
import com.uoi.pratikumwithbunafla.ui.theme.WasteTeal
import com.uoi.pratikumwithbunafla.ui.theme.InterFont

private val SplashForest = Color(0xFF1B714C)
private val SplashLeaf = Color(0xFF4A9B68)
private val SplashText = Color(0xFF25392D)
private val SplashMuted = Color(0xFF596C5D)

@Composable
fun AppScreen(content: @Composable () -> Unit) {
    val ambientMotion = rememberInfiniteTransition(label = "ambient-motion")
    val topOrbOffset by ambientMotion.animateFloat(
        initialValue = -78f,
        targetValue = -70f,
        animationSpec = infiniteRepeatable(
            animation = tween(7200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "top-orb-offset"
    )
    val bottomOrbOffset by ambientMotion.animateFloat(
        initialValue = 88f,
        targetValue = 80f,
        animationSpec = infiniteRepeatable(
            animation = tween(8200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bottom-orb-offset"
    )
    Box(modifier = Modifier.fillMaxSize().background(AppBackground)) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            WasteMint.copy(alpha = 0.30f),
                            WasteBlue.copy(alpha = 0.14f),
                            AppBackground,
                            WasteCoral.copy(alpha = 0.16f)
                        )
                    )
                )
        )
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset {
                    IntOffset(
                        x = 72.dp.roundToPx(),
                        y = topOrbOffset.dp.roundToPx()
                    )
                }
                .size(210.dp)
                .clip(CircleShape)
                .background(WasteBlue.copy(alpha = 0.24f))
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset {
                    IntOffset(
                        x = (-82).dp.roundToPx(),
                        y = bottomOrbOffset.dp.roundToPx()
                    )
                }
                .size(190.dp)
                .clip(CircleShape)
                .background(WasteAmber.copy(alpha = 0.20f))
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 16.dp, y = 20.dp)
                .size(92.dp)
                .clip(CircleShape)
                .background(WastePurple.copy(alpha = 0.04f))
        )
        AnimatedVisibility(
            visible = true,
            modifier = Modifier.fillMaxSize(),
            enter = fadeIn(tween(250)) + slideInVertically(
                initialOffsetY = { it / 24 },
                animationSpec = tween(250)
            )
        ) {
            content()
        }
    }
}

@Composable
fun SmartWasteSplash() {
    val compactScreen = LocalConfiguration.current.screenWidthDp < 360
    val splashMotion = rememberInfiniteTransition(label = "splash-motion")
    val leafDrift by splashMotion.animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(4200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "splash-leaf-drift"
    )
    val title = "Smart Waste"
    val writeProgress = remember { Animatable(0f) }

    LaunchedEffect(title) {
        writeProgress.snapTo(0f)
        writeProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1500, easing = LinearEasing)
        )
    }

    AppScreen {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFFD9B19C),
                            Color(0xFFE5C2A2),
                            Color(0xFFE9D2B0),
                            Color(0xFFD8C8AA),
                            Color(0xFFBEC7AB)
                        )
                    )
                )
        ) {
            SplashEcoBackdrop(
                drift = leafDrift,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    modifier = Modifier.drawWithContent {
                        clipRect(right = size.width * writeProgress.value) {
                            this@drawWithContent.drawContent()
                        }
                    },
                    color = SplashText,
                    fontSize = if (compactScreen) 30.sp else 34.sp,
                    fontFamily = InterFont,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-0.35).sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Kelola sampah lebih mudah, mulai dari lingkungan sekitar.",
                    color = SplashMuted,
                    fontSize = 15.sp,
                    fontFamily = InterFont,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 21.sp,
                    letterSpacing = 0.05.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        color = SplashForest,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(9.dp))
                    Text(
                        text = "Menyiapkan ruang kerja...",
                        modifier = Modifier.weight(1f),
                        color = SplashMuted,
                        fontSize = 12.sp,
                        fontFamily = InterFont,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 0.05.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
private fun SplashEcoBackdrop(drift: Float, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val treeTone = Color(0xFF456A53).copy(alpha = 0.30f)
        val hillTone = Color(0xFF66836A).copy(alpha = 0.30f)

        drawPath(
            path = Path().apply {
                moveTo(0f, h * 0.69f)
                cubicTo(w * 0.22f, h * 0.56f, w * 0.34f, h * 0.72f, w * 0.52f, h * 0.64f)
                cubicTo(w * 0.71f, h * 0.55f, w * 0.85f, h * 0.72f, w, h * 0.61f)
                lineTo(w, h)
                lineTo(0f, h)
                close()
            },
            color = Color(0xFF91AE91).copy(alpha = 0.32f)
        )
        drawPath(
            path = Path().apply {
                moveTo(0f, h * 0.81f)
                cubicTo(w * 0.2f, h * 0.7f, w * 0.43f, h * 0.88f, w * 0.62f, h * 0.76f)
                cubicTo(w * 0.79f, h * 0.67f, w * 0.9f, h * 0.83f, w, h * 0.74f)
                lineTo(w, h)
                lineTo(0f, h)
                close()
            },
            color = hillTone
        )

        drawSplashTree(w * 0.04f, h * 0.98f, w * 0.32f, treeTone)
        drawSplashTree(w * 0.98f, h * 0.98f, w * 0.40f, treeTone.copy(alpha = 0.12f))
        drawSplashTree(w * 0.22f, h * 0.97f, w * 0.19f, treeTone.copy(alpha = 0.11f))
        drawSplashHouse(w * 0.62f, h * 0.91f, w * 0.14f, h * 0.16f, treeTone)

        val sway = sin(drift * PI).toFloat()
        drawSplashLeaf(Offset(w * 0.13f + sway * 13.dp.toPx(), h * 0.22f), 10.dp.toPx(), SplashLeaf.copy(alpha = 0.30f), -28f)
        drawSplashLeaf(Offset(w * 0.82f - sway * 10.dp.toPx(), h * 0.34f), 8.dp.toPx(), SplashForest.copy(alpha = 0.24f), 24f)
        drawSplashLeaf(Offset(w * 0.91f + sway * 8.dp.toPx(), h * 0.53f), 6.dp.toPx(), Color(0xFF5D8567).copy(alpha = 0.30f), 42f)
    }
}

private fun DrawScope.drawSplashTree(x: Float, groundY: Float, treeSize: Float, color: Color) {
    val trunkWidth = treeSize * 0.10f
    drawRoundRect(
        color = color.copy(alpha = color.alpha * 0.8f),
        topLeft = Offset(x - trunkWidth / 2f, groundY - treeSize * 0.65f),
        size = Size(trunkWidth, treeSize * 0.67f),
        cornerRadius = CornerRadius(trunkWidth / 2f)
    )
    drawCircle(color, treeSize * 0.30f, Offset(x, groundY - treeSize * 0.79f))
    drawCircle(color.copy(alpha = color.alpha * 0.85f), treeSize * 0.24f, Offset(x - treeSize * 0.2f, groundY - treeSize * 0.63f))
    drawCircle(color.copy(alpha = color.alpha * 0.85f), treeSize * 0.24f, Offset(x + treeSize * 0.2f, groundY - treeSize * 0.63f))
}

private fun DrawScope.drawSplashHouse(x: Float, groundY: Float, houseWidth: Float, houseHeight: Float, color: Color) {
    val top = groundY - houseHeight * 0.82f
    drawRoundRect(
        color = color,
        topLeft = Offset(x, top + houseHeight * 0.26f),
        size = Size(houseWidth, houseHeight * 0.56f),
        cornerRadius = CornerRadius(5.dp.toPx())
    )
    drawPath(
        path = Path().apply {
            moveTo(x - houseWidth * 0.08f, top + houseHeight * 0.28f)
            lineTo(x + houseWidth * 0.5f, top)
            lineTo(x + houseWidth * 1.08f, top + houseHeight * 0.28f)
            close()
        },
        color = color.copy(alpha = color.alpha * 1.12f)
    )
    drawRoundRect(
        color = Color(0xFFFFE8C2).copy(alpha = 0.88f),
        topLeft = Offset(x + houseWidth * 0.14f, top + houseHeight * 0.39f),
        size = Size(houseWidth * 0.2f, houseHeight * 0.15f),
        cornerRadius = CornerRadius(2.dp.toPx())
    )
    drawRoundRect(
        color = Color(0xFFFFE8C2).copy(alpha = 0.88f),
        topLeft = Offset(x + houseWidth * 0.66f, top + houseHeight * 0.39f),
        size = Size(houseWidth * 0.2f, houseHeight * 0.15f),
        cornerRadius = CornerRadius(2.dp.toPx())
    )
}

private fun DrawScope.drawSplashLeaf(center: Offset, radius: Float, color: Color, angle: Float) {
    rotate(angle, center) {
        val leaf = Path().apply {
            moveTo(center.x, center.y - radius)
            quadraticTo(center.x + radius, center.y, center.x, center.y + radius)
            quadraticTo(center.x - radius, center.y, center.x, center.y - radius)
            close()
        }
        drawPath(leaf, color)
        drawLine(
            color = color.copy(alpha = color.alpha * 0.68f),
            start = Offset(center.x, center.y - radius * 0.62f),
            end = Offset(center.x, center.y + radius * 0.62f),
            strokeWidth = 1.dp.toPx()
        )
    }
}

@Composable
fun AppHeader(compact: Boolean = false) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(if (compact) 34.dp else 38.dp)
                .clip(RoundedCornerShape(13.dp))
                .background(WasteGreenDark),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Icon(
                imageVector = Icons.Filled.Eco,
                contentDescription = "Smart Waste",
                tint = Color.White,
                modifier = Modifier.size(if (compact) 19.dp else 21.dp)
            )
        }
        Spacer(modifier = Modifier.width(if (compact) 8.dp else 10.dp))
        Column {
            Text(
                text = "Smart Waste",
                color = AppText,
                fontSize = if (compact) 15.sp else 17.sp,
                fontWeight = FontWeight.Bold
            )
            if (!compact) Text(
                text = "Panel operasional",
                color = AppTextMuted,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    containerColor: Color = AppSurface,
    borderColor: Color = AppBorder,
    accentColor: Color? = null,
    elevation: Dp = 0.dp,
    content: @Composable () -> Unit
) {
    val glassSurface = if (containerColor == Color.White || containerColor == AppSurface) {
        AppSurface.copy(alpha = 0.98f)
    } else {
        containerColor
    }
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = glassSurface),
        border = null,
        elevation = CardDefaults.cardElevation(defaultElevation = elevation)
    ) {
        content()
    }
}

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    containerColor: Color = WasteGreen
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(if (enabled) containerColor else AppBorder)
            .border(
                width = 1.dp,
                color = if (enabled) containerColor.copy(alpha = 0.30f) else AppBorder,
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.White,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = AppTextMuted
        )
    ) {
        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun UserAvatar(
    name: String,
    modifier: Modifier = Modifier
) {
    val initial = name.trim().firstOrNull()?.uppercase() ?: "A"
    Box(
        modifier = modifier
            .size(42.dp)
            .clip(CircleShape)
            .background(WasteGreenDark),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initial,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SummaryCard(
    value: String,
    label: String,
    accent: Color,
    modifier: Modifier = Modifier,
    containerColor: Color = Color.White,
    icon: ImageVector,
    onClick: (() -> Unit)? = null
) {
    AppCard(
        modifier = modifier.then(onClick?.let { Modifier.clickable(onClick = it) } ?: Modifier),
        containerColor = containerColor,
        borderColor = accent.copy(alpha = 0.28f),
        accentColor = accent
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 15.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = value,
                    color = accent,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(accent.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = accent,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
            Text(
                text = label,
                color = AppTextMuted,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
