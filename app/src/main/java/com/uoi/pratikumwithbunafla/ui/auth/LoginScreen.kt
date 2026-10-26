package com.uoi.pratikumwithbunafla.ui.auth

import android.content.Context
import androidx.core.content.edit
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlinx.coroutines.launch
import com.uoi.pratikumwithbunafla.R
import com.uoi.pratikumwithbunafla.data.model.UserAccount
import com.uoi.pratikumwithbunafla.data.model.UserRole
import com.uoi.pratikumwithbunafla.ui.theme.InterFont

private val LoginFont = InterFont

private data class LoginPalette(
    val background: Color,
    val backgroundTop: Color,
    val backgroundMiddle: Color,
    val backgroundBottom: Color,
    val surface: Color,
    val field: Color,
    val text: Color,
    val muted: Color,
    val accent: Color,
    val accentDark: Color,
    val accentSoft: Color,
    val border: Color,
    val error: Color
)

private val LightLoginPalette = LoginPalette(
    background = Color(0xFFDCE7E0),
    backgroundTop = Color(0xFFC5DDE5),
    backgroundMiddle = Color(0xFFE8E3D5),
    backgroundBottom = Color(0xFFC1D3BF),
    surface = Color(0xFFEEF0E5),
    field = Color(0xFFE3E9DD),
    text = Color(0xFF1D3527),
    muted = Color(0xFF53695B),
    accent = Color(0xFF287A56),
    accentDark = Color(0xFF20563D),
    accentSoft = Color(0xFFD5E4D2),
    border = Color(0xFF91AF94),
    error = Color(0xFFC34457)
)

private val DarkLoginPalette = LoginPalette(
    background = Color(0xFF111B15),
    backgroundTop = Color(0xFF1D3023),
    backgroundMiddle = Color(0xFF18271D),
    backgroundBottom = Color(0xFF101912),
    surface = Color(0xFF23352A),
    field = Color(0xFF2B3D31),
    text = Color(0xFFF0F4EF),
    muted = Color(0xFFB8C9BA),
    accent = Color(0xFF79CA8F),
    accentDark = Color(0xFF9ED8AA),
    accentSoft = Color(0xFF304A37),
    border = Color(0xFF55755C),
    error = Color(0xFFFF9AA8)
)

private data class LoginRoleStyle(
    val accent: Color,
    val soft: Color,
    val border: Color,
    val button: Color,
    val icon: ImageVector,
    val hint: String
)

private fun loginRoleStyle(role: UserRole, darkMode: Boolean): LoginRoleStyle = when (role) {
    UserRole.ADMIN -> if (darkMode) {
        LoginRoleStyle(
            accent = Color(0xFF9BD9A7),
            soft = Color(0xFF304A37),
            border = Color(0xFF5C8A65),
            button = Color(0xFF2E8050),
            icon = Icons.Filled.Lock,
            hint = "Kelola sistem"
        )
    } else {
        LoginRoleStyle(
            accent = Color(0xFF276746),
            soft = Color(0xFFD2E2C9),
            border = Color(0xFF7EA58A),
            button = Color(0xFF287A56),
            icon = Icons.Filled.Lock,
            hint = "Kelola sistem"
        )
    }

    UserRole.PETUGAS -> if (darkMode) {
        LoginRoleStyle(
            accent = Color(0xFF85D3B0),
            soft = Color(0xFF21483A),
            border = Color(0xFF478C70),
            button = Color(0xFF267A5D),
            icon = Icons.Filled.Person,
            hint = "Tugas lapangan"
        )
    } else {
        LoginRoleStyle(
            accent = Color(0xFF286B5A),
            soft = Color(0xFFD3E5DB),
            border = Color(0xFF83AE9E),
            button = Color(0xFF347F69),
            icon = Icons.Filled.Person,
            hint = "Tugas lapangan"
        )
    }
}

@Composable
private fun animateLoginPalette(darkMode: Boolean): LoginPalette {
    val progress by animateFloatAsState(
        targetValue = if (darkMode) 1f else 0f,
        animationSpec = tween(durationMillis = 760, easing = FastOutSlowInEasing),
        label = "login-palette-transition"
    )
    fun blend(light: Color, dark: Color) = lerp(light, dark, progress)

    return LoginPalette(
        background = blend(LightLoginPalette.background, DarkLoginPalette.background),
        backgroundTop = blend(LightLoginPalette.backgroundTop, DarkLoginPalette.backgroundTop),
        backgroundMiddle = blend(LightLoginPalette.backgroundMiddle, DarkLoginPalette.backgroundMiddle),
        backgroundBottom = blend(LightLoginPalette.backgroundBottom, DarkLoginPalette.backgroundBottom),
        surface = blend(LightLoginPalette.surface, DarkLoginPalette.surface),
        field = blend(LightLoginPalette.field, DarkLoginPalette.field),
        text = blend(LightLoginPalette.text, DarkLoginPalette.text),
        muted = blend(LightLoginPalette.muted, DarkLoginPalette.muted),
        accent = blend(LightLoginPalette.accent, DarkLoginPalette.accent),
        accentDark = blend(LightLoginPalette.accentDark, DarkLoginPalette.accentDark),
        accentSoft = blend(LightLoginPalette.accentSoft, DarkLoginPalette.accentSoft),
        border = blend(LightLoginPalette.border, DarkLoginPalette.border),
        error = blend(LightLoginPalette.error, DarkLoginPalette.error)
    )
}

private val RecyclingLogoFilter = ColorFilter.colorMatrix(
    ColorMatrix(
        floatArrayOf(
            0f, 0f, 0f, 0f, 35f,
            0f, 0f, 0f, 0f, 132f,
            0f, 0f, 0f, 0f, 90f,
            -0.299f, -0.587f, -0.114f, 0f, 255f
        )
    )
)

@Composable
private fun SmartWasteBackdrop(
    palette: LoginPalette,
    darkMode: Boolean,
    modifier: Modifier = Modifier
) {
    val transition = rememberInfiniteTransition(label = "eco-backdrop-motion")
    val drift by transition.animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 6200),
            repeatMode = RepeatMode.Reverse
        ),
        label = "floating-leaves"
    )
    val skyMotion by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5200),
            repeatMode = RepeatMode.Reverse
        ),
        label = "sun-moon-drift"
    )

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val farHill = if (darkMode) {
            Color(0xFF273B2F).copy(alpha = 0.88f)
        } else {
            Color(0xFF9DB7A0).copy(alpha = 0.78f)
        }
        val nearHill = if (darkMode) {
            Color(0xFF17251C).copy(alpha = 0.96f)
        } else {
            Color(0xFF5C896B).copy(alpha = 0.43f)
        }
        val treeColor = if (darkMode) {
            Color(0xFF131E17).copy(alpha = 0.90f)
        } else {
            Color(0xFF356B4D).copy(alpha = 0.40f)
        }

        if (darkMode) {
            val skyPhase = skyMotion * 2f * PI.toFloat()
            val floatX = sin(skyPhase.toDouble()).toFloat()
            val floatY = cos(skyPhase.toDouble()).toFloat()
            val glowPulse = 0.5f + 0.5f * sin((skyPhase + 0.6f).toDouble()).toFloat()
            val moonCenter = Offset(
                x = w * 0.82f + floatX * 2.dp.toPx(),
                y = h * 0.18f + floatY * 4.dp.toPx()
            )
            drawGlowingMoon(moonCenter, w * 0.073f, glowPulse)

            val stars = listOf(
                Offset(w * 0.18f, h * 0.18f),
                Offset(w * 0.47f, h * 0.12f),
                Offset(w * 0.68f, h * 0.34f),
                Offset(w * 0.91f, h * 0.43f)
            )
            stars.forEachIndexed { index, star ->
                val twinkle = 0.34f + 0.46f * (
                    0.5f + 0.5f * sin((skyPhase + index * 1.4f).toDouble()).toFloat()
                )
                drawCircle(Color(0xFFE8F1E5).copy(alpha = twinkle), (1.4f + index % 2 * 0.5f).dp.toPx(), star)
            }
        }

        drawPath(
            path = Path().apply {
                moveTo(0f, h * 0.42f)
                cubicTo(w * 0.18f, h * 0.18f, w * 0.34f, h * 0.52f, w * 0.56f, h * 0.32f)
                cubicTo(w * 0.75f, h * 0.14f, w * 0.85f, h * 0.43f, w, h * 0.24f)
                lineTo(w, h)
                lineTo(0f, h)
                close()
            },
            color = farHill
        )
        drawPath(
            path = Path().apply {
                moveTo(0f, h * 0.62f)
                cubicTo(w * 0.2f, h * 0.37f, w * 0.38f, h * 0.74f, w * 0.62f, h * 0.52f)
                cubicTo(w * 0.8f, h * 0.37f, w * 0.9f, h * 0.62f, w, h * 0.46f)
                lineTo(w, h)
                lineTo(0f, h)
                close()
            },
            color = nearHill
        )

        drawSoftTree(w * 0.10f, h * 0.78f, w * 0.18f, treeColor)
        drawSoftTree(w * 0.90f, h * 0.80f, w * 0.20f, treeColor)
        drawSoftTree(w * 0.27f, h * 0.78f, w * 0.11f, treeColor.copy(alpha = treeColor.alpha * 0.72f))
        drawNeighborhoodHouse(w * 0.57f, h * 0.78f, w * 0.12f, h * 0.23f, treeColor)

        val breeze = (drift + 1f) * PI.toFloat()
        val firstLeaf = sin(breeze.toDouble()).toFloat()
        val secondLeaf = sin((breeze + 1.3f).toDouble()).toFloat()
        val thirdLeaf = sin((breeze + 2.5f).toDouble()).toFloat()
        drawFloatingLeaf(
            Offset(w * 0.39f + firstLeaf * 13.dp.toPx(), h * 0.37f + cos(breeze.toDouble()).toFloat() * 4.dp.toPx()),
            8.dp.toPx(), palette.accent.copy(alpha = if (darkMode) 0.72f else 0.48f), -28f + firstLeaf * 12f
        )
        drawFloatingLeaf(
            Offset(w * 0.75f - secondLeaf * 11.dp.toPx(), h * 0.20f + firstLeaf * 5.dp.toPx()),
            6.dp.toPx(), palette.accentDark.copy(alpha = if (darkMode) 0.76f else 0.42f), 22f + secondLeaf * 14f
        )
        drawFloatingLeaf(
            Offset(w * 0.46f - thirdLeaf * 9.dp.toPx(), h * 0.66f + secondLeaf * 3.dp.toPx()),
            5.dp.toPx(), palette.accent.copy(alpha = if (darkMode) 0.64f else 0.36f), 38f + thirdLeaf * 16f
        )
        if (!darkMode) {
            drawFloatingLeaf(
                Offset(w * 0.16f + firstLeaf * 9.dp.toPx(), h * 0.28f + secondLeaf * 4.dp.toPx()),
                14.dp.toPx(), Color(0xFF3E9A5D).copy(alpha = 0.50f), -38f + firstLeaf * 12f
            )
            drawFloatingLeaf(
                Offset(w * 0.86f - secondLeaf * 8.dp.toPx(), h * 0.35f + thirdLeaf * 4.dp.toPx()),
                11.dp.toPx(), Color(0xFF69A56B).copy(alpha = 0.46f), 28f + secondLeaf * 12f
            )
        }
    }
}

private fun DrawScope.drawGlowingMoon(center: Offset, radius: Float, pulse: Float) {
    val moonlight = Color(0xFFDDEAD9)
    val glowRadius = size.width * (0.24f + pulse * 0.025f)
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(moonlight.copy(alpha = 0.25f), moonlight.copy(alpha = 0.08f), Color.Transparent),
            center = center,
            radius = glowRadius
        ),
        radius = glowRadius,
        center = center
    )

    val crescent = Path().apply {
        moveTo(center.x + radius * 0.24f, center.y - radius)
        cubicTo(
            center.x - radius * 0.96f, center.y - radius * 0.78f,
            center.x - radius * 0.96f, center.y + radius * 0.78f,
            center.x + radius * 0.24f, center.y + radius
        )
        cubicTo(
            center.x + radius * 0.52f, center.y + radius * 0.78f,
            center.x + radius * 0.70f, center.y + radius * 0.34f,
            center.x + radius * 0.68f, center.y
        )
        cubicTo(
            center.x + radius * 0.70f, center.y - radius * 0.34f,
            center.x + radius * 0.52f, center.y - radius * 0.78f,
            center.x + radius * 0.24f, center.y - radius
        )
        close()
    }
    drawPath(
        path = crescent,
        brush = Brush.linearGradient(
            colors = listOf(Color(0xFFF2F6EF), Color(0xFFC9DCCB), Color(0xFF96B99B)),
            start = Offset(center.x - radius, center.y - radius),
            end = Offset(center.x + radius, center.y + radius)
        )
    )
    drawCircle(
        color = Color.White.copy(alpha = 0.20f),
        radius = radius * 0.085f,
        center = Offset(center.x - radius * 0.39f, center.y - radius * 0.24f)
    )
}

private fun DrawScope.drawSoftTree(x: Float, groundY: Float, treeSize: Float, color: Color) {
    val trunkWidth = treeSize * 0.11f
    drawRoundRect(
        color = color.copy(alpha = color.alpha * 0.78f),
        topLeft = Offset(x - trunkWidth / 2f, groundY - treeSize * 0.64f),
        size = Size(trunkWidth, treeSize * 0.66f),
        cornerRadius = CornerRadius(trunkWidth / 2f)
    )
    drawCircle(color, treeSize * 0.31f, Offset(x, groundY - treeSize * 0.79f))
    drawCircle(color.copy(alpha = color.alpha * 0.86f), treeSize * 0.25f, Offset(x - treeSize * 0.2f, groundY - treeSize * 0.63f))
    drawCircle(color.copy(alpha = color.alpha * 0.86f), treeSize * 0.25f, Offset(x + treeSize * 0.2f, groundY - treeSize * 0.63f))
}

private fun DrawScope.drawNeighborhoodHouse(x: Float, groundY: Float, houseWidth: Float, houseHeight: Float, color: Color) {
    val houseTop = groundY - houseHeight * 0.7f
    drawRoundRect(
        color = color.copy(alpha = color.alpha * 0.62f),
        topLeft = Offset(x, houseTop + houseHeight * 0.25f),
        size = Size(houseWidth, houseHeight * 0.45f),
        cornerRadius = CornerRadius(5.dp.toPx())
    )
    drawPath(
        path = Path().apply {
            moveTo(x - houseWidth * 0.08f, houseTop + houseHeight * 0.28f)
            lineTo(x + houseWidth * 0.5f, houseTop)
            lineTo(x + houseWidth * 1.08f, houseTop + houseHeight * 0.28f)
            close()
        },
        color = color
    )
    drawRoundRect(
        color = paletteHouseWindow(color),
        topLeft = Offset(x + houseWidth * 0.13f, houseTop + houseHeight * 0.37f),
        size = Size(houseWidth * 0.2f, houseHeight * 0.13f),
        cornerRadius = CornerRadius(2.dp.toPx())
    )
    drawRoundRect(
        color = paletteHouseWindow(color),
        topLeft = Offset(x + houseWidth * 0.66f, houseTop + houseHeight * 0.37f),
        size = Size(houseWidth * 0.2f, houseHeight * 0.13f),
        cornerRadius = CornerRadius(2.dp.toPx())
    )
}

private fun paletteHouseWindow(color: Color) = color.copy(alpha = color.alpha * 0.62f)

private fun DrawScope.drawFloatingLeaf(center: Offset, radius: Float, color: Color, rotation: Float) {
    rotate(rotation, center) {
        val leaf = Path().apply {
            moveTo(center.x, center.y - radius)
            quadraticTo(center.x + radius * 1.25f, center.y, center.x, center.y + radius)
            quadraticTo(center.x - radius * 1.25f, center.y, center.x, center.y - radius)
            close()
        }
        drawPath(leaf, color)
        drawLine(
            color = color.copy(alpha = color.alpha * 0.65f),
            start = Offset(center.x, center.y - radius * 0.65f),
            end = Offset(center.x, center.y + radius * 0.65f),
            strokeWidth = 1.dp.toPx()
        )
    }
}

@Composable
fun LoginScreen(
    onAuthenticate: suspend (String, String, UserRole) -> UserAccount?,
    onCheckConnection: suspend () -> String,
    onLoginSuccess: () -> Unit,
    initialDarkMode: Boolean = false,
    onThemeModeChange: (Boolean) -> Unit = {}
) {
    val configuration = LocalConfiguration.current
    val compactScreen = configuration.screenWidthDp < 380 || configuration.fontScale >= 1.2f
    val context = LocalContext.current
    val preferences = remember(context) {
        context.getSharedPreferences("smart_waste_admin_login", Context.MODE_PRIVATE)
    }
    val savedUsername = remember(preferences) {
        preferences.getString("admin_username", "").orEmpty()
    }

    var rememberMe by rememberSaveable {
        mutableStateOf(preferences.getBoolean("remember_admin", true))
    }
    var username by rememberSaveable {
        mutableStateOf(if (rememberMe) savedUsername else "")
    }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var selectedRole by rememberSaveable { mutableStateOf<UserRole?>(null) }
    var isDarkMode by rememberSaveable(initialDarkMode) { mutableStateOf(initialDarkMode) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var showForgotDialog by remember { mutableStateOf(false) }
    var serverReachable by remember { mutableStateOf<Boolean?>(null) }
    val scope = rememberCoroutineScope()
    val modeRippleProgress = remember { Animatable(1f) }
    var modeRippleOrigin by remember { mutableStateOf(Offset.Zero) }

    LaunchedEffect(Unit) {
        runCatching { onCheckConnection() }
            .onSuccess { serverReachable = true }
            .onFailure { serverReachable = false }
    }

    fun submitLogin() {
        val enteredUsername = username.trim()
        val enteredRole = selectedRole
        when {
            enteredRole == null -> errorMessage = "Pilih Admin atau Petugas terlebih dahulu."
            enteredUsername.isBlank() -> errorMessage = "Masukkan username atau email."
            password.isBlank() -> errorMessage = "Masukkan kata sandi."
            else -> scope.launch {
                isLoading = true
                errorMessage = ""
                try {
                    val account = onAuthenticate(enteredUsername, password, enteredRole)
                    if (account == null) {
                        errorMessage = "Username atau kata sandi tidak sesuai."
                    } else {
                        if (rememberMe) {
                            preferences.edit {
                                putBoolean("remember_admin", true)
                                putString("admin_username", enteredUsername)
                            }
                        } else {
                            preferences.edit { clear() }
                        }
                        onThemeModeChange(false)
                        onLoginSuccess()
                    }
                } catch (error: Exception) {
                    val detail = error.message.orEmpty()
                    val connectionProblem = listOf("connect", "timeout", "refused", "resolve")
                        .any { detail.contains(it, ignoreCase = true) }
                    errorMessage =
                        if (connectionProblem || (serverReachable == false && detail.isBlank())) {
                            "Server belum terhubung. Pastikan Apache XAMPP aktif di port 8080, lalu coba lagi."
                        } else {
                            detail.ifBlank { "Login gagal. Coba lagi sebentar." }
                        }
                } finally {
                    isLoading = false
                }
            }
        }
    }

    val palette = animateLoginPalette(isDarkMode)
    val primaryText = palette.text
    val secondaryText = palette.muted
    val logoTopColor by animateColorAsState(
        targetValue = if (isDarkMode) Color(0xFF2B3B30) else Color(0xFFE3F0D4),
        animationSpec = tween(760),
        label = "login-logo-top"
    )
    val logoBottomColor by animateColorAsState(
        targetValue = if (isDarkMode) Color(0xFF19271E) else Color(0xFFAED3B2),
        animationSpec = tween(760),
        label = "login-logo-bottom"
    )
    val logoBorderColor by animateColorAsState(
        targetValue = Color.White.copy(alpha = if (isDarkMode) 0.40f else 0.30f),
        animationSpec = tween(760),
        label = "login-logo-border"
    )
    val logoElevation by animateDpAsState(
        targetValue = if (isDarkMode) 18.dp else 11.dp,
        animationSpec = tween(760),
        label = "login-logo-elevation"
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        palette.backgroundTop,
                        palette.backgroundMiddle,
                        palette.backgroundBottom
                    )
                )
            )
    ) {
        Crossfade(
            targetState = isDarkMode,
            animationSpec = tween(durationMillis = 760, easing = FastOutSlowInEasing),
            label = "sunset-night-background",
            modifier = Modifier.fillMaxSize()
        ) { darkScene ->
            Box(modifier = Modifier.fillMaxSize()) {
                if (!darkScene) {
                    MorningAtmosphere(modifier = Modifier.fillMaxSize())
                }
                SmartWasteBackdrop(
                    palette = palette,
                    darkMode = darkScene,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(300.dp)
                )
            }
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            val progress = modeRippleProgress.value
            if (progress < 1f && modeRippleOrigin != Offset.Zero) {
                val waveAlpha = sin((progress * PI.toFloat()).toDouble()).toFloat().coerceIn(0f, 1f)
                val rippleRadius = size.maxDimension * (0.025f + progress * 1.35f)
                val rippleColor = if (isDarkMode) {
                    Color(0xFF70BF83)
                } else {
                    Color(0xFFB5D9BA)
                }
                drawCircle(
                    color = rippleColor.copy(alpha = waveAlpha * 0.12f),
                    radius = rippleRadius,
                    center = modeRippleOrigin
                )
                drawCircle(
                    color = rippleColor.copy(alpha = waveAlpha * 0.18f),
                    radius = rippleRadius,
                    center = modeRippleOrigin,
                    style = Stroke(width = 26.dp.toPx())
                )
                drawCircle(
                    color = rippleColor.copy(alpha = waveAlpha * 0.72f),
                    radius = rippleRadius,
                    center = modeRippleOrigin,
                    style = Stroke(width = 4.dp.toPx())
                )

            }
        }

        Column(
            modifier = Modifier
                .widthIn(max = 520.dp)
                .fillMaxSize()
                .align(Alignment.TopCenter)
                .windowInsetsPadding(WindowInsets.statusBars)
                .imePadding()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(
                    start = if (compactScreen) 16.dp else 28.dp,
                    end = if (compactScreen) 16.dp else 28.dp,
                    top = 10.dp,
                    bottom = 18.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(78.dp)
                    .shadow(
                        logoElevation,
                        CircleShape,
                        spotColor = palette.accent.copy(alpha = if (isDarkMode) 0.34f else 0.18f)
                    )
                    .clip(CircleShape)
                    .background(Brush.verticalGradient(listOf(logoTopColor, logoBottomColor)))
                    .border(1.2.dp, logoBorderColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.login_recycling_mark),
                    contentDescription = "Logo daur ulang Smart Waste",
                    colorFilter = RecyclingLogoFilter,
                    modifier = Modifier.size(59.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Masuk ke Smart Waste",
                color = primaryText,
                fontFamily = LoginFont,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                lineHeight = 30.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(13.dp))
            Crossfade(
                targetState = isDarkMode,
                animationSpec = tween(420),
                label = "mode-caption"
            ) { darkMode ->
                Text(
                    text = if (darkMode) {
                        "Mode malam · operasional tetap terpantau"
                    } else {
                        "Pagi yang lebih bersih, dimulai dari sekitar"
                    },
                    color = secondaryText,
                    fontFamily = LoginFont,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            LoginRoleSelector(
                selectedRole = selectedRole,
                onSelectRole = {
                    selectedRole = it
                    errorMessage = ""
                },
                palette = palette,
                darkMode = isDarkMode,
                compact = compactScreen
            )
            Spacer(modifier = Modifier.height(12.dp))

            LoginGlassPanel(palette = palette, darkMode = isDarkMode) {
                LoginPillField(
                    value = username,
                    onValueChange = { username = it; errorMessage = "" },
                    placeholder = "Username atau email",
                    leadingIcon = Icons.Filled.Person,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                    palette = palette,
                    darkMode = isDarkMode
                )
                Spacer(modifier = Modifier.height(10.dp))
                LoginPillField(
                    value = password,
                    onValueChange = { password = it; errorMessage = "" },
                    placeholder = "Kata sandi",
                    leadingIcon = Icons.Filled.Lock,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                    visualTransformation = if (passwordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    onImeAction = ::submitLogin,
                    palette = palette,
                    darkMode = isDarkMode,
                    trailingContent = {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .clickable { passwordVisible = !passwordVisible },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (passwordVisible) {
                                    Icons.Filled.Visibility
                                } else {
                                    Icons.Filled.VisibilityOff
                                },
                                contentDescription = if (passwordVisible) "Sembunyikan kata sandi" else "Tampilkan kata sandi",
                                modifier = Modifier.size(18.dp),
                                tint = secondaryText
                            )
                        }
                    }
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 48.dp)
                        .padding(top = 2.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier
                            .heightIn(min = 48.dp)
                            .toggleable(
                                value = rememberMe,
                                role = Role.Checkbox,
                                onValueChange = { rememberMe = it }
                            )
                            .semantics { role = Role.Checkbox },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .shadow(
                                    elevation = if (rememberMe) 7.dp else 2.dp,
                                    shape = RoundedCornerShape(5.dp),
                                    spotColor = palette.accent.copy(alpha = 0.30f)
                                )
                                .clip(RoundedCornerShape(5.dp))
                                .background(if (rememberMe) palette.accent else palette.surface)
                                .border(1.dp, palette.border, RoundedCornerShape(5.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            if (rememberMe) {
                                Icon(
                                    imageVector = Icons.Filled.Check,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(7.dp))
                        Text(
                            text = "Ingat username",
                            color = primaryText,
                            fontFamily = LoginFont,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp
                        )
                    }

                    TextButton(
                        onClick = { showForgotDialog = true },
                        contentPadding = PaddingValues(horizontal = 2.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Lupa kata sandi?",
                            color = palette.accentDark,
                            fontFamily = LoginFont,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp,
                            maxLines = 1
                        )
                    }
                }

                AnimatedVisibility(
                    visible = errorMessage.isNotBlank(),
                    enter = fadeIn() + slideInVertically(initialOffsetY = { -it / 3 })
                ) {
                    Text(
                        text = errorMessage,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 6.dp),
                        color = palette.error,
                        fontFamily = LoginFont,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(3.dp))
                LoginActionButton(
                    isLoading = isLoading,
                    selectedRole = selectedRole,
                    palette = palette,
                    darkMode = isDarkMode,
                    onClick = ::submitLogin
                )
            }
        }

        NeomorphicModeToggle(
            darkMode = isDarkMode,
            palette = palette,
            onClick = {
                val nextDarkMode = !isDarkMode
                isDarkMode = nextDarkMode
                onThemeModeChange(nextDarkMode)
                scope.launch {
                    modeRippleProgress.snapTo(0f)
                    modeRippleProgress.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(durationMillis = 860, easing = FastOutSlowInEasing)
                    )
                }
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(top = 10.dp, end = 14.dp)
                .onGloballyPositioned { coordinates ->
                    val position = coordinates.positionInRoot()
                    modeRippleOrigin = Offset(
                        x = position.x + coordinates.size.width / 2f,
                        y = position.y + coordinates.size.height / 2f
                    )
                }
        )
    }

    if (showForgotDialog) {
        AlertDialog(
            onDismissRequest = { showForgotDialog = false },
            title = {
                Text(
                    text = "Butuh bantuan masuk?",
                    color = palette.text,
                    fontFamily = LoginFont,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "Reset kata sandi dikelola admin Smart Waste. Hubungi admin untuk mengatur ulang akun.",
                    color = palette.muted,
                    fontFamily = LoginFont,
                    fontSize = 13.sp
                )
            },
            confirmButton = {
                TextButton(onClick = { showForgotDialog = false }) {
                    Text("Mengerti", color = palette.accentDark, fontFamily = LoginFont)
                }
            },
            containerColor = palette.surface
        )
    }
}

@Composable
private fun MorningAtmosphere(modifier: Modifier = Modifier) {
    val morningMotion = rememberInfiniteTransition(label = "soft-morning-light")
    val dawnBreath by morningMotion.animateFloat(
        initialValue = 0.20f,
        targetValue = 0.30f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 12000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "morning-dawn-breath"
    )
    val morningAir by morningMotion.animateFloat(
        initialValue = 0.11f,
        targetValue = 0.17f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 14500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "soft-morning-air"
    )
    val cloudDrift by morningMotion.animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 18000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "morning-cloud-drift"
    )
    val birdFlight by morningMotion.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 15000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "morning-birds"
    )

    Canvas(modifier = modifier) {
        // A broad, cool morning sky with a soft warm horizon; no circular light source.
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFB6D5E0).copy(alpha = 0.50f),
                    Color(0xFFD7E5E0).copy(alpha = 0.34f),
                    Color(0xFFEBD6B9).copy(alpha = 0.26f + dawnBreath * 0.12f),
                    Color(0xFFD2E0D0).copy(alpha = 0.18f),
                    Color.Transparent
                ),
                startY = 0f,
                endY = size.height * 0.90f
            ),
            size = size
        )
        val cloudShift = cloudDrift * 18.dp.toPx()

        val morningCloud = Path().apply {
            moveTo(-cloudShift, size.height * 0.16f)
            cubicTo(
                size.width * 0.22f - cloudShift, size.height * 0.11f,
                size.width * 0.38f - cloudShift, size.height * 0.21f,
                size.width * 0.62f - cloudShift, size.height * 0.14f
            )
            cubicTo(
                size.width * 0.78f - cloudShift, size.height * 0.09f,
                size.width * 0.89f - cloudShift, size.height * 0.15f,
                size.width - cloudShift, size.height * 0.10f
            )
            lineTo(size.width - cloudShift, size.height * 0.21f)
            cubicTo(
                size.width * 0.76f - cloudShift, size.height * 0.25f,
                size.width * 0.42f - cloudShift, size.height * 0.22f,
                -cloudShift, size.height * 0.26f
            )
            close()
        }
        drawPath(morningCloud, Color(0xFFF5F4E8).copy(alpha = 0.28f))

        val passingCloud = Path().apply {
            moveTo(size.width * 0.05f, size.height * 0.36f)
            cubicTo(
                size.width * 0.16f, size.height * 0.31f,
                size.width * 0.27f, size.height * 0.39f,
                size.width * 0.39f, size.height * 0.34f
            )
            cubicTo(
                size.width * 0.33f, size.height * 0.40f,
                size.width * 0.16f, size.height * 0.40f,
                size.width * 0.05f, size.height * 0.36f
            )
            close()
        }
        drawPath(passingCloud, Color(0xFFF5F4E8).copy(alpha = 0.18f))

        // A small, slow-moving flock gives the light theme its own morning cue.
        val flightPhase = birdFlight * PI.toFloat()
        val flockX = size.width * 0.13f + sin(flightPhase).toFloat() * 22.dp.toPx()
        val flockY = size.height * 0.30f + cos(flightPhase).toFloat() * 5.dp.toPx()
        val birdColor = Color(0xFF426B50).copy(alpha = 0.46f)
        drawMorningBird(Offset(flockX, flockY), 7.dp.toPx(), birdColor)
        drawMorningBird(
            Offset(flockX + 24.dp.toPx(), flockY - 8.dp.toPx()),
            5.5.dp.toPx(),
            birdColor.copy(alpha = 0.78f)
        )
        drawMorningBird(
            Offset(flockX + 44.dp.toPx(), flockY + 4.dp.toPx()),
            4.5.dp.toPx(),
            birdColor.copy(alpha = 0.62f)
        )

        drawRect(
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color(0xFFB8D7C0).copy(alpha = morningAir * 0.62f),
                    Color.Transparent,
                    Color(0xFFE4D9C1).copy(alpha = morningAir * 0.42f)
                )
            ),
            size = size
        )
    }
}

private fun DrawScope.drawMorningBird(center: Offset, size: Float, color: Color) {
    val wings = Path().apply {
        moveTo(center.x - size, center.y)
        cubicTo(
            center.x - size * 0.62f, center.y - size * 0.62f,
            center.x - size * 0.28f, center.y - size * 0.34f,
            center.x, center.y + size * 0.08f
        )
        cubicTo(
            center.x + size * 0.28f, center.y - size * 0.34f,
            center.x + size * 0.62f, center.y - size * 0.62f,
            center.x + size, center.y
        )
    }
    drawPath(wings, color, style = Stroke(width = 1.5.dp.toPx()))
}

@Composable
private fun LoginGlassPanel(
    palette: LoginPalette,
    darkMode: Boolean,
    content: @Composable ColumnScope.() -> Unit
) {
    val shape = RoundedCornerShape(24.dp)
    val glassTop by animateColorAsState(targetValue = if (darkMode) {
        Color(0xFFA5C5AA).copy(alpha = 0.15f)
    } else {
        Color(0xFFF5F5E8).copy(alpha = 0.94f)
    }, animationSpec = tween(800), label = "login-glass-top")
    val glassMiddle by animateColorAsState(targetValue = if (darkMode) {
        palette.surface.copy(alpha = 0.48f)
    } else {
        Color(0xFFDFEBDD).copy(alpha = 0.95f)
    }, animationSpec = tween(800), label = "login-glass-middle")
    val glassBottom by animateColorAsState(targetValue = if (darkMode) {
        Color(0xFF152019).copy(alpha = 0.82f)
    } else {
        Color(0xFFD7E6D3).copy(alpha = 0.92f)
    }, animationSpec = tween(800), label = "login-glass-bottom")
    val glassElevation by animateDpAsState(
        targetValue = if (darkMode) 10.dp else 5.dp,
        animationSpec = tween(800),
        label = "login-glass-elevation"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = glassElevation,
                shape = shape,
                ambientColor = palette.surface,
                spotColor = palette.accent.copy(alpha = if (darkMode) 0.34f else 0.09f)
            )
            .clip(shape)
            .background(Brush.verticalGradient(listOf(glassTop, glassMiddle, glassBottom)))
            .padding(horizontal = 18.dp, vertical = 16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            content = content
        )
    }
}

@Composable
private fun LoginRoleSelector(
    selectedRole: UserRole?,
    onSelectRole: (UserRole) -> Unit,
    palette: LoginPalette,
    darkMode: Boolean,
    compact: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(if (compact) 6.dp else 10.dp)
    ) {
        UserRole.entries.forEach { role ->
            val isSelected = role == selectedRole
            val roleStyle = loginRoleStyle(role, darkMode)
            val roleColor by animateColorAsState(
                targetValue = if (isSelected) {
                    roleStyle.accent
                } else {
                    palette.text.copy(alpha = if (darkMode) 0.82f else 1f)
                },
                animationSpec = tween(260),
                label = "${role.name.lowercase()}-role-color"
            )
            val roleFill by animateColorAsState(
                targetValue = if (isSelected) {
                    roleStyle.soft.copy(alpha = if (darkMode) 0.88f else 0.88f)
                } else {
                    roleStyle.soft.copy(alpha = if (darkMode) 0.20f else 0.76f)
                },
                animationSpec = tween(260),
                label = "${role.name.lowercase()}-role-fill"
            )
            val roleShape = RoundedCornerShape(22.dp)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 80.dp)
                    .clip(roleShape)
                    .background(roleFill, roleShape)
                    .selectable(
                        selected = isSelected,
                        role = Role.RadioButton,
                        onClick = { onSelectRole(role) }
                    )
                    .padding(
                        horizontal = if (compact) 7.dp else 11.dp,
                        vertical = if (compact) 8.dp else 9.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(if (compact) 7.dp else 10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(if (compact) 27.dp else 30.dp)
                            .clip(CircleShape)
                            .background(roleStyle.soft.copy(alpha = if (isSelected) 0.92f else 0.62f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = roleStyle.icon,
                            contentDescription = null,
                            modifier = Modifier.size(if (compact) 17.dp else 19.dp),
                            tint = roleColor
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Text(
                            text = role.label,
                            color = roleColor,
                            fontFamily = LoginFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = if (compact) 13.sp else 14.sp,
                            lineHeight = 16.sp,
                            maxLines = 1
                        )
                        Text(
                            text = roleStyle.hint,
                            color = palette.muted,
                            fontFamily = LoginFont,
                            fontWeight = FontWeight.Medium,
                            fontSize = 11.sp,
                            lineHeight = 14.sp,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LoginPillField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: ImageVector,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    palette: LoginPalette,
    darkMode: Boolean,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onImeAction: (() -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null
) {
    var focused by remember { mutableStateOf(false) }
    val shape = RoundedCornerShape(50.dp)
    val fieldText = palette.text

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .shadow(
                elevation = if (focused) 11.dp else 7.dp,
                shape = shape,
                ambientColor = palette.surface,
                spotColor = palette.accent.copy(alpha = if (focused) 0.20f else 0.10f)
            )
            .clip(shape)
            .background(palette.field.copy(alpha = if (darkMode) 0.70f else 0.76f))
            .border(
                width = if (focused) 1.5.dp else 1.dp,
                color = if (focused) palette.accent else palette.border,
                shape = shape
            )
            .padding(start = 15.dp, end = 9.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = leadingIcon,
            contentDescription = null,
            modifier = Modifier.size(17.dp),
            tint = fieldText
        )
        Spacer(modifier = Modifier.width(13.dp))
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .weight(1f)
                .onFocusChanged { focused = it.isFocused }
                .focusable(),
            singleLine = true,
            textStyle = TextStyle(
                color = fieldText,
                fontFamily = LoginFont,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            ),
            cursorBrush = SolidColor(palette.accent),
            visualTransformation = visualTransformation,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onNext = { },
                onDone = { onImeAction?.invoke() }
            ),
            decorationBox = { innerTextField ->
                Box(contentAlignment = Alignment.CenterStart) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = fieldText.copy(alpha = if (darkMode) 0.86f else 1f),
                            fontFamily = LoginFont,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1
                        )
                    }
                    innerTextField()
                }
            }
        )
        trailingContent?.invoke()
    }
}

@Composable
private fun LoginActionButton(
    isLoading: Boolean,
    selectedRole: UserRole?,
    palette: LoginPalette,
    darkMode: Boolean,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(50.dp)
    val roleStyle = selectedRole?.let { loginRoleStyle(it, darkMode) }
    val buttonColor by animateColorAsState(
        targetValue = roleStyle?.button ?: palette.accent,
        animationSpec = tween(700),
        label = "login-button-role-color"
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .shadow(
                elevation = 14.dp,
                shape = shape,
                ambientColor = palette.surface,
                spotColor = (roleStyle?.accent ?: palette.accent).copy(alpha = 0.28f)
            )
            .clip(shape)
            .background(buttonColor)
            .clickable(enabled = !isLoading, role = Role.Button, onClick = onClick)
            .padding(horizontal = 11.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = when {
                isLoading -> "Memeriksa akun..."
                selectedRole == null -> "Pilih peran untuk masuk"
                else -> "Masuk sebagai ${selectedRole.label}"
            },
            color = Color.White,
            fontFamily = LoginFont,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            maxLines = 2,
            textAlign = TextAlign.Center,
            lineHeight = 17.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .size(32.dp)
                .shadow(5.dp, CircleShape, spotColor = Color(0xFF183324).copy(alpha = 0.2f))
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.18f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                modifier = Modifier.size(19.dp),
                tint = Color.White
            )
        }
    }
}

@Composable
private fun NeomorphicModeToggle(
    darkMode: Boolean,
    palette: LoginPalette,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(50.dp)
    val toggleTopColor by animateColorAsState(
        targetValue = if (darkMode) Color(0xFF2C4634).copy(alpha = 0.92f) else Color(0xFFF0F3E4).copy(alpha = 0.96f),
        animationSpec = tween(650),
        label = "mode-toggle-top"
    )
    val toggleBottomColor by animateColorAsState(
        targetValue = if (darkMode) Color(0xFF15241A).copy(alpha = 0.86f) else Color(0xFFD7E9D4).copy(alpha = 0.94f),
        animationSpec = tween(650),
        label = "mode-toggle-bottom"
    )
    val iconRotation by animateFloatAsState(
        targetValue = if (darkMode) 180f else 0f,
        animationSpec = tween(520),
        label = "mode-toggle-rotation"
    )
    val toggleElevation by animateDpAsState(
        targetValue = if (darkMode) 12.dp else 8.dp,
        animationSpec = tween(650),
        label = "mode-toggle-elevation"
    )
    val toggleGlow by animateFloatAsState(
        targetValue = if (darkMode) 0.30f else 0.16f,
        animationSpec = tween(650),
        label = "mode-toggle-glow"
    )
    Box(
        modifier = modifier
            .size(48.dp)
            .shadow(
                toggleElevation,
                shape,
                ambientColor = palette.surface,
                spotColor = palette.accent.copy(alpha = toggleGlow)
            )
            .clip(shape)
            .background(
                Brush.verticalGradient(listOf(toggleTopColor, toggleBottomColor))
            )
            .clickable(role = Role.Button, onClick = onClick)
            .semantics { role = Role.Button },
        contentAlignment = Alignment.Center
    ) {
        Crossfade(targetState = darkMode, label = "theme-toggle-icon") { isDark ->
            Icon(
                imageVector = if (isDark) Icons.Filled.LightMode else Icons.Filled.DarkMode,
                contentDescription = if (isDark) "Aktifkan mode terang" else "Aktifkan mode gelap",
                modifier = Modifier.size(20.dp).graphicsLayer { rotationZ = iconRotation },
                tint = palette.accentDark
            )
        }
    }
}
