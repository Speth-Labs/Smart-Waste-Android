package com.uoi.pratikumwithbunafla.data.model

enum class UserRole(
    val label: String,
    val description: String
) {
    ADMIN("Admin", "Mengelola laporan dan operasional Smart Waste"),
    PETUGAS("Petugas", "Menjalankan penugasan pengangkutan sampah")
}

data class UserAccount(
    val name: String,
    val username: String,
    val password: String,
    val role: UserRole,
    val isSuperAdmin: Boolean = false
)

data class WasteRecord(
    val id: Int,
    val code: String,
    val type: String,
    val weightKg: Double,
    val status: String,
    val reportedBy: String = "User",
    val location: String = "-",
    val description: String = "",
    val photoUrl: String? = null,
    val reportedAt: String = "",
    val hazardLevel: String = "RENDAH",
    val workerId: Int? = null,
    val workerName: String = "",
    val vehicleId: Int? = null,
    val vehiclePlate: String = "",
    val tpaId: Int? = null,
    val tpaName: String = ""
)

data class AdminDashboardSummary(
    val totalUsers: Int = 0,
    val totalReports: Int = 0,
    val waiting: Int = 0,
    val processing: Int = 0,
    val completed: Int = 0,
    val rejected: Int = 0,
    val assigned: Int = 0,
    val transported: Int = 0,
    val totalWeightKg: Double = 0.0,
    val activeWorkers: Int = 0,
    val vehicleCount: Int = 0,
    val availableVehicles: Int = 0,
    val tpaCapacityTon: Double = 0.0,
    val tpaUsedTon: Double = 0.0,
    val tpaReservedTon: Double = 0.0,
    val tpaUtilizationPercent: Double = 0.0,
    val hazardAlerts: Int = 0
)

data class AdminWorker(
    val id: Int,
    val name: String,
    val username: String,
    val zone: String,
    val totalPickups: Int,
    val active: Boolean,
    val hasAccount: Boolean = true
)

data class WasteVehicle(
    val id: Int,
    val plate: String,
    val capacityKg: Double,
    val loadKg: Double,
    val condition: String,
    val route: String,
    val reservedKg: Double = 0.0
) {
    val availableKg: Double get() = (capacityKg - loadKg - reservedKg).coerceAtLeast(0.0)
}

data class WasteFacility(
    val id: Int,
    val name: String,
    val address: String,
    val capacityTon: Double,
    val filledTon: Double,
    val reservedTon: Double = 0.0
) {
    val remainingTon: Double get() = (capacityTon - filledTon - reservedTon).coerceAtLeast(0.0)
}

data class DailyReport(
    val date: String,
    val totalWaste: Int,
    val weightKg: Double,
    val b3Alerts: Int
)

data class AdminUser(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val active: Boolean,
    val totalReports: Int
)

data class MonthlyStat(
    val period: String,
    val total: Int
)

data class TypeStat(
    val type: String,
    val total: Int,
    val weightKg: Double
)

data class AdminStatistics(
    val monthly: List<MonthlyStat> = emptyList(),
    val byType: List<TypeStat> = emptyList(),
    val completedReports: Int = 0
)
