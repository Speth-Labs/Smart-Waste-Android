package com.uoi.pratikumwithbunafla

import android.graphics.Color as AndroidColor
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.core.net.toUri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uoi.pratikumwithbunafla.data.model.*
import com.uoi.pratikumwithbunafla.data.remote.LanAppUpdate
import com.uoi.pratikumwithbunafla.data.remote.LanAppUpdater
import com.uoi.pratikumwithbunafla.data.remote.SmartWasteApi
import com.uoi.pratikumwithbunafla.domain.WasteFlow
import com.uoi.pratikumwithbunafla.ui.admin.*
import com.uoi.pratikumwithbunafla.ui.auth.LoginScreen
import com.uoi.pratikumwithbunafla.ui.components.SmartWasteSplash
import com.uoi.pratikumwithbunafla.ui.theme.SmartWasteTheme
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.io.File
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = AndroidColor.TRANSPARENT
        window.navigationBarColor = AndroidColor.TRANSPARENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = true
            isAppearanceLightNavigationBars = true
        }

        setContent {
            val appearancePreferences = remember {
                getSharedPreferences("smart_waste_appearance", MODE_PRIVATE)
            }
            var isDarkMode by rememberSaveable {
                mutableStateOf(appearancePreferences.getBoolean("dark_mode", false))
            }
            SideEffect {
                WindowCompat.getInsetsController(window, window.decorView).apply {
                    isAppearanceLightStatusBars = !isDarkMode
                    isAppearanceLightNavigationBars = !isDarkMode
                }
            }
            SmartWasteTheme(
                darkTheme = isDarkMode,
                onToggleTheme = {
                    isDarkMode = !isDarkMode
                    appearancePreferences.edit().putBoolean("dark_mode", isDarkMode).apply()
                }
            ) {
                val navController = rememberNavController()
                val scope = rememberCoroutineScope()
                var showSplash by rememberSaveable { mutableStateOf(true) }
                var activeAccount by remember { mutableStateOf<UserAccount?>(null) }
                var summary by remember { mutableStateOf(AdminDashboardSummary()) }
                var reports by remember { mutableStateOf(emptyList<WasteRecord>()) }
                var tasks by remember { mutableStateOf(emptyList<WasteRecord>()) }
                var users by remember { mutableStateOf(emptyList<AdminUser>()) }
                var workers by remember { mutableStateOf(emptyList<AdminWorker>()) }
                var vehicles by remember { mutableStateOf(emptyList<WasteVehicle>()) }
                var facilities by remember { mutableStateOf(emptyList<WasteFacility>()) }
                var dailyReports by remember { mutableStateOf(emptyList<DailyReport>()) }
                var dailyRangeDays by rememberSaveable { mutableIntStateOf(30) }
                var statistics by remember { mutableStateOf(AdminStatistics()) }
                var errorMessage by remember { mutableStateOf("") }
                var selectedStatus by remember { mutableStateOf<String?>(null) }
                var selectedReportView by rememberSaveable { mutableStateOf("verify") }
                var selectedOpsTab by rememberSaveable { mutableStateOf("petugas") }
                var availableUpdate by remember { mutableStateOf<LanAppUpdate?>(null) }
                var dismissedUpdateCode by rememberSaveable { mutableIntStateOf(0) }
                var downloadingUpdate by remember { mutableStateOf(false) }
                var downloadedUpdate by remember { mutableStateOf<File?>(null) }
                var updateMessage by remember { mutableStateOf("") }

                suspend fun refreshWorkspace() {
                    val account = activeAccount ?: return
                    val loadErrors = mutableListOf<String>()
                    if (account.role == UserRole.ADMIN) {
                        val calendar = Calendar.getInstance()
                            .apply { add(Calendar.DAY_OF_YEAR, -(dailyRangeDays - 1)) }
                        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                        val from = formatter.format(calendar.time)
                        val to = formatter.format(Date())
                        summary = loadOrKeep(
                            "Ringkasan",
                            summary,
                            loadErrors
                        ) { SmartWasteApi.dashboard() }
                        reports =
                            loadOrKeep("Laporan", reports, loadErrors) { SmartWasteApi.reports() }
                        users =
                            loadOrKeep("Data warga", users, loadErrors) { SmartWasteApi.users() }
                        statistics = loadOrKeep(
                            "Statistik",
                            statistics,
                            loadErrors
                        ) { SmartWasteApi.statistics() }
                        workers = loadOrKeep(
                            "Data petugas",
                            workers,
                            loadErrors
                        ) { SmartWasteApi.workers() }
                        vehicles = loadOrKeep(
                            "Data kendaraan",
                            vehicles,
                            loadErrors
                        ) { SmartWasteApi.vehicles() }
                        facilities = loadOrKeep(
                            "Data TPA",
                            facilities,
                            loadErrors
                        ) { SmartWasteApi.facilities() }
                        dailyReports = loadOrKeep("Aktivitas harian", dailyReports, loadErrors) {
                            SmartWasteApi.dailyReports(from, to)
                        }
                    } else {
                        tasks = loadOrKeep(
                            "Penugasan",
                            tasks,
                            loadErrors
                        ) { SmartWasteApi.workerTasks() }
                    }
                    errorMessage = loadErrors.take(4).joinToString("\n") +
                            if (loadErrors.size > 4) "\n+ ${loadErrors.size - 4} data lain gagal dimuat" else ""
                }

                fun signOut() {
                    scope.launch { SmartWasteApi.logout() }
                    activeAccount = null
                    reports = emptyList()
                    tasks = emptyList()
                    users = emptyList()
                    workers = emptyList()
                    vehicles = emptyList()
                    facilities = emptyList()
                    dailyReports = emptyList()
                    statistics = AdminStatistics()
                    summary = AdminDashboardSummary()
                    errorMessage = ""
                    selectedStatus = null
                    selectedReportView = "verify"
                    selectedOpsTab = "petugas"
                    navController.navigate("login") { popUpTo(0) { inclusive = true } }
                }

                fun navigateAdminRoute(route: String) {
                    when {
                        route.startsWith("status:") -> {
                            selectedStatus = route.substringAfter(':').takeUnless { it == "semua" }
                            selectedReportView = "all"
                            navController.navigate("reports") { launchSingleTop = true }
                        }

                        route.startsWith("ops:") -> {
                            selectedOpsTab = route.substringAfter(':')
                            navController.navigate("ops") { launchSingleTop = true }
                        }

                        route == "reports" -> {
                            selectedStatus = null
                            selectedReportView = "verify"
                            navController.navigate("reports") { launchSingleTop = true }
                        }

                        else -> {
                            navController.navigate(route) { launchSingleTop = true }
                        }
                    }
                }

                LaunchedEffect(Unit) {
                    delay(3000)
                    showSplash = false
                }

                LaunchedEffect(Unit) {
                    delay(5_000)
                    while (true) {
                        if (availableUpdate == null) {
                            runCatching { LanAppUpdater.check(this@MainActivity) }
                                .getOrNull()
                                ?.takeIf { it.versionCode > dismissedUpdateCode }
                                ?.let { availableUpdate = it }
                        }
                        delay(60_000L)
                    }
                }

                LaunchedEffect(activeAccount?.username) {
                    if (activeAccount != null) {
                        while (true) {
                            refreshWorkspace()
                            delay(15_000)
                        }
                    }
                }

                AnimatedVisibility(
                    visible = showSplash,
                    exit = fadeOut(animationSpec = tween(350))
                ) {
                    SmartWasteSplash()
                }

                if (!showSplash) {
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") {
                            LoginScreen(
                                initialDarkMode = isDarkMode,
                                onAuthenticate = { username, password, selectedRole ->
                                    val account = SmartWasteApi.login(username.trim(), password)
                                    if (account.role != selectedRole) {
                                        SmartWasteApi.logout()
                                        throw IllegalStateException(
                                            "Akun ini terdaftar sebagai ${account.role.label}. Pilih ${account.role.label} untuk masuk."
                                        )
                                    }
                                    activeAccount = account
                                    selectedStatus = null
                                    selectedReportView = "verify"
                                    account
                                },
                                onCheckConnection = { SmartWasteApi.checkConnection() },
                                onLoginSuccess = {
                                    val destination =
                                        if (activeAccount?.role == UserRole.PETUGAS) "tasks" else "dashboard"
                                    navController.navigate(destination) {
                                        popUpTo("login") { inclusive = true }
                                    }
                                },
                                onThemeModeChange = { darkMode ->
                                    isDarkMode = darkMode
                                    appearancePreferences.edit().putBoolean("dark_mode", darkMode).apply()
                                }
                            )
                        }
                        composable("dashboard") {
                            val account = activeAccount
                            if (account == null) RedirectToLogin { navController.navigate("login") }
                            else if (account.role != UserRole.ADMIN) RedirectToRoute {
                                navController.navigate(
                                    "tasks"
                                )
                            }
                            else AdminDashboardScreen(
                                summary = summary,
                                reports = reports,
                                errorMessage = errorMessage,
                                account = account,
                                onNavigate = ::navigateAdminRoute,
                                onLogout = ::signOut
                            )
                        }
                        composable("reports") {
                            val account = activeAccount
                            if (account == null) RedirectToLogin { navController.navigate("login") }
                            else if (account.role != UserRole.ADMIN) RedirectToRoute {
                                navController.navigate(
                                    "tasks"
                                )
                            }
                            else AdminVerificationScreen(
                                reports = reports,
                                workers = workers,
                                vehicles = vehicles,
                                facilities = facilities,
                                selectedStatus = selectedStatus,
                                showAllReports = selectedReportView == "all",
                                errorMessage = errorMessage,
                                account = account,
                                onReject = { report ->
                                    scope.launch {
                                        try {
                                            SmartWasteApi.updateReportStatus(
                                                report.id,
                                                WasteFlow.REJECTED,
                                                "Laporan ditolak oleh admin."
                                            )
                                            refreshWorkspace()
                                        } catch (error: Exception) {
                                            errorMessage = error.message ?: "Laporan gagal ditolak."
                                        }
                                    }
                                },
                                onAssign = { report, workerId, vehicleId, tpaId ->
                                    SmartWasteApi.assignReport(
                                        report.id,
                                        workerId,
                                        vehicleId,
                                        tpaId
                                    )
                                    refreshWorkspace()
                                },
                                onOpenPhoto = { openPhoto(it) },
                                onReportViewChange = { showAll ->
                                    selectedReportView = if (showAll) "all" else "verify"
                                    selectedStatus = null
                                },
                                onFilterChange = { selectedStatus = it },
                                onNavigate = ::navigateAdminRoute,
                                onLogout = ::signOut
                            )
                        }
                        composable("ops") {
                            val account = activeAccount
                            if (account == null) RedirectToLogin { navController.navigate("login") }
                            else if (account.role != UserRole.ADMIN) RedirectToRoute {
                                navController.navigate(
                                    "tasks"
                                )
                            }
                            else AdminOperationsScreen(
                                users = users,
                                workers = workers,
                                vehicles = vehicles,
                                facilities = facilities,
                                errorMessage = errorMessage,
                                account = account,
                                onCreateWorker = { name, username, email, password, zone, role ->
                                    SmartWasteApi.createStaffAccount(
                                        name,
                                        username,
                                        email,
                                        password,
                                        zone,
                                        role
                                    )
                                    refreshWorkspace()
                                },
                                onUpdateWorker = { worker, active ->
                                    SmartWasteApi.updateWorker(worker, active)
                                    refreshWorkspace()
                                },
                                onCreateVehicle = { plate, capacity, condition, route ->
                                    SmartWasteApi.createVehicle(plate, capacity, condition, route)
                                    refreshWorkspace()
                                },
                                onUpdateVehicle = { vehicle ->
                                    SmartWasteApi.updateVehicle(vehicle)
                                    refreshWorkspace()
                                },
                                onCreateFacility = { name, address, capacity ->
                                    SmartWasteApi.createFacility(name, address, capacity)
                                    refreshWorkspace()
                                },
                                onUpdateFacility = { facility ->
                                    SmartWasteApi.updateFacility(facility)
                                    refreshWorkspace()
                                },
                                onToggleUser = { user ->
                                    scope.launch {
                                        try {
                                            SmartWasteApi.toggleUser(user.id, !user.active)
                                            refreshWorkspace()
                                        } catch (error: Exception) {
                                            errorMessage =
                                                error.message ?: "Status warga gagal diperbarui."
                                        }
                                    }
                                },
                                onNavigate = ::navigateAdminRoute,
                                initialTab = selectedOpsTab,
                                onLogout = ::signOut
                            )
                        }
                        composable("stats") {
                            val account = activeAccount
                            if (account == null) RedirectToLogin { navController.navigate("login") }
                            else if (account.role != UserRole.ADMIN) RedirectToRoute {
                                navController.navigate(
                                    "tasks"
                                )
                            }
                            else AdminStatsScreen(
                                stats = statistics,
                                dailyReports = dailyReports,
                                dailyRangeDays = dailyRangeDays,
                                errorMessage = errorMessage,
                                account = account,
                                onDailyRange = { days ->
                                    dailyRangeDays = days
                                    scope.launch { refreshWorkspace() }
                                },
                                onNavigate = ::navigateAdminRoute,
                                onLogout = ::signOut
                            )
                        }
                        composable("profile") {
                            val account = activeAccount
                            if (account == null) RedirectToLogin { navController.navigate("login") }
                            else if (account.role != UserRole.ADMIN) RedirectToRoute {
                                navController.navigate("tasks")
                            }
                            else AdminProfileScreen(
                                account = account,
                                onNavigate = ::navigateAdminRoute,
                                onLogout = ::signOut
                            )
                        }
                        composable("tasks") {
                            val account = activeAccount
                            if (account == null) RedirectToLogin { navController.navigate("login") }
                            else if (account.role != UserRole.PETUGAS) RedirectToRoute {
                                navController.navigate(
                                    "dashboard"
                                )
                            }
                            else PetugasTasksScreen(
                                tasks = tasks,
                                errorMessage = errorMessage,
                                account = account,
                                onOpenPhoto = { openPhoto(it) },
                                onMarkTransported = { task ->
                                    scope.launch {
                                        try {
                                            SmartWasteApi.markTaskTransported(task.id)
                                            refreshWorkspace()
                                        } catch (error: Exception) {
                                            errorMessage =
                                                error.message ?: "Status angkut gagal diperbarui."
                                        }
                                    }
                                },
                                onComplete = { task ->
                                    scope.launch {
                                        try {
                                            SmartWasteApi.completeTask(task.id)
                                            refreshWorkspace()
                                        } catch (error: Exception) {
                                            errorMessage =
                                                error.message ?: "Penugasan gagal diselesaikan."
                                        }
                                    }
                                },
                                onLogout = ::signOut
                            )
                        }
                    }
                }

                availableUpdate?.let { update ->
                    androidx.compose.material3.AlertDialog(
                        onDismissRequest = {
                            if (!downloadingUpdate) {
                                dismissedUpdateCode = update.versionCode
                                availableUpdate = null
                                updateMessage = ""
                            }
                        },
                        title = { androidx.compose.material3.Text("Pembaruan Smart Waste ${update.versionName}") },
                        text = {
                            androidx.compose.material3.Text(
                                if (updateMessage.isBlank()) update.notes
                                else "${update.notes}\n\n$updateMessage"
                            )
                        },
                        confirmButton = {
                            androidx.compose.material3.Button(
                                enabled = !downloadingUpdate,
                                onClick = {
                                    scope.launch {
                                        downloadingUpdate = true
                                        updateMessage = ""
                                        try {
                                            val apk = downloadedUpdate
                                                ?: LanAppUpdater.download(this@MainActivity, update)
                                                    .also { downloadedUpdate = it }
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O &&
                                                !packageManager.canRequestPackageInstalls()
                                            ) {
                                                updateMessage = "Unduhan siap. Izinkan pemasangan untuk Smart Waste, lalu tekan tombol ini lagi."
                                                startActivity(
                                                    Intent(
                                                        Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
                                                        android.net.Uri.parse("package:$packageName")
                                                    )
                                                )
                                            } else {
                                                val apkUri = androidx.core.content.FileProvider.getUriForFile(
                                                    this@MainActivity,
                                                    "$packageName.fileprovider",
                                                    apk
                                                )
                                                startActivity(
                                                    Intent(Intent.ACTION_VIEW).apply {
                                                        setDataAndType(
                                                            apkUri,
                                                            "application/vnd.android.package-archive"
                                                        )
                                                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                                    }
                                                )
                                            }
                                        } catch (error: Exception) {
                                            updateMessage = error.message ?: "Pembaruan gagal disiapkan."
                                        } finally {
                                            downloadingUpdate = false
                                        }
                                    }
                                }
                            ) {
                                androidx.compose.material3.Text(
                                    when {
                                        downloadingUpdate -> "Mengunduh…"
                                        downloadedUpdate != null -> "Pasang pembaruan"
                                        else -> "Unduh & pasang"
                                    }
                                )
                            }
                        },
                        dismissButton = {
                            androidx.compose.material3.TextButton(
                                enabled = !downloadingUpdate,
                                onClick = {
                                    dismissedUpdateCode = update.versionCode
                                    availableUpdate = null
                                    updateMessage = ""
                                }
                            ) {
                                androidx.compose.material3.Text("Nanti")
                            }
                        }
                    )
                }
            }
        }
    }

    private fun openPhoto(url: String) {
        if (url.isBlank()) return
        runCatching { startActivity(Intent(Intent.ACTION_VIEW, url.toUri())) }
            .onFailure { /* Laporan tetap bisa dibaca meski browser foto tidak tersedia. */ }
    }
}

private suspend fun <T> loadOrKeep(
    label: String,
    currentValue: T,
    errors: MutableList<String>,
    load: suspend () -> T
): T = try {
    load()
} catch (cancelled: CancellationException) {
    throw cancelled
} catch (error: Exception) {
    errors += "$label: ${error.message ?: "gagal dimuat."}"
    currentValue
}

@androidx.compose.runtime.Composable
private fun RedirectToLogin(onRedirect: () -> Unit) {
    LaunchedEffect(Unit) { onRedirect() }
}

@androidx.compose.runtime.Composable
private fun RedirectToRoute(onRedirect: () -> Unit) {
    LaunchedEffect(Unit) { onRedirect() }
}
