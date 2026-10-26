package com.uoi.pratikumwithbunafla.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.rounded.NightsStay
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.SwitchAccount
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.TaskAlt
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.LocalTextStyle
import androidx.compose.ui.text.TextStyle
import com.uoi.pratikumwithbunafla.ui.theme.AppBorder
import com.uoi.pratikumwithbunafla.ui.theme.AppSurface
import com.uoi.pratikumwithbunafla.ui.theme.AppSurfaceMuted
import com.uoi.pratikumwithbunafla.ui.theme.AppText
import com.uoi.pratikumwithbunafla.ui.theme.AppTextMuted
import com.uoi.pratikumwithbunafla.ui.theme.WasteBlue
import com.uoi.pratikumwithbunafla.ui.theme.WasteGreen
import com.uoi.pratikumwithbunafla.ui.theme.WasteGreenDark
import com.uoi.pratikumwithbunafla.ui.theme.WasteMint
import com.uoi.pratikumwithbunafla.ui.theme.WasteNavy
import com.uoi.pratikumwithbunafla.ui.theme.WasteOrange
import com.uoi.pratikumwithbunafla.ui.theme.WastePurple
import com.uoi.pratikumwithbunafla.ui.theme.WasteRed
import com.uoi.pratikumwithbunafla.ui.theme.WasteTeal
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import com.uoi.pratikumwithbunafla.data.model.*
import com.uoi.pratikumwithbunafla.data.remote.SmartWasteApi
import com.uoi.pratikumwithbunafla.domain.WasteFlow
import com.uoi.pratikumwithbunafla.ui.components.*
import com.uoi.pratikumwithbunafla.ui.theme.InterFont
import com.uoi.pratikumwithbunafla.ui.theme.DarkSmartWastePalette
import com.uoi.pratikumwithbunafla.ui.theme.LightSmartWastePalette
import com.uoi.pratikumwithbunafla.ui.theme.LocalSmartWasteAppearance
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

private val LoginInk: Color
    @Composable get() = MaterialTheme.colorScheme.onBackground
private val LoginMuted: Color
    @Composable get() = MaterialTheme.colorScheme.onSurfaceVariant
private val LoginAccent: Color
    @Composable get() = MaterialTheme.colorScheme.primary
private val TaskAmber = Color(0xFFB86B1D)
private val TaskBlue = Color(0xFF456FB5)
private val TaskPurple = Color(0xFF7952A0)
private val TaskTeal = Color(0xFF237E7B)
private val TaskGreen = Color(0xFF43814C)
private val TaskCoral = Color(0xFFC25556)
private val TaskIndigo = Color(0xFF535DAA)
private val TaskRose = Color(0xFFA64F75)

@Composable
private fun ScrollableDialogContent(
    spacing: Dp = 6.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    val maxHeight = (LocalConfiguration.current.screenHeightDp.dp - 300.dp)
        .coerceIn(120.dp, 360.dp)
    Column(
        modifier = Modifier.heightIn(max = maxHeight).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(spacing),
        content = content
    )
}

@Composable
private fun AnimatedAdminPageBackground() {
    val dark = LocalSmartWasteAppearance.current.darkMode
    val background by animateColorAsState(
        targetValue = if (dark) DarkSmartWastePalette.background else LightSmartWastePalette.background,
        animationSpec = tween(durationMillis = 820, easing = FastOutSlowInEasing),
        label = "admin-page-background"
    )
    Box(Modifier.fillMaxSize().background(background))
}

@Composable
private fun AdminThemeTransitionWave() {
    val dark = LocalSmartWasteAppearance.current.darkMode
    val modeProgress by animateFloatAsState(
        targetValue = if (dark) 1f else 0f,
        animationSpec = tween(durationMillis = 860, easing = FastOutSlowInEasing),
        label = "admin-theme-wave"
    )
    val waveProgress = if (dark) modeProgress else 1f - modeProgress

    Canvas(Modifier.fillMaxSize()) {
        val waveStrength = sin((waveProgress * PI).toDouble()).toFloat().coerceIn(0f, 1f)
        if (waveStrength > 0.015f) {
            val center = Offset(size.width * 0.96f, size.height * 0.08f)
            val radius = size.maxDimension * (0.04f + waveProgress * 1.18f)
            val tint = if (dark) Color(0xFFB8D2B9) else Color(0xFF789D7B)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        tint.copy(alpha = waveStrength * 0.19f),
                        tint.copy(alpha = waveStrength * 0.07f),
                        Color.Transparent
                    ),
                    center = center,
                    radius = radius
                ),
                radius = radius,
                center = center
            )
            drawCircle(
                color = tint.copy(alpha = waveStrength * 0.30f),
                radius = radius * 0.82f,
                center = center,
                style = Stroke(width = 18.dp.toPx())
            )
            drawCircle(
                color = tint.copy(alpha = waveStrength * 0.62f),
                radius = radius * 0.82f,
                center = center,
                style = Stroke(width = 1.6.dp.toPx())
            )
        }
    }
}

@Composable
private fun AdminPage(
    current: String,
    account: UserAccount,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit,
    bottomBar: (@Composable () -> Unit)? = null,
    backgroundContent: @Composable BoxScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val horizontalPadding = when {
        screenWidth < 350.dp -> 14.dp
        screenWidth >= 600.dp -> 28.dp
        else -> 20.dp
    }
    Box(modifier = Modifier.fillMaxSize()) {
        val isAdmin = account.role == UserRole.ADMIN
        if (isAdmin) AnimatedAdminPageBackground()
        backgroundContent()
        if (isAdmin) AdminThemeTransitionWave()
        CompositionLocalProvider(
            LocalTextStyle provides LocalTextStyle.current.merge(
                TextStyle(
                    fontFamily = InterFont
                )
            )
        ) {
            AnimatedVisibility(
                visible = true,
                modifier = Modifier.fillMaxSize(),
                enter = fadeIn(tween(260)) + slideInVertically(
                    initialOffsetY = { it / 36 },
                    animationSpec = tween(260)
                )
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Column(
                            modifier = Modifier.widthIn(max = 720.dp).fillMaxHeight().fillMaxWidth()
                                .windowInsetsPadding(WindowInsets.statusBars)
                                .verticalScroll(rememberScrollState())
                                .padding(horizontal = horizontalPadding, vertical = 11.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            AdminTopBar(
                                account = account,
                                onLogout = onLogout,
                                onOpenProfile = { onNavigate("profile") }
                            )
                            content()
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                    when {
                        bottomBar != null -> bottomBar()
                        account.role == UserRole.ADMIN -> AdminNavigationBar(current, onNavigate)
                    }
                }
            }
        }
    }
}

@Composable
private fun AdminSectionHeading(eyebrow: String, title: String, subtitle: String) {
    val compact = LocalConfiguration.current.screenWidthDp < 360
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 2.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            eyebrow,
            color = LoginAccent,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
        Text(
            title,
            color = LoginInk,
            fontSize = if (compact) 20.sp else 22.sp,
            lineHeight = if (compact) 25.sp else 28.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            subtitle,
            color = LoginMuted,
            fontSize = 12.sp,
            lineHeight = 18.sp,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun OperationalMetric(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.clip(RoundedCornerShape(12.dp)).clickable(onClick = onClick)
            .padding(horizontal = 7.dp, vertical = 5.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            label,
            color = AppTextMuted,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1
        )
        Text(value, color = LoginInk, fontSize = 15.sp, fontWeight = FontWeight.Bold, maxLines = 1)
    }
}

@Composable
private fun AdminTopBar(
    account: UserAccount,
    onLogout: () -> Unit,
    onOpenProfile: () -> Unit
) {
    var profileMenuExpanded by remember(account.username) { mutableStateOf(false) }
    val appearance = LocalSmartWasteAppearance.current
    val compact = LocalConfiguration.current.screenWidthDp < 350
    val roleIconTint = if (appearance.darkMode) Color(0xFFA9B9AC) else Color(0xFF5D776C)
    val modeIconTint by animateColorAsState(
        targetValue = if (appearance.darkMode) Color(0xFFC2B48E) else Color(0xFF687F83),
        animationSpec = tween(520, easing = FastOutSlowInEasing),
        label = "admin-theme-icon-tint"
    )
    val modeIconSurface by animateColorAsState(
        targetValue = if (appearance.darkMode) Color(0xFF29372F) else Color(0xFFE3EBE5),
        animationSpec = tween(520, easing = FastOutSlowInEasing),
        label = "admin-theme-icon-surface"
    )
    val modeIconBorder by animateColorAsState(
        targetValue = if (appearance.darkMode) Color(0xFF405247) else Color(0xFFC6D4C9),
        animationSpec = tween(520, easing = FastOutSlowInEasing),
        label = "admin-theme-icon-border"
    )
    val roleIcon = if (account.role == UserRole.ADMIN) {
        Icons.Filled.AdminPanelSettings
    } else {
        Icons.Rounded.Person
    }
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = roleIcon,
                contentDescription = null,
                tint = roleIconTint,
                modifier = Modifier.size(if (compact) 19.dp else 21.dp)
            )
            Text(
                text = account.role.label,
                color = AppText,
                fontSize = if (compact) 15.sp else 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Box {
            IconButton(onClick = { profileMenuExpanded = true }, modifier = Modifier.size(if (compact) 38.dp else 42.dp)) {
                UserAvatar(account.name, modifier = Modifier.size(if (compact) 30.dp else 34.dp))
            }
            DropdownMenu(
                expanded = profileMenuExpanded,
                onDismissRequest = { profileMenuExpanded = false }) {
                Column(
                    modifier = Modifier.width(220.dp).padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Text("Akun aktif", color = AppTextMuted, fontSize = 11.sp)
                    Text(
                        account.name,
                        color = AppText,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "@${account.username} · ${account.role.label}",
                        color = LoginMuted,
                        fontSize = 11.sp
                    )
                }
                if (account.role == UserRole.ADMIN) {
                    DropdownMenuItem(
                        text = { Text("Pengaturan profil") },
                        leadingIcon = { Icon(Icons.Rounded.Person, contentDescription = null) },
                        onClick = {
                            profileMenuExpanded = false
                            onOpenProfile()
                        }
                    )
                }
                DropdownMenuItem(
                    text = { Text(if (account.role == UserRole.ADMIN) "Pindah ke akun Petugas" else "Pindah akun") },
                    leadingIcon = { Icon(Icons.Filled.SwitchAccount, contentDescription = null) },
                    onClick = {
                        profileMenuExpanded = false
                        onLogout()
                    }
                )
            }
        }
        Spacer(modifier = Modifier.width(if (compact) 2.dp else 6.dp))
        IconButton(onClick = appearance.onToggleTheme, modifier = Modifier.size(if (compact) 36.dp else 40.dp)) {
            Box(
                modifier = Modifier.size(if (compact) 30.dp else 34.dp)
                    .clip(CircleShape)
                    .background(modeIconSurface)
                    .border(1.dp, modeIconBorder, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Crossfade(
                    targetState = appearance.darkMode,
                    animationSpec = tween(380),
                    label = "admin-theme-icon"
                ) { dark ->
                    Icon(
                        imageVector = if (dark) Icons.Rounded.WbSunny else Icons.Rounded.NightsStay,
                        contentDescription = if (dark) "Aktifkan mode terang" else "Aktifkan mode gelap",
                        tint = modeIconTint,
                        modifier = Modifier.size(if (compact) 16.dp else 18.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun AdminNavigationBar(
    current: String,
    onNavigate: (String) -> Unit
) {
    val destinations = listOf(
        Triple("dashboard", "Beranda", Icons.Filled.Home),
        Triple("reports", "Laporan", Icons.AutoMirrored.Filled.Assignment),
        Triple("ops", "Operasional", Icons.Filled.Build),
        Triple("stats", "Statistik", Icons.Filled.BarChart)
    )
    FloatingRoleNavigationBar(current, destinations, onNavigate)
}

@Composable
private fun FloatingRoleNavigationBar(
    current: String,
    destinations: List<Triple<String, String, ImageVector>>,
    onNavigate: (String) -> Unit
) {
    val darkMode = LocalSmartWasteAppearance.current.darkMode
    val selectedIndex = destinations.indexOfFirst { it.first == current }.coerceAtLeast(0)
    val barColor by animateColorAsState(
        targetValue = if (darkMode) Color(0xFF14231B) else Color(0xFF244638),
        animationSpec = tween(460, easing = FastOutSlowInEasing),
        label = "role-navbar-surface"
    )
    val activeColor by animateColorAsState(
        targetValue = if (darkMode) Color(0xFF89C965) else Color(0xFFA5DA73),
        animationSpec = tween(460, easing = FastOutSlowInEasing),
        label = "role-navbar-active"
    )
    val inactiveColor = if (darkMode) Color(0xFFB4C1B8) else Color(0xFFD5E0D8)

    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(top = 5.dp)
            .height(82.dp)
    ) {
        val horizontalInset = 24.dp
        val itemWidth = (maxWidth - horizontalInset - horizontalInset) / destinations.size
        val targetCenter = horizontalInset + itemWidth * (selectedIndex + 0.5f)
        val activeCenter by animateDpAsState(
            targetValue = targetCenter,
            animationSpec = tween(520, easing = FastOutSlowInEasing),
            label = "role-navbar-indicator-position"
        )

        Canvas(
            modifier = Modifier.align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(64.dp)
        ) {
            val notchHalfWidth = 34.dp.toPx()
            val notchDepth = 30.dp.toPx()
            val centerX = activeCenter.toPx().coerceIn(
                notchHalfWidth,
                size.width - notchHalfWidth
            )
            val notchStart = centerX - notchHalfWidth
            val notchEnd = centerX + notchHalfWidth
            val path = Path().apply {
                moveTo(0f, 0f)
                lineTo(notchStart, 0f)
                cubicTo(
                    notchStart + 7.dp.toPx(), 0f,
                    centerX - notchHalfWidth * 0.68f, notchDepth,
                    centerX, notchDepth
                )
                cubicTo(
                    centerX + notchHalfWidth * 0.68f, notchDepth,
                    notchEnd - 7.dp.toPx(), 0f,
                    notchEnd, 0f
                )
                lineTo(size.width, 0f)
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }
            drawPath(path, barColor)
        }

        Row(
            modifier = Modifier.align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = horizontalInset),
            verticalAlignment = Alignment.CenterVertically
        ) {
            destinations.forEachIndexed { index, (route, label, icon) ->
                val selected = index == selectedIndex
                Box(
                    modifier = Modifier.weight(1f).fillMaxHeight()
                        .clickable { onNavigate(route) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        tint = if (selected) Color.Transparent else inactiveColor,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }

        Box(
            modifier = Modifier.align(Alignment.TopStart)
                .offset(x = activeCenter - 25.dp)
                .size(50.dp)
                .shadow(8.dp, CircleShape)
                .background(activeColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = destinations[selectedIndex].third,
                contentDescription = null,
                tint = barColor,
                modifier = Modifier.size(22.dp)
            )
        }
    }
}

@Composable
private fun DashboardHeroCard(
    summary: AdminDashboardSummary,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(WasteGreenDark)
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(
                "Ringkasan laporan",
                color = Color.White.copy(alpha = 0.82f),
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
            Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                Text(
                    summary.totalReports.toString(),
                    color = Color.White,
                    fontSize = 36.sp,
                    lineHeight = 42.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    "laporan",
                    modifier = Modifier.padding(bottom = 5.dp),
                    color = Color.White.copy(alpha = 0.86f),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Text(
                "${summary.totalWeightKg.pretty()} kg sampah tercatat",
                color = Color.White.copy(alpha = 0.88f),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        }
        Icon(
            Icons.AutoMirrored.Filled.Assignment,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.9f),
            modifier = Modifier.size(26.dp)
        )
    }
}

@Composable
private fun AdminDashboardBackdrop(modifier: Modifier = Modifier) {
    val dark = LocalSmartWasteAppearance.current.darkMode
    val motion = rememberInfiniteTransition(label = "admin-dashboard-ambience")
    val breeze by motion.animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 18000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dashboard-breeze"
    )
    val lightPulse by motion.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 7600, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dashboard-light-pulse"
    )

    Canvas(modifier) {
        val width = size.width
        val height = size.height
        val drift = breeze * 18.dp.toPx()
        val pulse = 0.5f + 0.5f * sin((lightPulse * 2f * PI).toDouble()).toFloat()
        val upperGlow = Offset(width * 0.88f + drift * 0.35f, height * 0.12f)
        val upperColor = if (dark) Color(0xFF82B99A) else Color(0xFFE7D6A8)
        val upperRadius = width * 0.56f
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    upperColor.copy(alpha = (if (dark) 0.12f else 0.16f) + pulse * 0.025f),
                    upperColor.copy(alpha = if (dark) 0.045f else 0.055f),
                    Color.Transparent
                ),
                center = upperGlow,
                radius = upperRadius
            ),
            radius = upperRadius,
            center = upperGlow
        )

        val lowerGlow = Offset(width * 0.10f - drift * 0.24f, height * 0.88f)
        val lowerColor = if (dark) Color(0xFF4C8D78) else Color(0xFF74A889)
        val lowerRadius = width * 0.48f
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(lowerColor.copy(alpha = if (dark) 0.10f else 0.085f), Color.Transparent),
                center = lowerGlow,
                radius = lowerRadius
            ),
            radius = lowerRadius,
            center = lowerGlow
        )

        val leafTint = if (dark) Color(0xFF8FB69A) else Color(0xFF4D8664)
        val leaves = listOf(
            Triple(0.07f, 0.27f, 0.72f),
            Triple(0.91f, 0.39f, 0.55f),
            Triple(0.13f, 0.70f, 0.62f),
            Triple(0.86f, 0.77f, 0.48f)
        )
        leaves.forEachIndexed { index, (xFraction, yFraction, scale) ->
            val sway = sin((lightPulse * 2f * PI + index * 1.7f).toDouble()).toFloat()
            val center = Offset(
                width * xFraction + drift * (if (index % 2 == 0) 0.28f else -0.24f),
                height * yFraction + sway * 5.dp.toPx()
            )
            val leafLength = 17.dp.toPx() * scale
            val leafHeight = 7.dp.toPx() * scale
            val leaf = Path().apply {
                moveTo(center.x - leafLength * 0.52f, center.y)
                cubicTo(
                    center.x - leafLength * 0.16f, center.y - leafHeight,
                    center.x + leafLength * 0.30f, center.y - leafHeight * 0.72f,
                    center.x + leafLength * 0.52f, center.y - leafHeight * 0.08f
                )
                cubicTo(
                    center.x + leafLength * 0.24f, center.y + leafHeight * 0.80f,
                    center.x - leafLength * 0.25f, center.y + leafHeight * 0.68f,
                    center.x - leafLength * 0.52f, center.y
                )
                close()
            }
            val alpha = (if (dark) 0.10f else 0.095f) + pulse * 0.025f
            drawPath(leaf, leafTint.copy(alpha = alpha))
            drawLine(
                color = leafTint.copy(alpha = alpha * 0.78f),
                start = Offset(center.x - leafLength * 0.38f, center.y),
                end = Offset(center.x + leafLength * 0.38f, center.y - leafHeight * 0.04f),
                strokeWidth = 0.8.dp.toPx(),
                cap = StrokeCap.Round
            )
        }
    }
}

@Composable
fun AdminProfileScreen(
    account: UserAccount,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit
) {
    AdminPage(
        current = "profile",
        account = account,
        onNavigate = onNavigate,
        onLogout = onLogout,
        backgroundContent = { AdminDashboardBackdrop(Modifier.fillMaxSize()) }
    ) {
        AccountProfileSettings(account, onLogout)
    }
}

@Composable
fun AdminDashboardScreen(
    summary: AdminDashboardSummary,
    reports: List<WasteRecord>,
    errorMessage: String,
    account: UserAccount,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit
) {
    AdminPage(
        current = "dashboard",
        account = account,
        onNavigate = onNavigate,
        onLogout = onLogout,
        backgroundContent = { AdminDashboardBackdrop(Modifier.fillMaxSize()) }
    ) {
        AdminSectionHeading(
            "ADMIN • RUANG KENDALI",
            "Ringkasan Smart Waste",
            "Laporan warga, tim lapangan, dan kapasitas dalam satu alur."
        )
        if (errorMessage.isNotBlank()) InlineError(errorMessage)
        DashboardHeroCard(summary) { onNavigate("status:semua") }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text("Tugas Admin", color = AppText, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                Text("Pintasan pekerjaan harian", color = AppTextMuted, fontSize = 11.sp)
            }
            Text(
                "Semua laporan →",
                modifier = Modifier.clip(RoundedCornerShape(10.dp))
                    .clickable { onNavigate("status:semua") }.padding(6.dp),
                color = LoginAccent,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                AdminTaskTile("Verifikasi", Icons.Filled.History, TaskAmber, Modifier.weight(1f)) {
                    onNavigate("reports")
                }
                AdminTaskTile("Pantau tugas", Icons.Filled.LocalShipping, TaskBlue, Modifier.weight(1f)) {
                    onNavigate("status:${WasteFlow.ASSIGNED}")
                }
                AdminTaskTile("Petugas", Icons.Filled.AdminPanelSettings, TaskPurple, Modifier.weight(1f)) {
                    onNavigate("ops:petugas")
                }
                AdminTaskTile("Armada", Icons.Filled.LocalShipping, TaskTeal, Modifier.weight(1f)) {
                    onNavigate("ops:kendaraan")
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                AdminTaskTile("TPA", Icons.Filled.Place, TaskGreen, Modifier.weight(1f)) {
                    onNavigate("ops:tpa")
                }
                AdminTaskTile("Data warga", Icons.Rounded.Person, TaskCoral, Modifier.weight(1f)) {
                    onNavigate("ops:warga")
                }
                AdminTaskTile("Statistik", Icons.Filled.BarChart, TaskIndigo, Modifier.weight(1f)) {
                    onNavigate("stats")
                }
                AdminTaskTile("Riwayat", Icons.AutoMirrored.Filled.Assignment, TaskRose, Modifier.weight(1f)) {
                    onNavigate("status:${WasteFlow.DONE}")
                }
            }
        }
        AppCard(
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color.White,
            borderColor = AppBorder,
            accentColor = LoginAccent
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    OperationalMetric(
                        "Petugas",
                        summary.activeWorkers.toString(),
                        Modifier.weight(1f)
                    ) { onNavigate("ops:petugas") }
                    OperationalMetric(
                        "Armada",
                        "${summary.availableVehicles}/${summary.vehicleCount}",
                        Modifier.weight(1f)
                    ) { onNavigate("ops:kendaraan") }
                    OperationalMetric(
                        "TPA",
                        "${summary.tpaUtilizationPercent.pretty()}%",
                        Modifier.weight(1f)
                    ) { onNavigate("ops:tpa") }
                }
                SoftProgress(summary.tpaUtilizationPercent.toFloat() / 100f)
                if (summary.hazardAlerts > 0) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(7.dp)
                    ) {
                        Icon(
                            Icons.Filled.ReportProblem,
                            contentDescription = null,
                            tint = WasteRed,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            "${summary.hazardAlerts} laporan berisiko perlu perhatian",
                            color = WasteRed,
                            fontSize = 11.sp,
                            lineHeight = 16.sp
                        )
                    }
                } else Text(
                    buildString {
                        append("Kapasitas TPA: ")
                        append(summary.tpaUsedTon.pretty())
                        append(" terisi · ")
                        append(summary.tpaReservedTon.pretty())
                        append(" dicadangkan dari ")
                        append(summary.tpaCapacityTon.pretty())
                        append(" ton")
                    },
                    color = AppTextMuted,
                    fontSize = 11.sp,
                    lineHeight = 16.sp
                )
            }
        }
        SectionTitle("Laporan terbaru", "Ringkasan laporan warga")
        val latest = reports.take(3)
        if (latest.isEmpty()) {
            EmptyAdminState("Belum ada laporan dari warga.")
        } else {
            AppCard(
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.White,
                borderColor = AppBorder
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 13.dp, vertical = 4.dp)
                ) {
                    latest.forEach { report ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    report.code,
                                    color = AppText,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    "${report.type} · ${report.location}",
                                    color = AppTextMuted,
                                    fontSize = 11.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            StatusPill(report.status, reportAccent(report.status))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AdminTaskTile(
    label: String,
    icon: ImageVector,
    accent: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.height(78.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 3.dp, vertical = 7.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Box(
            modifier = Modifier.size(32.dp)
                .shadow(2.dp, CircleShape)
                .clip(CircleShape)
                .background(accent),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(17.dp))
        }
        Text(
            label,
            color = AppText,
            fontSize = 11.sp,
            lineHeight = 12.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 2,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun AdminVerificationScreen(
    reports: List<WasteRecord>,
    workers: List<AdminWorker>,
    vehicles: List<WasteVehicle>,
    facilities: List<WasteFacility>,
    selectedStatus: String?,
    showAllReports: Boolean,
    errorMessage: String,
    account: UserAccount,
    onReject: (WasteRecord) -> Unit,
    onAssign: suspend (WasteRecord, Int, Int, Int) -> Unit,
    onOpenPhoto: (String) -> Unit,
    onReportViewChange: (Boolean) -> Unit,
    onFilterChange: (String?) -> Unit,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit
) {
    var assigningReport by remember { mutableStateOf<WasteRecord?>(null) }
    val statusFilters = listOf(
        null,
        WasteFlow.WAITING,
        WasteFlow.PROCESSING,
        WasteFlow.ASSIGNED,
        WasteFlow.TRANSPORTED,
        WasteFlow.DONE,
        WasteFlow.REJECTED
    )
    val waitingReports = reports.filter { it.status == WasteFlow.WAITING }
    val visibleReports = when {
        !showAllReports -> waitingReports
        selectedStatus == null -> reports
        selectedStatus == WasteFlow.PROCESSING -> reports.filter { WasteFlow.isInProgress(it.status) }
        else -> reports.filter { it.status == selectedStatus }
    }
    AdminPage("reports", account, onNavigate, onLogout) {
        AdminSectionHeading(
            "ADMIN • LAPORAN",
            "Laporan warga",
            "Verifikasi laporan baru dan pantau status penanganannya."
        )
        if (errorMessage.isNotBlank()) InlineError(errorMessage)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(
                false to "Perlu verifikasi · ${waitingReports.size}",
                true to "Semua laporan · ${reports.size}"
            ).forEach { (showAll, label) ->
                val active = showAllReports == showAll
                TextButton(
                    onClick = { onReportViewChange(showAll) },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = if (active) LoginAccent else LoginMuted,
                        containerColor = if (active) WasteBlue else AppSurface
                    ),
                    shape = RoundedCornerShape(13.dp)
                ) {
                    Text(label, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        if (showAllReports) {
            Row(
                modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                statusFilters.forEach { status ->
                    val active = selectedStatus == status
                    TextButton(
                        onClick = { onFilterChange(status) },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = if (active) LoginAccent else AppTextMuted,
                            containerColor = if (active) WasteBlue else AppSurfaceMuted
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            if (status == null) "Semua" else WasteFlow.label(status),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        SectionTitle(
            if (showAllReports) "${visibleReports.size} laporan" else "Antrean verifikasi",
            if (showAllReports) "Pantau laporan berdasarkan status" else "${waitingReports.size} laporan menunggu keputusan"
        )
        if (visibleReports.isEmpty()) {
            EmptyAdminState(
                if (showAllReports) "Belum ada laporan pada status ini."
                else "Semua laporan baru sudah ditinjau."
            )
        } else {
            visibleReports.forEach { report ->
                if (showAllReports) {
                    AdminReportCard(report, onOpenPhoto, compact = true)
                } else {
                    AdminReportCard(
                        report,
                        onOpenPhoto,
                        onReject = { onReject(report) },
                        onAssign = { assigningReport = report }
                    )
                }
            }
        }
    }
    assigningReport?.let { report ->
        AssignmentDialog(
            report = report,
            workers = workers.filter { it.active && it.username.isNotBlank() },
            vehicles = vehicles.filter {
                it.condition.equals(
                    "baik",
                    true
                ) && it.availableKg >= report.weightKg
            },
            facilities = facilities.filter { it.remainingTon * 1000 >= report.weightKg },
            onDismiss = { assigningReport = null },
            onAssign = { workerId, vehicleId, tpaId ->
                onAssign(report, workerId, vehicleId, tpaId)
                assigningReport = null
            }
        )
    }
}

@Composable
private fun AdminReportCard(
    report: WasteRecord,
    onOpenPhoto: (String) -> Unit,
    onReject: (() -> Unit)? = null,
    onAssign: (() -> Unit)? = null,
    compact: Boolean = false
) {
    var showDetails by rememberSaveable(report.id) { mutableStateOf(!compact) }
    val accent = reportAccent(report.status)
    AppCard(
        modifier = Modifier.fillMaxWidth(),
        containerColor = Color.White,
        accentColor = accent,
        borderColor = AppBorder
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        report.code,
                        color = AppText,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text("Dari ${report.reportedBy}", color = AppTextMuted, fontSize = 11.sp)
                }
                StatusPill(report.status, accent)
            }
            Text(
                "${report.type}${if (report.weightKg > 0) "  •  ${report.weightKg.pretty()} kg" else ""}",
                color = AppText, fontSize = 12.sp, fontWeight = FontWeight.SemiBold
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    Icons.Filled.Place,
                    contentDescription = null,
                    tint = LoginAccent,
                    modifier = Modifier.size(16.dp)
                )
                Text(report.location, color = AppTextMuted, fontSize = 12.sp)
            }
            if (showDetails) {
                if (report.hazardLevel.isNotBlank() && !report.hazardLevel.equals("RENDAH", true)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            Icons.Filled.ReportProblem,
                            contentDescription = null,
                            tint = WasteRed,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            "Bahaya ${report.hazardLevel}",
                            color = WasteRed,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
                if (report.workerName.isNotBlank()) {
                    Text(
                        "Petugas: ${report.workerName}  •  ${report.vehiclePlate}  •  ${report.tpaName}",
                        color = LoginMuted,
                        fontSize = 11.sp
                    )
                }
                if (report.reportedAt.isNotBlank()) Text(
                    "Dilaporkan ${report.reportedAt}",
                    color = AppTextMuted,
                    fontSize = 11.sp
                )
                if (report.description.isNotBlank()) Text(
                    report.description,
                    color = AppTextMuted,
                    fontSize = 11.sp,
                    lineHeight = 16.sp
                )
                if (!report.photoUrl.isNullOrBlank()) {
                    TextButton(onClick = { onOpenPhoto(report.photoUrl) }) {
                        Text(
                            "Lihat foto",
                            color = LoginAccent,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            } else if (compact) {
                TextButton(
                    onClick = { showDetails = true },
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                ) {
                    Text(
                        "Lihat detail ↓",
                        color = LoginAccent,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            if (compact && showDetails) {
                TextButton(
                    onClick = { showDetails = false },
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                ) {
                    Text(
                        "Tutup detail ↑",
                        color = LoginMuted,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            if (onReject != null || onAssign != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    if (onReject != null) {
                        TextButton(
                            onClick = onReject,
                            colors = ButtonDefaults.textButtonColors(contentColor = WasteRed)
                        ) {
                            Text("Tolak", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    if (onAssign != null) {
                        TextButton(
                            onClick = onAssign,
                            colors = ButtonDefaults.textButtonColors(contentColor = LoginAccent)
                        ) {
                            Text("Tugaskan", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AssignmentDialog(
    report: WasteRecord,
    workers: List<AdminWorker>,
    vehicles: List<WasteVehicle>,
    facilities: List<WasteFacility>,
    onDismiss: () -> Unit,
    onAssign: suspend (Int, Int, Int) -> Unit
) {
    var worker by remember(report.id) { mutableStateOf<AdminWorker?>(null) }
    var vehicle by remember(report.id) { mutableStateOf<WasteVehicle?>(null) }
    var facility by remember(report.id) { mutableStateOf<WasteFacility?>(null) }
    var saving by remember(report.id) { mutableStateOf(false) }
    var saveError by remember(report.id) { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val eligibleWorkers = workers.filter { it.active && it.hasAccount }
    val eligibleVehicles = vehicles.filter {
        it.condition.equals("baik", ignoreCase = true) && it.availableKg >= report.weightKg
    }
    val eligibleFacilities = facilities.filter {
        it.remainingTon >= report.weightKg / 1000.0
    }
    val canAssign = worker?.let(eligibleWorkers::contains) == true &&
            vehicle?.let(eligibleVehicles::contains) == true &&
            facility?.let(eligibleFacilities::contains) == true
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Buat penugasan", color = LoginInk, fontWeight = FontWeight.Bold) },
        text = {
            ScrollableDialogContent(spacing = 9.dp) {
                Text(
                    "${report.code} • ${report.weightKg.pretty()} kg",
                    color = LoginMuted,
                    fontSize = 11.sp
                )
                SelectionField(
                    "Petugas",
                    worker?.let { "${it.name} · ${it.zone.ifBlank { "tanpa zona" }}" },
                    eligibleWorkers,
                    { "${it.name} · ${it.zone.ifBlank { "tanpa zona" }}" }) { worker = it }
                SelectionField(
                    "Kendaraan",
                    vehicle?.let { "${it.plate} · sisa ${it.availableKg.pretty()} kg" },
                    eligibleVehicles,
                    { "${it.plate} · sisa ${it.availableKg.pretty()} kg" }) { vehicle = it }
                SelectionField(
                    "TPA tujuan",
                    facility?.let { "${it.name} · sisa ${it.remainingTon.pretty()} ton" },
                    eligibleFacilities,
                    { "${it.name} · sisa ${it.remainingTon.pretty()} ton" }) { facility = it }
                if (eligibleWorkers.isEmpty() || eligibleVehicles.isEmpty() || eligibleFacilities.isEmpty()) {
                    val unavailable = buildList {
                        if (eligibleWorkers.isEmpty()) add("petugas aktif yang memiliki akun Android")
                        if (eligibleVehicles.isEmpty()) add("kendaraan sehat dengan kapasitas cukup")
                        if (eligibleFacilities.isEmpty()) add("TPA dengan kapasitas cukup")
                    }.joinToString(", ")
                    Text(
                        "Belum bisa ditugaskan: tidak ada $unavailable. Periksa menu Operasional.",
                        color = WasteRed,
                        fontSize = 11.sp
                    )
                }
                if (saveError.isNotBlank()) InlineError(saveError)
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (canAssign && !saving) scope.launch {
                        saving = true
                        saveError = ""
                        try {
                            onAssign(worker!!.id, vehicle!!.id, facility!!.id)
                        } catch (cancelled: CancellationException) {
                            throw cancelled
                        } catch (error: Exception) {
                            saveError = error.message
                                ?: "Penugasan gagal disimpan. Periksa koneksi dan coba lagi."
                        } finally {
                            saving = false
                        }
                    }
                },
                enabled = canAssign && !saving
            ) {
                Text(
                    if (saving) "Menyimpan…" else "Tetapkan",
                    color = LoginAccent,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !saving) {
                Text(
                    "Batal",
                    color = LoginMuted
                )
            }
        },
        containerColor = AppSurface
    )
}

@Composable
private fun <T> SelectionField(
    title: String,
    selectedLabel: String?,
    choices: List<T>,
    label: (T) -> String,
    onSelected: (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Column {
        Text(title, color = LoginMuted, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
        Box {
            TextButton(
                onClick = { expanded = true },
                enabled = choices.isNotEmpty(),
                modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                    .background(WasteBlue.copy(alpha = 0.56f), RoundedCornerShape(12.dp))
            ) {
                Text(
                    selectedLabel ?: "Pilih $title",
                    color = LoginInk,
                    fontSize = 11.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                choices.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(label(item), fontSize = 11.sp) },
                        onClick = { onSelected(item); expanded = false }
                    )
                }
            }
        }
    }
}

@Composable
private fun AdminUsersContent(
    users: List<AdminUser>,
    onToggleUser: (AdminUser) -> Unit
) {
    SectionTitle("Data warga", "Akun warga yang mengirim laporan melalui website")
    if (users.isEmpty()) EmptyAdminState("Belum ada warga terdaftar.")
    users.forEach { user ->
        AppCard(
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color.White,
            borderColor = AppBorder,
            accentColor = if (user.active) LoginAccent else WasteRed
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                UserAvatar(user.name)
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        user.name,
                        color = AppText,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text("@${user.username}", color = AppTextMuted, fontSize = 11.sp)
                    Text(
                        "${user.totalReports} laporan${if (user.email.isNotBlank()) "  •  ${user.email}" else ""}",
                        color = AppTextMuted,
                        fontSize = 11.sp
                    )
                }
                TextButton(
                    onClick = { onToggleUser(user) },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = if (user.active) WasteRed else LoginAccent
                    )
                ) {
                    Text(
                        if (user.active) "Nonaktifkan" else "Aktifkan",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun AdminStatsScreen(
    stats: AdminStatistics,
    dailyReports: List<DailyReport>,
    dailyRangeDays: Int,
    errorMessage: String,
    account: UserAccount,
    onDailyRange: (Int) -> Unit,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit
) {
    val periodCount = dailyReports.sumOf { it.totalWaste }
    val periodWeight = dailyReports.sumOf { it.weightKg }
    val periodB3 = dailyReports.sumOf { it.b3Alerts }
    val monthlyBars = stats.monthly.take(6).reversed()
    val maxMonthly = monthlyBars.maxOfOrNull { it.total }?.coerceAtLeast(1) ?: 1
    val maxTypeWeight = stats.byType.maxOfOrNull { it.weightKg }?.coerceAtLeast(1.0) ?: 1.0
    val chartColors =
        listOf(LoginAccent, WasteTeal, WastePurple, WasteOrange, WasteGreenDark, WasteNavy)

    AdminPage("stats", account, onNavigate, onLogout) {
        AdminSectionHeading(
            "ADMIN • STATISTIK",
            "Ringkasan dampak",
            "Lihat tren laporan, jenis sampah, dan pekerjaan yang selesai."
        )
        if (errorMessage.isNotBlank()) InlineError(errorMessage)
        SectionTitle("Rentang laporan selesai", "Pilih periode yang ingin dibandingkan")
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf(7, 30, 90).forEach { days ->
                TextButton(
                    onClick = { onDailyRange(days) },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = if (days == dailyRangeDays) LoginAccent else LoginMuted,
                        containerColor = if (days == dailyRangeDays) WasteBlue else AppSurface
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) { Text("$days hari", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SummaryCard(
                stats.completedReports.toString(),
                "Selesai total",
                WasteGreenDark,
                Modifier.weight(1f),
                icon = Icons.Filled.CheckCircle
            )
            SummaryCard(
                periodCount.toString(),
                "Selesai $dailyRangeDays hari",
                LoginAccent,
                Modifier.weight(1f),
                icon = Icons.AutoMirrored.Filled.Assignment
            )
        }
        AppCard(
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color.White,
            borderColor = AppBorder,
            accentColor = WasteTeal
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Berat selesai",
                        color = AppTextMuted,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        "${periodWeight.pretty()} kg",
                        color = AppText,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Dalam ${dailyRangeDays} hari terakhir",
                        color = AppTextMuted,
                        fontSize = 11.sp
                    )
                }
                if (periodB3 > 0) {
                    Text(
                        "$periodB3 laporan B3",
                        color = WasteRed,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    Text(
                        "Aman",
                        color = WasteGreenDark,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        SectionTitle("Laporan per bulan", "Perbandingan enam bulan terakhir")
        if (monthlyBars.isEmpty()) EmptyAdminState("Belum ada data bulanan.") else {
            AppCard(
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.White,
                borderColor = AppBorder
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 13.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        "Jumlah laporan",
                        color = AppTextMuted,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().height(138.dp),
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        monthlyBars.forEachIndexed { index, item ->
                            val ratio = item.total.toFloat() / maxMonthly
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Bottom
                            ) {
                                Text(
                                    item.total.toString(),
                                    color = AppTextMuted,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.58f)
                                        .height((22f + ratio * 67f).dp)
                                        .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                                        .background(chartColors[index % chartColors.size])
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                Text(
                                    shortMonth(item.period),
                                    color = AppTextMuted,
                                    fontSize = 11.sp,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
            }
        }

        SectionTitle("Komposisi jenis sampah", "Perbandingan berdasarkan berat tercatat")
        if (stats.byType.isEmpty()) EmptyAdminState("Belum ada data jenis sampah.") else {
            AppCard(
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.White,
                borderColor = AppBorder
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    stats.byType.take(6).forEachIndexed { index, item ->
                        val accent = chartColors[index % chartColors.size]
                        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    item.type,
                                    modifier = Modifier.weight(1f),
                                    color = AppText,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text("${item.total} laporan", color = AppTextMuted, fontSize = 11.sp)
                            }
                            DataProgress(item.weightKg.toFloat() / maxTypeWeight.toFloat(), accent)
                            Text(
                                "${item.weightKg.pretty()} kg",
                                color = accent,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        SectionTitle("Aktivitas harian", "Laporan selesai per tanggal")
        if (dailyReports.isEmpty()) {
            EmptyAdminState("Belum ada laporan selesai dalam rentang ini.")
        } else {
            AppCard(
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.White,
                borderColor = AppBorder
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 13.dp, vertical = 4.dp)
                ) {
                    dailyReports.take(10).forEach { item ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 9.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                item.date,
                                modifier = Modifier.weight(1f),
                                color = AppText,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                buildString {
                                    append("${item.totalWaste} laporan · ${item.weightKg.pretty()} kg")
                                    if (item.b3Alerts > 0) append(" · B3 ${item.b3Alerts}")
                                },
                                color = if (item.b3Alerts > 0) WasteRed else LoginAccent,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdminOperationsScreen(
    users: List<AdminUser>,
    workers: List<AdminWorker>,
    vehicles: List<WasteVehicle>,
    facilities: List<WasteFacility>,
    errorMessage: String,
    account: UserAccount,
    onCreateWorker: suspend (String, String, String, String, String, String) -> Unit,
    onUpdateWorker: suspend (AdminWorker, Boolean) -> Unit,
    onCreateVehicle: suspend (String, Double, String, String) -> Unit,
    onUpdateVehicle: suspend (WasteVehicle) -> Unit,
    onCreateFacility: suspend (String, String, Double) -> Unit,
    onUpdateFacility: suspend (WasteFacility) -> Unit,
    onToggleUser: (AdminUser) -> Unit,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit,
    initialTab: String = "petugas"
) {
    var tab by rememberSaveable(initialTab) { mutableStateOf(initialTab) }
    var adding by rememberSaveable { mutableStateOf(false) }
    var editingWorker by remember { mutableStateOf<AdminWorker?>(null) }
    var editingVehicle by remember { mutableStateOf<WasteVehicle?>(null) }
    var editingFacility by remember { mutableStateOf<WasteFacility?>(null) }
    var actionError by rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    AdminPage("ops", account, onNavigate, onLogout) {
        AdminSectionHeading(
            if (tab == "warga") "ADMIN • DATA WARGA" else "ADMIN • OPERASIONAL",
            if (tab == "warga") "Kelola akun warga" else "Tim dan fasilitas",
            if (tab == "warga") "Pantau akun warga dan tindak lanjuti akun bermasalah."
            else "Data ini dipakai saat laporan ditugaskan ke lapangan."
        )
        if (errorMessage.isNotBlank()) InlineError(errorMessage)
        if (actionError.isNotBlank()) InlineError(actionError)
        Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            listOf(
                "petugas" to "Petugas",
                "kendaraan" to "Kendaraan",
                "tpa" to "TPA",
                "warga" to "Warga"
            ).forEach { (key, label) ->
                TextButton(
                    onClick = { tab = key },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = if (tab == key) LoginAccent else LoginMuted,
                        containerColor = if (tab == key) WasteBlue else AppSurface
                    ),
                    shape = RoundedCornerShape(13.dp)
                ) { Text(label, fontSize = 11.sp, fontWeight = FontWeight.Bold) }
            }
        }
        when (tab) {
            "petugas" -> {
                SectionTitle(
                    "Data petugas",
                    "Akun Android dibuat oleh admin, tidak melalui registrasi umum"
                )
                if (account.isSuperAdmin) {
                    AppButton(
                        "＋ Daftarkan akun Admin/Petugas",
                        { adding = true },
                        containerColor = LoginAccent
                    )
                } else {
                    Text(
                        "Pendaftaran akun Admin/Petugas hanya dapat dilakukan oleh admin pertama.",
                        color = LoginMuted,
                        fontSize = 11.sp
                    )
                }
                if (workers.isEmpty()) EmptyAdminState("Belum ada data petugas.")
                workers.forEach { item ->
                    AppCard(
                        modifier = Modifier.fillMaxWidth(),
                        containerColor = Color.White,
                        borderColor = AppBorder,
                        accentColor = if (item.active && item.hasAccount) LoginAccent else WasteRed
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            UserAvatar(item.name)
                            Spacer(modifier = Modifier.width(9.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    item.name,
                                    color = AppText,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    if (item.hasAccount) "@${item.username} · ${item.zone.ifBlank { "Tanpa zona" }}"
                                    else "Belum ada akun Android · ${item.zone.ifBlank { "Tanpa zona" }}",
                                    color = if (item.hasAccount) AppTextMuted else WasteRed,
                                    fontSize = 11.sp
                                )
                                Text(
                                    "${item.totalPickups} penugasan selesai",
                                    color = LoginAccent,
                                    fontSize = 11.sp
                                )
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                TextButton(onClick = { editingWorker = item }) {
                                    Text(
                                        "Zona",
                                        color = LoginAccent,
                                        fontSize = 11.sp
                                    )
                                }
                                if (item.hasAccount) {
                                    TextButton(onClick = {
                                        scope.launch {
                                            actionError = ""
                                            try {
                                                onUpdateWorker(item, !item.active)
                                            } catch (cancelled: CancellationException) {
                                                throw cancelled
                                            } catch (error: Exception) {
                                                actionError = error.message
                                                    ?: "Status petugas gagal diperbarui."
                                            }
                                        }
                                    }) {
                                        Text(
                                            if (item.active) "Nonaktifkan" else "Aktifkan",
                                            color = if (item.active) WasteRed else LoginAccent,
                                            fontSize = 11.sp
                                        )
                                    }
                                } else {
                                    Text("Tidak bisa login", color = WasteRed, fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }

            "kendaraan" -> {
                SectionTitle("Data armada", "Kapasitas, muatan aktif, kondisi, dan rute")
                AppButton("＋ Tambah kendaraan", { adding = true }, containerColor = LoginAccent)
                if (vehicles.isEmpty()) EmptyAdminState("Belum ada kendaraan terdaftar.")
                vehicles.forEach { item ->
                    AppCard(
                        modifier = Modifier.fillMaxWidth(),
                        containerColor = Color.White,
                        borderColor = AppBorder,
                        accentColor = if (item.condition.equals(
                                "baik",
                                true
                            )
                        ) WasteTeal else WasteOrange
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    item.plate,
                                    color = AppText,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    "${item.loadKg.pretty()} / ${item.capacityKg.pretty()} kg " +
                                            "· ${item.reservedKg.pretty()} dicadangkan · ${item.condition}",
                                    color = AppTextMuted,
                                    fontSize = 11.sp
                                )
                                Text(
                                    "Rute: ${item.route.ifBlank { "Belum ditentukan" }}",
                                    color = LoginMuted,
                                    fontSize = 11.sp
                                )
                            }
                            TextButton(onClick = { editingVehicle = item }) {
                                Text(
                                    "Ubah",
                                    color = LoginAccent,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }
            }

            "tpa" -> {
                SectionTitle(
                    "Data TPA",
                    "Kapasitas terisi diperbarui saat Petugas menyelesaikan penugasan"
                )
                AppButton("＋ Tambah TPA", { adding = true }, containerColor = LoginAccent)
                if (facilities.isEmpty()) EmptyAdminState("Belum ada data TPA.")
                facilities.forEach { item ->
                    AppCard(
                        modifier = Modifier.fillMaxWidth(),
                        containerColor = Color.White,
                        borderColor = AppBorder,
                        accentColor = WastePurple
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        item.name,
                                        color = AppText,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        item.address.ifBlank { "Alamat belum diisi" },
                                        color = AppTextMuted,
                                        fontSize = 11.sp
                                    )
                                }
                                TextButton(onClick = { editingFacility = item }) {
                                    Text(
                                        "Ubah",
                                        color = LoginAccent,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                            Text(
                                "${item.filledTon.pretty()} ton terisi · " +
                                        "${item.reservedTon.pretty()} ton dicadangkan dari ${item.capacityTon.pretty()} ton",
                                color = LoginMuted,
                                fontSize = 11.sp
                            )
                            SoftProgress(
                                if (item.capacityTon > 0) ((item.filledTon + item.reservedTon) / item.capacityTon).toFloat() else 0f
                            )
                        }
                    }
                }
            }

            "warga" -> AdminUsersContent(users, onToggleUser)
        }
    }
    if (adding) {
        when (tab) {
            "petugas" -> StaffAccountFormDialog(
                onDismiss = { adding = false },
                onSave = { name, username, email, password, zone, role ->
                    onCreateWorker(
                        name,
                        username,
                        email,
                        password,
                        zone,
                        role
                    ); adding = false
                })

            "kendaraan" -> VehicleFormDialog(
                null,
                onDismiss = { adding = false },
                onSave = { plate, capacity, condition, route ->
                    onCreateVehicle(
                        plate,
                        capacity,
                        condition,
                        route
                    ); adding = false
                })

            else -> FacilityFormDialog(
                null,
                onDismiss = { adding = false },
                onSave = { name, address, capacity ->
                    onCreateFacility(
                        name,
                        address,
                        capacity
                    ); adding = false
                })
        }
    }
    editingWorker?.let { item ->
        SingleFieldDialog(
            "Ubah zona tugas",
            item.zone,
            "Zona tugas",
            { editingWorker = null }) { zone ->
            onUpdateWorker(item.copy(zone = zone), item.active); editingWorker = null
        }
    }
    editingVehicle?.let { item ->
        VehicleFormDialog(item, { editingVehicle = null }, { plate, capacity, condition, route ->
            onUpdateVehicle(
                item.copy(
                    plate = plate,
                    capacityKg = capacity,
                    condition = condition,
                    route = route
                )
            ); editingVehicle = null
        })
    }
    editingFacility?.let { item ->
        FacilityFormDialog(item, { editingFacility = null }, { name, address, capacity ->
            onUpdateFacility(
                item.copy(
                    name = name,
                    address = address,
                    capacityTon = capacity
                )
            ); editingFacility = null
        })
    }
}

@Composable
fun PetugasTasksScreen(
    tasks: List<WasteRecord>,
    errorMessage: String,
    account: UserAccount,
    onOpenPhoto: (String) -> Unit,
    onMarkTransported: (WasteRecord) -> Unit,
    onComplete: (WasteRecord) -> Unit,
    onLogout: () -> Unit
) {
    var selectedSection by rememberSaveable(account.username) { mutableStateOf("ringkasan") }
    val currentSection = selectedSection.takeIf {
        it in setOf("ringkasan", "tugas", "riwayat", "profil")
    } ?: "ringkasan"
    val activeTasks =
        tasks.filter { it.status == WasteFlow.ASSIGNED || it.status == WasteFlow.TRANSPORTED }
    val completedTasks = tasks.filter { it.status == WasteFlow.DONE }

    AdminPage(
        current = "tasks",
        account = account,
        onNavigate = {},
        onLogout = onLogout,
        bottomBar = { PetugasNavigationBar(currentSection) { selectedSection = it } },
        backgroundContent = { PetugasThemeBackdrop(Modifier.fillMaxSize()) }
    ) {
        if (currentSection != "profil") {
            PetugasJourneyHero(
                name = account.name,
                tasks = tasks,
                onOpenTasks = { selectedSection = "tugas" }
            )
            if (errorMessage.isNotBlank()) InlineError(errorMessage)
        }
        AnimatedContent(
            targetState = currentSection,
            modifier = Modifier.fillMaxWidth(),
            transitionSpec = {
                val sectionOrder = listOf("ringkasan", "tugas", "riwayat")
                val direction = if (sectionOrder.indexOf(targetState) >= sectionOrder.indexOf(initialState)) 1 else -1
                (slideInHorizontally(
                    animationSpec = tween(460, easing = FastOutSlowInEasing)
                ) { width -> width * direction * 34 / 100 } +
                    fadeIn(tween(340)) + scaleIn(
                    animationSpec = tween(420, easing = FastOutSlowInEasing),
                    initialScale = 0.94f
                )) togetherWith
                    (slideOutHorizontally(
                        animationSpec = tween(400, easing = FastOutSlowInEasing)
                    ) { width -> -width * direction * 22 / 100 } +
                        fadeOut(tween(260)) + scaleOut(
                        animationSpec = tween(380, easing = FastOutSlowInEasing),
                        targetScale = 0.97f
                    ))
            },
            label = "petugas-section-slide"
        ) { section ->
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                when (section) {
            "ringkasan" -> {
                PetugasOperationalOverview(tasks) { selectedSection = "tugas" }
                SectionTitle("Penugasan berikutnya", "Lanjutkan pekerjaan yang masih aktif")
                val nextTask = activeTasks.firstOrNull()
                if (nextTask == null) {
                    PetugasEmptyState("Belum ada tugas aktif", "Penugasan baru dari admin akan muncul di sini.")
                } else {
                    PetugasTaskCard(nextTask, onOpenPhoto, onMarkTransported, onComplete)
                    TextButton(
                        onClick = { selectedSection = "tugas" },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            "Lihat semua tugas aktif",
                            color = LoginAccent,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            "tugas" -> {
                SectionTitle(
                    "Tugas aktif",
                    "Perbarui status setelah kegiatan benar-benar dilakukan"
                )
                if (activeTasks.isEmpty()) PetugasEmptyState("Belum ada tugas aktif", "Penugasan baru akan muncul di bagian ini.")
                activeTasks.forEach { task ->
                    PetugasTaskCard(
                        task,
                        onOpenPhoto,
                        onMarkTransported,
                        onComplete
                    )
                }
            }

            "riwayat" -> {
                SectionTitle("Riwayat selesai", "Penugasan yang sudah diterima di TPA")
                if (completedTasks.isEmpty()) PetugasEmptyState("Belum ada riwayat", "Tugas yang sudah diterima di TPA akan tersimpan di sini.")
                completedTasks.forEach { task -> PetugasTaskCard(task, onOpenPhoto) }
            }

            "profil" -> AccountProfileSettings(account, onLogout)
                }
            }
        }
    }
}

@Composable
private fun PetugasThemeBackdrop(modifier: Modifier = Modifier) {
    val darkMode = LocalSmartWasteAppearance.current.darkMode
    val motion = rememberInfiniteTransition(label = "petugas-backdrop-motion")
    val ambientPhase by motion.animateFloat(
        initialValue = 0f,
        targetValue = 2f * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 18000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "petugas-ambient-phase"
    )
    val nightProgress by animateFloatAsState(
        targetValue = if (darkMode) 1f else 0f,
        animationSpec = tween(durationMillis = 760, easing = FastOutSlowInEasing),
        label = "petugas-theme-progress"
    )

    Canvas(modifier) {
            val width = size.width
            val height = size.height
            val breeze = sin(ambientPhase.toDouble()).toFloat()
            val drift = breeze * 12.dp.toPx()
            val dayProgress = 1f - nightProgress
            val sky = listOf(
                lerp(Color(0xFFC5DDE5), Color(0xFF111C2A), nightProgress),
                lerp(Color(0xFFE8E3D5), Color(0xFF18302F), nightProgress),
                lerp(Color(0xFFC1D3BF), Color(0xFF10221F), nightProgress)
            )
            drawRect(Brush.verticalGradient(sky), size = size)
            val nightPhase = ambientPhase

            run {
                val glowPulse = 0.5f + 0.5f * sin(nightPhase.toDouble()).toFloat()
                val moon = Offset(width * 0.81f + drift * 0.12f, height * 0.17f)
                val glowRadius = width * (0.34f + glowPulse * 0.03f)
                drawCircle(
                    Brush.radialGradient(
                        listOf(
                            Color(0xFFDDE8C9).copy(alpha = (0.22f + glowPulse * 0.06f) * nightProgress),
                            Color(0xFF8EB9A7).copy(alpha = 0.10f * nightProgress),
                            Color.Transparent
                        ),
                        center = moon,
                        radius = glowRadius
                    ),
                    radius = glowRadius,
                    center = moon
                )
                val moonRadius = width * 0.068f
                val crescent = Path().apply {
                    moveTo(moon.x + moonRadius * 0.72f, moon.y - moonRadius * 0.68f)
                    cubicTo(
                        moon.x - moonRadius * 0.18f, moon.y - moonRadius * 1.04f,
                        moon.x - moonRadius * 0.88f, moon.y - moonRadius * 0.38f,
                        moon.x - moonRadius * 0.69f, moon.y + moonRadius * 0.34f
                    )
                    cubicTo(
                        moon.x - moonRadius * 0.48f, moon.y + moonRadius * 1.04f,
                        moon.x + moonRadius * 0.34f, moon.y + moonRadius * 0.96f,
                        moon.x + moonRadius * 0.73f, moon.y + moonRadius * 0.38f
                    )
                    cubicTo(
                        moon.x + moonRadius * 0.34f, moon.y + moonRadius * 0.56f,
                        moon.x + moonRadius * 0.02f, moon.y + moonRadius * 0.27f,
                        moon.x + moonRadius * 0.03f, moon.y - moonRadius * 0.10f
                    )
                    cubicTo(
                        moon.x + moonRadius * 0.04f, moon.y - moonRadius * 0.40f,
                        moon.x + moonRadius * 0.31f, moon.y - moonRadius * 0.61f,
                        moon.x + moonRadius * 0.72f, moon.y - moonRadius * 0.68f
                    )
                    close()
                }
                drawPath(
                    crescent,
                    Brush.linearGradient(
                        listOf(
                            Color(0xFFF0F1DA).copy(alpha = nightProgress),
                            Color(0xFFC8D8BD).copy(alpha = nightProgress),
                            Color(0xFFAFC7B0).copy(alpha = nightProgress)
                        ),
                        start = Offset(moon.x - moonRadius, moon.y - moonRadius),
                        end = Offset(moon.x + moonRadius, moon.y + moonRadius)
                    )
                )
                listOf(
                    Offset(width * 0.13f, height * 0.14f),
                    Offset(width * 0.31f, height * 0.22f),
                    Offset(width * 0.48f, height * 0.12f),
                    Offset(width * 0.61f, height * 0.32f),
                    Offset(width * 0.22f, height * 0.43f),
                    Offset(width * 0.73f, height * 0.42f)
                ).forEachIndexed { index, star ->
                    val twinkle = 0.28f + 0.28f * (
                        0.5f + 0.5f * sin((nightPhase + index * 1.3f).toDouble()).toFloat()
                    )
                    drawCircle(Color(0xFFE5EEDC).copy(alpha = twinkle * nightProgress), (1.2f + index % 2 * 0.45f).dp.toPx(), star)
                }
                val horizonHaze = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color(0xFF8EB9A7).copy(alpha = (0.08f + glowPulse * 0.035f) * nightProgress),
                        Color.Transparent
                    ),
                    startY = height * 0.43f,
                    endY = height * 0.82f
                )
                drawRect(horizonHaze, topLeft = Offset(0f, height * 0.43f), size = Size(width, height * 0.39f))
            }
            run {
                val morningGlow = Offset(width * 0.78f + drift * 0.12f, height * 0.22f)
                drawCircle(
                    Brush.radialGradient(
                        listOf(Color(0xFFF1E6CA).copy(alpha = 0.36f * dayProgress), Color.Transparent),
                        center = morningGlow,
                        radius = width * 0.36f
                    ),
                    radius = width * 0.36f,
                    center = morningGlow
                )
                // Soft morning clouds and a quiet breeze echo the login screen's light theme.
                val cloud = Path().apply {
                    moveTo(-width * 0.05f + drift, height * 0.21f)
                    cubicTo(width * 0.12f + drift, height * 0.14f, width * 0.25f + drift, height * 0.27f, width * 0.40f + drift, height * 0.19f)
                    cubicTo(width * 0.51f + drift, height * 0.14f, width * 0.61f + drift, height * 0.20f, width * 0.71f + drift, height * 0.17f)
                    lineTo(width * 0.71f + drift, height * 0.23f)
                    cubicTo(width * 0.47f + drift, height * 0.29f, width * 0.19f + drift, height * 0.25f, -width * 0.05f + drift, height * 0.27f)
                    close()
                }
                drawPath(cloud, Color(0xFFF5F4E8).copy(alpha = 0.28f * dayProgress))
            }

            // Wetland plants and a narrow stream keep the lower scene environmental
            // without repeating the hills and tree silhouettes from the login screen.
            val bank = Path().apply {
                moveTo(0f, height * 0.81f)
                cubicTo(width * 0.20f, height * 0.79f, width * 0.31f, height * 0.83f, width * 0.49f, height * 0.81f)
                cubicTo(width * 0.67f, height * 0.79f, width * 0.84f, height * 0.83f, width, height * 0.80f)
                lineTo(width, height)
                lineTo(0f, height)
                close()
            }
            drawPath(
                bank,
                lerp(
                    Color(0xFFAAC6AF).copy(alpha = 0.58f),
                    Color(0xFF203A31).copy(alpha = 0.82f),
                    nightProgress
                )
            )

            val water = Path().apply {
                moveTo(0f, height * 0.88f)
                cubicTo(width * 0.18f, height * 0.83f, width * 0.33f, height * 0.92f, width * 0.51f, height * 0.87f)
                cubicTo(width * 0.70f, height * 0.82f, width * 0.84f, height * 0.90f, width, height * 0.85f)
                lineTo(width, height)
                lineTo(0f, height)
                close()
            }
            val waterColors = listOf(
                lerp(Color(0xFFB4D2CF).copy(alpha = 0.66f), Color(0xFF2B5551).copy(alpha = 0.84f), nightProgress),
                lerp(Color(0xFF8FB8B5).copy(alpha = 0.60f), Color(0xFF142B2B).copy(alpha = 0.94f), nightProgress)
            )
            drawPath(water, Brush.verticalGradient(waterColors, startY = height * 0.83f, endY = height))
            val waterEdge = Path().apply {
                moveTo(0f, height * 0.88f)
                cubicTo(width * 0.18f, height * 0.83f, width * 0.33f, height * 0.92f, width * 0.51f, height * 0.87f)
                cubicTo(width * 0.70f, height * 0.82f, width * 0.84f, height * 0.90f, width, height * 0.85f)
            }
            drawPath(
                waterEdge,
                lerp(
                    Color(0xFFE6EEE0).copy(alpha = 0.48f),
                    Color(0xFFB1D1C3).copy(alpha = 0.40f),
                    nightProgress
                ),
                style = Stroke(width = 1.4.dp.toPx(), cap = StrokeCap.Round)
            )

            val waterPhase = ambientPhase
            val reflectionPulse = 0.5f + 0.5f * sin(waterPhase.toDouble()).toFloat()
            val reflectionAlpha = 0.12f + (0.05f + 0.02f * nightProgress) * reflectionPulse
            listOf(
                Triple(0.67f, 0.875f, 0.09f),
                Triple(0.76f, 0.91f, 0.12f),
                Triple(0.62f, 0.94f, 0.10f),
                Triple(0.82f, 0.96f, 0.07f)
            ).forEachIndexed { index, (x, y, length) ->
                val rippleShift = sin((waterPhase + index * 1.6f).toDouble()).toFloat() * 0.018f
                val rippleX = (x + rippleShift).coerceIn(0.02f, 0.96f - length)
                val ripple = Path().apply {
                    moveTo(width * rippleX, height * y)
                    cubicTo(
                        width * (rippleX + length * 0.3f), height * (y - 0.004f),
                        width * (rippleX + length * 0.65f), height * (y + 0.004f),
                        width * (rippleX + length), height * y
                    )
                }
                val reflectionColor = lerp(Color(0xFFF5F0D9), Color(0xFFD6E8D5), nightProgress)
                drawPath(
                    ripple,
                    reflectionColor.copy(alpha = reflectionAlpha * (1f - index * 0.12f)),
                    style = Stroke(width = (1.1f - index * 0.1f).dp.toPx(), cap = StrokeCap.Round)
                )
            }

            val lilyColor = lerp(
                Color(0xFF478565).copy(alpha = 0.56f),
                Color(0xFF91B59B).copy(alpha = 0.68f),
                nightProgress
            )
            val waterCut = lerp(
                Color(0xFFB4D2CF).copy(alpha = 0.65f),
                Color(0xFF2B5551).copy(alpha = 0.82f),
                nightProgress
            )
            val lilyPads = listOf(
                Triple(0.16f, 0.862f, 0.76f),
                Triple(0.39f, 0.878f, 1.00f),
                Triple(0.65f, 0.847f, 0.88f),
                Triple(0.85f, 0.862f, 0.70f)
            )
            lilyPads.forEachIndexed { index, (xFraction, yFraction, scale) ->
                val gentleBob = sin((waterPhase + index * 1.15f).toDouble()).toFloat() * 2.2.dp.toPx()
                val center = Offset(width * xFraction, height * yFraction + gentleBob)
                val radiusX = width * 0.030f * scale
                val radiusY = height * 0.011f * scale
                val pad = Path().apply {
                    moveTo(center.x, center.y)
                    cubicTo(
                        center.x + radiusX * 0.20f, center.y - radiusY * 0.70f,
                        center.x + radiusX * 0.86f, center.y - radiusY * 0.62f,
                        center.x + radiusX, center.y
                    )
                    cubicTo(
                        center.x + radiusX * 0.86f, center.y + radiusY * 0.95f,
                        center.x - radiusX * 0.86f, center.y + radiusY * 0.95f,
                        center.x - radiusX, center.y
                    )
                    cubicTo(
                        center.x - radiusX * 0.86f, center.y - radiusY * 0.62f,
                        center.x - radiusX * 0.20f, center.y - radiusY * 0.70f,
                        center.x, center.y
                    )
                    close()
                }
                drawPath(pad, lilyColor)
                drawLine(
                    waterCut,
                    center,
                    Offset(center.x + radiusX * 0.25f, center.y - radiusY * 0.36f),
                    strokeWidth = 1.15.dp.toPx()
                )
                drawLine(
                    lilyColor.copy(alpha = 0.72f),
                    Offset(center.x - radiusX * 0.72f, center.y + radiusY * 0.36f),
                    Offset(center.x, center.y + radiusY * 0.78f),
                    strokeWidth = 0.8.dp.toPx()
                )
            }

            val flowerColor = lerp(
                Color(0xFFF1E9D6).copy(alpha = 0.94f),
                Color(0xFFD9D8C2).copy(alpha = 0.86f),
                nightProgress
            )
            val flowerCenterColor = lerp(
                Color(0xFFC2A46B).copy(alpha = 0.88f),
                Color(0xFFC2AE7D).copy(alpha = 0.82f),
                nightProgress
            )
            listOf(0.28f to 0.851f, 0.73f to 0.842f).forEachIndexed { index, (xFraction, yFraction) ->
                val flowerBob = sin((waterPhase + index * 1.8f).toDouble()).toFloat() * 1.5.dp.toPx()
                val flower = Offset(width * xFraction, height * yFraction + flowerBob)
                val petalRadius = 4.dp.toPx()
                val petalDistance = 4.dp.toPx()
                repeat(6) { petal ->
                    val angle = (petal * 60f - 90f) * PI.toFloat() / 180f
                    val petalCenter = Offset(
                        flower.x + cos(angle) * petalDistance,
                        flower.y + sin(angle) * petalDistance
                    )
                    drawOval(
                        flowerColor,
                        topLeft = Offset(petalCenter.x - petalRadius, petalCenter.y - petalRadius * 0.72f),
                        size = Size(petalRadius * 2f, petalRadius * 1.44f)
                    )
                }
                drawCircle(flowerCenterColor, 1.7.dp.toPx(), flower)
            }
            if (nightProgress > 0.01f) {
                listOf(
                    Offset(width * 0.22f + drift * 0.4f, height * 0.70f),
                    Offset(width * 0.76f - drift * 0.3f, height * 0.75f),
                    Offset(width * 0.48f + drift * 0.25f, height * 0.81f)
                ).forEachIndexed { index, firefly ->
                    val shimmer = 0.22f + 0.20f * (
                        0.5f + 0.5f * sin((nightPhase + index * 2f).toDouble()).toFloat()
                    )
                    val fireflyGlow = width * 0.035f
                    drawCircle(
                        Brush.radialGradient(
                            listOf(Color(0xFFC8D8A2).copy(alpha = shimmer * 0.55f * nightProgress), Color.Transparent),
                            center = firefly,
                            radius = fireflyGlow
                        ),
                        radius = fireflyGlow,
                        center = firefly
                    )
                    drawCircle(Color(0xFFD5DDAA).copy(alpha = shimmer * nightProgress), 1.4.dp.toPx(), firefly)
                }
            }
    }
}

@Composable
private fun PetugasJourneyHero(
    name: String,
    tasks: List<WasteRecord>,
    onOpenTasks: () -> Unit
) {
    val isDark = LocalSmartWasteAppearance.current.darkMode
    val assigned = tasks.count { it.status == WasteFlow.ASSIGNED }
    val transported = tasks.count { it.status == WasteFlow.TRANSPORTED }
    val completed = tasks.count { it.status == WasteFlow.DONE }
    val total = tasks.size
    val active = assigned + transported
    val heroStart = if (isDark) Color(0xFF142D36) else Color(0xFF2F704F)
    val heroEnd = if (isDark) Color(0xFF205E45) else Color(0xFF3D8961)
    val heroText = Color(0xFFF4F3E8)
    val heroMuted = Color(0xFFDCE9DC)
    val completion = if (total == 0) 0f else completed.toFloat() / total
    val shape = RoundedCornerShape(26.dp)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(Brush.linearGradient(listOf(heroStart, heroEnd)))
            .clickable(onClick = onOpenTasks)
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Canvas(Modifier.fillMaxWidth().height(116.dp)) {
            val glowCenter = Offset(size.width * 0.96f, size.height * 0.04f)
            val glowRadius = size.width * 0.48f
            drawCircle(
                Brush.radialGradient(
                    listOf(Color.White.copy(alpha = if (isDark) 0.11f else 0.12f), Color.Transparent),
                    center = glowCenter,
                    radius = glowRadius
                ),
                radius = glowRadius,
                center = glowCenter
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(36.dp).clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.LocalShipping, contentDescription = null, tint = heroText, modifier = Modifier.size(19.dp))
                }
                Spacer(Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        "SMART WASTE  ·  PETUGAS",
                        color = heroMuted,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.65.sp
                    )
                    Text(
                        "Hai, $name",
                        color = heroText,
                        fontSize = 18.sp,
                        lineHeight = 22.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    "$active aktif",
                    color = heroText,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.15f))
                        .padding(horizontal = 11.dp, vertical = 7.dp)
                )
            }
            Text(
                when {
                    active == 0 -> "Jadwal lapanganmu sedang kosong."
                    transported > 0 -> "$transported muatan sedang dibawa menuju TPA."
                    else -> "$assigned penugasan menunggu untuk mulai diangkut."
                },
                color = heroMuted,
                fontSize = 11.sp,
                lineHeight = 15.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Canvas(Modifier.fillMaxWidth().height(8.dp)) {
                val centerY = size.height / 2f
                val start = 2.dp.toPx()
                val end = size.width - start
                drawLine(Color.White.copy(alpha = 0.34f), Offset(start, centerY), Offset(end, centerY), 2.dp.toPx())
                if (completion > 0f) {
                    val marker = start + (end - start) * completion
                    drawLine(Color.White.copy(alpha = 0.94f), Offset(start, centerY), Offset(marker, centerY), 2.dp.toPx())
                    drawCircle(Color.White, 4.dp.toPx(), Offset(marker, centerY))
                } else {
                    drawCircle(Color.White.copy(alpha = 0.70f), 3.dp.toPx(), Offset(start, centerY))
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("$assigned ditugaskan  ·  $transported dalam rute", color = heroMuted, fontSize = 9.sp)
                Spacer(Modifier.weight(1f))
                Text("$completed/$total selesai", color = heroText, fontSize = 9.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
private fun PetugasOperationalOverview(
    tasks: List<WasteRecord>,
    onOpenTasks: () -> Unit
) {
    val isDark = LocalSmartWasteAppearance.current.darkMode
    val activeTasks = tasks.filter {
        it.status == WasteFlow.ASSIGNED || it.status == WasteFlow.TRANSPORTED
    }
    val activeWeight = activeTasks.sumOf { it.weightKg }
    val activeTypes = activeTasks.map { it.type.trim() }.filter(String::isNotBlank).distinct().size
    val specialHandling = activeTasks.count { !it.hazardLevel.equals("RENDAH", ignoreCase = true) }
    val destination = activeTasks.firstOrNull { it.tpaName.isNotBlank() }?.tpaName
    val accent = if (isDark) Color(0xFFBFDBC6) else Color(0xFF397252)
    val shape = RoundedCornerShape(24.dp)
    val cardStart = if (isDark) Color(0xFF192B33) else AppSurface
    val cardEnd = if (isDark) Color(0xFF29473B) else Color(0xFFE8F1E4)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(Brush.linearGradient(listOf(cardStart, cardEnd)))
            .clickable(onClick = onOpenTasks)
            .padding(horizontal = 15.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(11.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(1.dp)) {
                Text("HARI INI", color = accent, fontSize = 9.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.8.sp)
                Text("Ringkasan muatan", color = AppText, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
            }
            Text("Lihat tugas  ›", color = accent, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            PetugasOverviewCell(activeWeight.pretty(), "KG MUATAN", Icons.Filled.LocalShipping, accent, Modifier.weight(1f))
            PetugasOverviewCell(activeTypes.toString(), "JENIS", Icons.Filled.Inbox, accent, Modifier.weight(1f))
            PetugasOverviewCell(specialHandling.toString(), "PERLU PERHATIAN", Icons.Filled.ReportProblem, accent, Modifier.weight(1f))
        }

        Row(
            modifier = Modifier.fillMaxWidth().clip(CircleShape)
                .background(accent.copy(alpha = if (isDark) 0.15f else 0.10f))
                .padding(horizontal = 10.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            Icon(Icons.Filled.Place, contentDescription = null, tint = accent, modifier = Modifier.size(14.dp))
            Text("TUJUAN", color = AppTextMuted, fontSize = 9.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.35.sp)
            Text(
                destination ?: "Belum ditentukan",
                modifier = Modifier.weight(1f),
                color = AppText,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun PetugasOverviewCell(
    value: String,
    label: String,
    icon: ImageVector,
    accent: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 5.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier.size(29.dp).clip(CircleShape)
                .background(accent.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = accent, modifier = Modifier.size(15.dp))
        }
        Text(value, color = AppText, fontSize = 18.sp, fontWeight = FontWeight.Bold, maxLines = 1)
        Text(
            label,
            color = AppTextMuted,
            fontSize = 8.sp,
            lineHeight = 10.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun PetugasNavigationBar(selectedSection: String, onSectionSelected: (String) -> Unit) {
    val destinations = listOf(
        Triple("ringkasan", "Beranda", Icons.Rounded.Home),
        Triple("tugas", "Tugas", Icons.Rounded.TaskAlt),
        Triple("riwayat", "Riwayat", Icons.Rounded.History),
        Triple("profil", "Profil", Icons.Rounded.Person)
    )
    FloatingRoleNavigationBar(selectedSection, destinations, onSectionSelected)
}

@Composable
private fun AccountProfileSettings(account: UserAccount, onLogout: () -> Unit) {
    val appearance = LocalSmartWasteAppearance.current
    val isDark = appearance.darkMode
    val cardShape = RoundedCornerShape(24.dp)
    val surface = AppSurface.copy(alpha = if (isDark) 0.88f else 0.92f)

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        AdminSectionHeading(
            eyebrow = "AKUN DAN PREFERENSI",
            title = "Profil ${account.role.label.lowercase()}",
            subtitle = "Kelola tampilan aplikasi dan lihat informasi akun yang sedang digunakan."
        )

        Column(
            modifier = Modifier.fillMaxWidth()
                .clip(cardShape)
                .background(
                    Brush.horizontalGradient(
                        if (isDark) listOf(Color(0xFF213A36), Color(0xFF26363D))
                        else listOf(Color(0xFFE5EEE1), Color(0xFFF1EBDD))
                    )
                )
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                UserAvatar(account.name, Modifier.size(58.dp))
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        account.name,
                        color = AppText,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        account.role.description,
                        color = AppTextMuted,
                        fontSize = 13.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            ProfileInfoRow("Username", "@${account.username}")
            ProfileInfoRow("Peran", account.role.label)
        }

        Column(
            modifier = Modifier.fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(surface)
                .clickable(onClick = appearance.onToggleTheme)
                .padding(horizontal = 16.dp, vertical = 15.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Icon(
                    imageVector = if (isDark) Icons.Rounded.NightsStay else Icons.Rounded.WbSunny,
                    contentDescription = null,
                    tint = LoginAccent,
                    modifier = Modifier.size(22.dp)
                )
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text("Tampilan aplikasi", color = AppText, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    Text(if (isDark) "Mode malam aktif" else "Mode terang aktif", color = AppTextMuted, fontSize = 12.sp)
                }
                TextButton(onClick = appearance.onToggleTheme) {
                    Text(if (isDark) "Terang" else "Malam", color = LoginAccent, fontWeight = FontWeight.Bold)
                }
            }
        }

        TextButton(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(if (isDark) Color(0xFF3A2927) else Color(0xFFF3E8E3))
                .padding(vertical = 5.dp)
        ) {
            Icon(Icons.Filled.SwitchAccount, contentDescription = null, tint = WasteRed)
            Spacer(Modifier.width(8.dp))
            Text("Keluar dari akun", color = WasteRed, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun ProfileInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = AppTextMuted, fontSize = 13.sp)
        Text(value, color = AppText, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun PetugasTaskCard(
    report: WasteRecord,
    onOpenPhoto: (String) -> Unit,
    onMarkTransported: ((WasteRecord) -> Unit)? = null,
    onComplete: ((WasteRecord) -> Unit)? = null
) {
    val isComplete = report.status == WasteFlow.DONE
    val isDark = LocalSmartWasteAppearance.current.darkMode
    val statusAccent = when (report.status) {
        WasteFlow.DONE -> if (isDark) Color(0xFF91C59B) else WasteGreen
        WasteFlow.TRANSPORTED -> if (isDark) Color(0xFF82C7B9) else WasteTeal
        else -> if (isDark) Color(0xFFC2CF91) else LoginAccent
    }
    val shape = RoundedCornerShape(22.dp)
    val cardStart = if (isDark) Color(0xFF192B34) else AppSurface
    val cardEnd = if (isDark) Color(0xFF244638) else statusAccent.copy(alpha = 0.08f)
    val markerIcon = when (report.status) {
        WasteFlow.DONE -> Icons.Rounded.TaskAlt
        WasteFlow.TRANSPORTED -> Icons.Filled.LocalShipping
        else -> Icons.AutoMirrored.Filled.Assignment
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(Brush.linearGradient(listOf(cardStart, cardEnd)))
    ) {
        Canvas(Modifier.fillMaxWidth().height(82.dp)) {
            val glowRadius = size.width * 0.32f
            drawCircle(
                Brush.radialGradient(
                    listOf(statusAccent.copy(alpha = if (isDark) 0.13f else 0.10f), Color.Transparent),
                    center = Offset(size.width, 0f),
                    radius = glowRadius
                ),
                radius = glowRadius,
                center = Offset(size.width, 0f)
            )
        }
        Column(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 13.dp),
            verticalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(statusAccent.copy(alpha = if (isDark) 0.20f else 0.14f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(markerIcon, contentDescription = null, tint = statusAccent, modifier = Modifier.size(18.dp))
                }
                Spacer(Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(1.dp)) {
                    Text(report.code, color = AppText, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    Text("${report.type}  ·  ${report.weightKg.pretty()} kg", color = AppTextMuted, fontSize = 10.sp)
                }
                StatusPill(report.status, statusAccent)
            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                Box(
                    modifier = Modifier.size(25.dp).clip(CircleShape)
                        .background(statusAccent.copy(alpha = 0.11f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Place, contentDescription = null, tint = statusAccent, modifier = Modifier.size(13.dp))
                }
                Text(
                    report.location,
                    color = AppText,
                    fontSize = 10.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (!isComplete) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                    Box(
                        modifier = Modifier.size(25.dp).clip(CircleShape)
                            .background(statusAccent.copy(alpha = 0.09f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.LocalShipping, contentDescription = null, tint = AppTextMuted, modifier = Modifier.size(13.dp))
                    }
                    Text(
                        "${report.vehiclePlate.ifBlank { "Armada —" }}   /   ${report.tpaName.ifBlank { "TPA belum ditentukan" }}",
                        color = AppTextMuted,
                        fontSize = 10.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                if (!report.hazardLevel.equals("RENDAH", true)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                        Icon(Icons.Filled.ReportProblem, contentDescription = null, tint = WasteRed, modifier = Modifier.size(14.dp))
                        Text("Perlu penanganan khusus · ${report.hazardLevel}", color = WasteRed, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (!report.photoUrl.isNullOrBlank()) {
                    TextButton(onClick = { onOpenPhoto(report.photoUrl) }) {
                        Text("Lihat foto", color = statusAccent, fontSize = 10.sp)
                    }
                }
                Spacer(Modifier.weight(1f))
                when {
                    report.status == WasteFlow.ASSIGNED && onMarkTransported != null ->
                        AppButton(
                            "Mulai angkut",
                            { onMarkTransported(report) },
                            modifier = Modifier.widthIn(min = 132.dp, max = 172.dp).height(42.dp),
                            containerColor = LoginAccent
                        )

                    report.status == WasteFlow.TRANSPORTED && onComplete != null ->
                        AppButton(
                            "Tandai selesai",
                            { onComplete(report) },
                            modifier = Modifier.widthIn(min = 132.dp, max = 172.dp).height(42.dp),
                            containerColor = WasteGreenDark
                        )
                }
            }
        }
    }
}

@Composable
private fun PetugasEmptyState(title: String, message: String) {
    val isDark = LocalSmartWasteAppearance.current.darkMode
    val accent = if (isDark) Color(0xFFA7D1B3) else Color(0xFF397252)
    val shape = RoundedCornerShape(22.dp)
    val start = if (isDark) Color(0xFF192B34) else AppSurface
    val end = if (isDark) Color(0xFF244638) else Color(0xFFE8F1E4)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(Brush.linearGradient(listOf(start, end)))
            .padding(horizontal = 15.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier.size(38.dp).clip(CircleShape).background(accent.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.LocalShipping, contentDescription = null, tint = accent, modifier = Modifier.size(19.dp))
        }
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(title, color = AppText, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            Text(message, color = AppTextMuted, fontSize = 10.sp, lineHeight = 14.sp)
        }
    }
}

@Composable
private fun StaffAccountFormDialog(
    onDismiss: () -> Unit,
    onSave: suspend (String, String, String, String, String, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var zone by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("petugas") }
    var saving by remember { mutableStateOf(false) }
    var saveError by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Daftarkan akun staf", color = LoginInk, fontWeight = FontWeight.Bold) },
        text = {
            ScrollableDialogContent {
                Text(
                    "Akun staf masuk memakai username dan kata sandi yang dibuat di sini.",
                    color = LoginMuted,
                    fontSize = 11.sp
                )
                SelectionField(
                    "Role akun",
                    role,
                    listOf("admin", "petugas"),
                    { if (it == "admin") "Admin" else "Petugas" }) { role = it }
                FormField(name, { name = it }, "Nama lengkap")
                FormField(username, { username = it }, "Username")
                FormField(email, { email = it }, "Email akun")
                FormField(password, { password = it }, "Password awal (minimal 5 karakter)")
                if (role == "petugas") FormField(zone, { zone = it }, "Zona tugas")
                if (saveError.isNotBlank()) InlineError(saveError)
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (!saving) scope.launch {
                        saving = true
                        saveError = ""
                        try {
                            onSave(
                                name.trim(),
                                username.trim(),
                                email.trim(),
                                password,
                                zone.trim(),
                                role
                            )
                        } catch (cancelled: CancellationException) {
                            throw cancelled
                        } catch (error: Exception) {
                            saveError = error.message ?: "Akun staf gagal dibuat."
                        } finally {
                            saving = false
                        }
                    }
                },
                enabled = name.isNotBlank() &&
                        username.trim().length >= 3 &&
                        email.trim().matches(Regex("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) &&
                        password.length >= 5 &&
                        (role == "admin" || zone.isNotBlank()) &&
                        !saving
            ) { Text(if (saving) "Menyimpan…" else "Simpan", color = LoginAccent) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !saving) {
                Text(
                    "Batal",
                    color = LoginMuted
                )
            }
        },
        containerColor = AppSurface
    )
}

@Composable
private fun VehicleFormDialog(
    vehicle: WasteVehicle?,
    onDismiss: () -> Unit,
    onSave: suspend (String, Double, String, String) -> Unit
) {
    var plate by remember(vehicle?.id) { mutableStateOf(vehicle?.plate.orEmpty()) }
    var capacity by remember(vehicle?.id) {
        mutableStateOf(
            vehicle?.capacityKg?.toString().orEmpty()
        )
    }
    var condition by remember(vehicle?.id) { mutableStateOf(vehicle?.condition ?: "Baik") }
    var route by remember(vehicle?.id) { mutableStateOf(vehicle?.route.orEmpty()) }
    var saving by remember(vehicle?.id) { mutableStateOf(false) }
    var saveError by remember(vehicle?.id) { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                if (vehicle == null) "Tambah kendaraan" else "Ubah kendaraan",
                color = LoginInk,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            ScrollableDialogContent {
                FormField(plate, { plate = it }, "Nomor polisi")
                FormField(capacity, { capacity = it }, "Kapasitas (kg)")
                SelectionField(
                    "Kondisi kendaraan",
                    condition,
                    listOf("Baik", "Servis"),
                    { it }) { condition = it }
                FormField(route, { route = it }, "Rute")
                if (vehicle != null) Text(
                    "Terpakai ${vehicle.loadKg.pretty()} kg · dicadangkan ${vehicle.reservedKg.pretty()} kg",
                    color = LoginMuted,
                    fontSize = 11.sp
                )
                if (saveError.isNotBlank()) InlineError(saveError)
            }
        },
        confirmButton = {
            val proposedCapacity = capacity.toDoubleOrNull() ?: 0.0
            val canFitCurrentLoad =
                vehicle == null || proposedCapacity >= vehicle.loadKg + vehicle.reservedKg
            val canChangeCondition = vehicle == null || condition.equals(
                "baik",
                true
            ) || (vehicle.loadKg == 0.0 && vehicle.reservedKg == 0.0)
            TextButton(
                onClick = {
                    if (!saving) scope.launch {
                        saving = true
                        saveError = ""
                        try {
                            onSave(plate.trim(), proposedCapacity, condition.trim(), route.trim())
                        } catch (cancelled: CancellationException) {
                            throw cancelled
                        } catch (error: Exception) {
                            saveError = error.message ?: "Data kendaraan gagal disimpan."
                        } finally {
                            saving = false
                        }
                    }
                },
                enabled = plate.isNotBlank() && proposedCapacity > 0 && canFitCurrentLoad && canChangeCondition && !saving
            ) { Text(if (saving) "Menyimpan…" else "Simpan", color = LoginAccent) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !saving) {
                Text(
                    "Batal",
                    color = LoginMuted
                )
            }
        },
        containerColor = AppSurface
    )
}

@Composable
private fun FacilityFormDialog(
    facility: WasteFacility?,
    onDismiss: () -> Unit,
    onSave: suspend (String, String, Double) -> Unit
) {
    var name by remember(facility?.id) { mutableStateOf(facility?.name.orEmpty()) }
    var address by remember(facility?.id) { mutableStateOf(facility?.address.orEmpty()) }
    var capacity by remember(facility?.id) {
        mutableStateOf(
            facility?.capacityTon?.toString().orEmpty()
        )
    }
    var saving by remember(facility?.id) { mutableStateOf(false) }
    var saveError by remember(facility?.id) { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                if (facility == null) "Tambah TPA" else "Ubah TPA",
                color = LoginInk,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            ScrollableDialogContent {
                FormField(name, { name = it }, "Nama TPA")
                FormField(address, { address = it }, "Alamat")
                FormField(capacity, { capacity = it }, "Kapasitas (ton)")
                if (facility != null) Text(
                    "Terisi: ${facility.filledTon.pretty()} ton",
                    color = LoginMuted,
                    fontSize = 11.sp
                )
                if (saveError.isNotBlank()) InlineError(saveError)
            }
        },
        confirmButton = {
            val proposedCapacity = capacity.toDoubleOrNull() ?: 0.0
            val minimumCapacity = (facility?.filledTon ?: 0.0) + (facility?.reservedTon ?: 0.0)
            TextButton(
                onClick = {
                    if (!saving) scope.launch {
                        saving = true
                        saveError = ""
                        try {
                            onSave(name.trim(), address.trim(), proposedCapacity)
                        } catch (cancelled: CancellationException) {
                            throw cancelled
                        } catch (error: Exception) {
                            saveError = error.message ?: "Data TPA gagal disimpan."
                        } finally {
                            saving = false
                        }
                    }
                },
                enabled = name.isNotBlank() && proposedCapacity >= minimumCapacity && proposedCapacity > 0 && !saving
            ) { Text(if (saving) "Menyimpan…" else "Simpan", color = LoginAccent) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !saving) {
                Text(
                    "Batal",
                    color = LoginMuted
                )
            }
        },
        containerColor = AppSurface
    )
}

@Composable
private fun SingleFieldDialog(
    title: String,
    initial: String,
    label: String,
    onDismiss: () -> Unit,
    onSave: suspend (String) -> Unit
) {
    var value by remember(title, initial) { mutableStateOf(initial) }
    var saving by remember(title, initial) { mutableStateOf(false) }
    var saveError by remember(title, initial) { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title, color = LoginInk, fontWeight = FontWeight.Bold) },
        text = {
            ScrollableDialogContent {
                FormField(
                    value,
                    { value = it },
                    label
                ); if (saveError.isNotBlank()) InlineError(saveError)
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (!saving) scope.launch {
                        saving = true
                        saveError = ""
                        try {
                            onSave(value.trim())
                        } catch (cancelled: CancellationException) {
                            throw cancelled
                        } catch (error: Exception) {
                            saveError = error.message ?: "Perubahan gagal disimpan."
                        } finally {
                            saving = false
                        }
                    }
                },
                enabled = value.isNotBlank() && !saving
            ) { Text(if (saving) "Menyimpan…" else "Simpan", color = LoginAccent) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !saving) {
                Text(
                    "Batal",
                    color = LoginMuted
                )
            }
        },
        containerColor = AppSurface
    )
}

@Composable
private fun FormField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    enabled: Boolean = true
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label, fontSize = 11.sp) },
        singleLine = true,
        shape = RoundedCornerShape(13.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = LoginInk,
            unfocusedTextColor = LoginInk,
        focusedContainerColor = AppSurface,
        unfocusedContainerColor = AppSurface,
            focusedBorderColor = LoginAccent,
            unfocusedBorderColor = AppBorder,
            focusedLabelColor = LoginAccent,
            unfocusedLabelColor = LoginMuted,
            cursorColor = LoginAccent
        )
    )
}

@Composable
private fun StatusPill(status: String, color: Color) {
    Box(
        modifier = Modifier.clip(RoundedCornerShape(50.dp)).background(color.copy(alpha = 0.12f))
            .padding(horizontal = 8.dp, vertical = 5.dp)
    ) {
        Text(WasteFlow.label(status), color = color, fontSize = 11.sp, fontWeight = FontWeight.Bold)
    }
}

private fun reportAccent(status: String): Color = when (status) {
    WasteFlow.WAITING -> WasteOrange
    WasteFlow.ASSIGNED -> WastePurple
    WasteFlow.TRANSPORTED -> WasteTeal
    WasteFlow.PROCESSING -> WasteNavy
    WasteFlow.DONE -> WasteGreen
    else -> WasteRed
}

@Composable
private fun SectionTitle(title: String, subtitle: String) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(title, color = AppText, fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
        Text(
            subtitle,
            color = AppTextMuted,
            fontSize = 12.sp,
            lineHeight = 18.sp
        )
    }
}

@Composable
private fun SoftProgress(progress: Float) {
    Box(
        modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(50.dp))
            .background(WasteBlue)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(progress.coerceIn(0f, 1f)).height(8.dp)
                .background(LoginAccent)
        )
    }
}

@Composable
private fun DataProgress(progress: Float, accent: Color) {
    Box(
        modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(50.dp))
            .background(WasteBlue)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(progress.coerceIn(0f, 1f)).height(8.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(accent)
        )
    }
}

@Composable
private fun InlineError(message: String) {
    AppCard(
        modifier = Modifier.fillMaxWidth(),
        containerColor = WasteRed.copy(alpha = 0.08f),
        borderColor = WasteRed.copy(alpha = 0.24f),
        accentColor = WasteRed
    ) {
        Text(
            message,
            modifier = Modifier.padding(12.dp),
            color = WasteRed,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun EmptyAdminState(message: String) {
    AppCard(
        modifier = Modifier.fillMaxWidth(),
        containerColor = AppSurface,
        borderColor = AppBorder
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(23.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Filled.Inbox,
                contentDescription = null,
                tint = LoginAccent,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(message, color = AppTextMuted, fontSize = 11.sp)
        }
    }
}

private fun Double.pretty(): String =
    if (this % 1.0 == 0.0) toLong().toString() else String.format(java.util.Locale.US, "%.1f", this)

private fun shortMonth(period: String): String {
    val month = period.substringAfter('-', "").toIntOrNull() ?: return period.takeLast(2)
    return listOf(
        "Jan",
        "Feb",
        "Mar",
        "Apr",
        "Mei",
        "Jun",
        "Jul",
        "Agu",
        "Sep",
        "Okt",
        "Nov",
        "Des"
    )
        .getOrNull(month - 1) ?: period.takeLast(2)
}
