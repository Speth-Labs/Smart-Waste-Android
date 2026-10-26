package com.uoi.pratikumwithbunafla.data.remote

import com.uoi.pratikumwithbunafla.BuildConfig
import com.uoi.pratikumwithbunafla.data.model.*
import com.uoi.pratikumwithbunafla.domain.WasteFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/** REST client Android untuk ruang Admin dan Petugas Smart Waste. */
object SmartWasteApi {
    // Host API diisi saat build: 10.0.2.2 untuk emulator, IP laptop untuk HP fisik.
    private const val BASE_URL = BuildConfig.SMART_WASTE_API_URL

    private var token: String? = null

    private suspend fun request(
        method: String,
        path: String,
        payload: JSONObject? = null,
        authToken: String? = token
    ): JSONObject =
        withContext(Dispatchers.IO) {
            val connection = (URL(BASE_URL + path).openConnection() as HttpURLConnection).apply {
                requestMethod = method
                connectTimeout = 8_000
                readTimeout = 12_000
                doInput = true
                setRequestProperty("Accept", "application/json")
                setRequestProperty("Content-Type", "application/json; charset=utf-8")
                authToken?.let { setRequestProperty("Authorization", "Bearer $it") }
                if (payload != null) doOutput = true
            }
            try {
                payload?.let { json ->
                    connection.outputStream.use { stream ->
                        stream.write(
                            json.toString().toByteArray(Charsets.UTF_8)
                        )
                    }
                }
                val code = connection.responseCode
                val stream =
                    if (code in 200..299) connection.inputStream else connection.errorStream
                val text = stream?.let { input ->
                    BufferedReader(InputStreamReader(input)).use { reader -> reader.readText() }
                }.orEmpty()
                if (code !in 200..299) {
                    val message = runCatching {
                        val json = JSONObject(text)
                        json.optString("message").ifBlank { json.optString("detail") }
                    }.getOrNull().orEmpty()
                    throw IllegalStateException(message.ifBlank { "Server mengembalikan error ($code)." })
                }
                JSONObject(text)
            } finally {
                connection.disconnect()
            }
        }

    private fun value(json: JSONObject, vararg keys: String): String {
        keys.forEach { key ->
            if (json.has(key) && !json.isNull(key)) return json.optString(key)
        }
        return ""
    }

    private fun mapAccount(json: JSONObject): UserAccount {
        val role = when (value(json, "role").lowercase()) {
            "admin" -> UserRole.ADMIN
            "petugas" -> UserRole.PETUGAS
            else -> throw IllegalStateException("Aplikasi ini hanya untuk Admin dan Petugas.")
        }
        return UserAccount(
            name = value(json, "name", "nama").ifBlank { "Admin Smart Waste" },
            username = value(json, "username"),
            password = "",
            role = role,
            isSuperAdmin = json.optBoolean("is_super_admin", false)
        )
    }

    private fun mapReport(json: JSONObject): WasteRecord {
        val status = value(json, "status").ifBlank { WasteFlow.WAITING }
        return WasteRecord(
            id = json.optInt("id", json.optInt("id_laporan", 0)),
            code = value(json, "code", "kode_laporan"),
            type = value(json, "type", "jenis", "jenis_sampah"),
            weightKg = json.optDouble("weight_kg", json.optDouble("berat_kg", 0.0)),
            status = status,
            reportedBy = value(json, "reported_by").ifBlank { "User" },
            location = value(json, "location", "lokasi").ifBlank { "-" },
            description = value(json, "description", "deskripsi"),
            photoUrl = value(json, "photo", "foto").ifBlank { null },
            reportedAt = value(json, "report_date", "tanggal_lapor", "created_at"),
            hazardLevel = value(json, "hazard_level", "tingkat_bahaya").ifBlank { "RENDAH" },
            workerId = json.optInt("worker_id").takeIf { it > 0 },
            workerName = value(json, "worker_name"),
            vehicleId = json.optInt("vehicle_id").takeIf { it > 0 },
            vehiclePlate = value(json, "vehicle_plate"),
            tpaId = json.optInt("tpa_id").takeIf { it > 0 },
            tpaName = value(json, "tpa_name")
        )
    }

    suspend fun login(username: String, password: String): UserAccount {
        // Jangan pernah memakai sesi akun sebelumnya jika percobaan login baru gagal.
        token = null
        val result = request(
            "POST",
            "/auth/login",
            JSONObject().put("username", username).put("password", password)
                .put("client", "android")
        )
        val account = mapAccount(result.getJSONObject("user"))
        token = result.optString("token").takeIf { it.isNotBlank() }
            ?: throw IllegalStateException("Server berhasil login tetapi tidak mengirim token sesi. Coba lagi.")
        return account
    }

    suspend fun checkConnection(): String {
        val result = request("GET", "/health")
        val apiStatus = result.optString("status")
        val databaseStatus = result.optString("database")
        if (apiStatus != "ok") {
            throw IllegalStateException("API merespons dengan status: ${apiStatus.ifBlank { "tidak diketahui" }}.")
        }
        if (databaseStatus != "connected") {
            throw IllegalStateException("API aktif, tetapi MySQL belum terhubung.")
        }
        return "Apache :8080 dan MySQL terhubung"
    }

    suspend fun dashboard(): AdminDashboardSummary {
        val data = request("GET", "/dashboard").optJSONObject("dashboard") ?: JSONObject()
        return AdminDashboardSummary(
            totalUsers = data.optInt("total_users", data.optInt("total_user", 0)),
            totalReports = data.optInt("total_reports", data.optInt("total_laporan", 0)),
            waiting = data.optInt("waiting", data.optInt("menunggu_verifikasi", 0)),
            processing = data.optInt("processing", data.optInt("diproses", 0)),
            completed = data.optInt("completed", data.optInt("selesai", 0)),
            rejected = data.optInt("rejected", data.optInt("ditolak", 0)),
            assigned = data.optInt("assigned", data.optInt("ditugaskan", 0)),
            transported = data.optInt("transported", data.optInt("diangkut", 0)),
            totalWeightKg = data.optDouble("total_weight_kg", 0.0),
            activeWorkers = data.optInt("active_workers", 0),
            vehicleCount = data.optInt("vehicle_count", 0),
            availableVehicles = data.optInt("available_vehicles", 0),
            tpaCapacityTon = data.optDouble("tpa_capacity_ton", 0.0),
            tpaUsedTon = data.optDouble("tpa_used_ton", 0.0),
            tpaReservedTon = data.optDouble("tpa_reserved_ton", 0.0),
            tpaUtilizationPercent = data.optDouble("tpa_utilization_percent", 0.0),
            hazardAlerts = data.optInt("hazard_alerts", 0)
        )
    }

    suspend fun reports(status: String? = null): List<WasteRecord> {
        val suffix = status?.takeIf { it.isNotBlank() }?.let { "?status=$it" } ?: ""
        val data = request("GET", "/admin/laporan$suffix").optJSONArray("reports") ?: JSONArray()
        return (0 until data.length()).map { index -> mapReport(data.getJSONObject(index)) }
    }

    suspend fun updateReportStatus(id: Int, status: String, note: String = ""): WasteRecord {
        val result = request(
            "PATCH",
            "/admin/laporan/$id/status",
            JSONObject().put("status", status).put("catatan", note)
        )
        return mapReport(result.getJSONObject("report"))
    }

    suspend fun users(): List<AdminUser> {
        val data = request("GET", "/admin/users").optJSONArray("users") ?: JSONArray()
        return (0 until data.length()).map { index ->
            val item = data.getJSONObject(index)
            AdminUser(
                id = item.optInt("id", item.optInt("id_user", 0)),
                name = value(item, "name", "nama"),
                username = value(item, "username"),
                email = value(item, "email"),
                active = item.optBoolean("active", item.optBoolean("aktif", true)),
                totalReports = item.optInt("total_reports", 0)
            )
        }
    }

    suspend fun toggleUser(id: Int, active: Boolean): Boolean {
        val result = request(
            "PATCH",
            "/admin/users/$id/toggle",
            JSONObject().put("aktif", active)
        )
        return result.optBoolean("active", active)
    }

    suspend fun workers(): List<AdminWorker> {
        val data = request("GET", "/admin/petugas").optJSONArray("workers") ?: JSONArray()
        return (0 until data.length()).map { index ->
            val item = data.getJSONObject(index)
            AdminWorker(
                id = item.optInt("id"),
                name = value(item, "name", "nama"),
                username = value(item, "username"),
                zone = value(item, "zone", "zona_tugas"),
                totalPickups = item.optInt("total_pickups", item.optInt("total_ambil", 0)),
                active = item.optBoolean("active", true),
                hasAccount = item.optBoolean("has_account", value(item, "username").isNotBlank())
            )
        }
    }

    suspend fun createStaffAccount(
        name: String,
        username: String,
        email: String,
        password: String,
        zone: String,
        role: String
    ) {
        request(
            "POST", "/admin/akun", JSONObject()
                .put("nama", name).put("username", username).put("email", email)
                .put("password", password).put("zona_tugas", zone).put("role", role)
        )
    }

    suspend fun updateWorker(worker: AdminWorker, active: Boolean) {
        request(
            "PATCH", "/admin/petugas/${worker.id}", JSONObject()
                .put("aktif", active).put("zona_tugas", worker.zone)
        )
    }

    suspend fun vehicles(): List<WasteVehicle> {
        val data = request("GET", "/admin/kendaraan").optJSONArray("vehicles") ?: JSONArray()
        return (0 until data.length()).map { index ->
            val item = data.getJSONObject(index)
            WasteVehicle(
                id = item.optInt("id"),
                plate = value(item, "plate", "plat_nomor"),
                capacityKg = item.optDouble("capacity_kg", 0.0),
                loadKg = item.optDouble("load_kg", 0.0),
                condition = value(item, "condition"),
                route = value(item, "route"),
                reservedKg = item.optDouble("reserved_kg", 0.0)
            )
        }
    }

    suspend fun createVehicle(plate: String, capacityKg: Double, condition: String, route: String) {
        request(
            "POST", "/admin/kendaraan", JSONObject()
                .put("plate", plate).put("capacity_kg", capacityKg)
                .put("condition", condition).put("route", route)
        )
    }

    suspend fun updateVehicle(vehicle: WasteVehicle) {
        request(
            "PATCH", "/admin/kendaraan/${vehicle.id}", JSONObject()
                .put("plate", vehicle.plate).put("capacity_kg", vehicle.capacityKg)
                .put("condition", vehicle.condition).put("route", vehicle.route)
        )
    }

    suspend fun facilities(): List<WasteFacility> {
        val data = request("GET", "/admin/tpa").optJSONArray("tpa") ?: JSONArray()
        return (0 until data.length()).map { index ->
            val item = data.getJSONObject(index)
            WasteFacility(
                id = item.optInt("id"),
                name = value(item, "name"),
                address = value(item, "address"),
                capacityTon = item.optDouble("capacity_ton", 0.0),
                filledTon = item.optDouble("filled_ton", 0.0),
                reservedTon = item.optDouble("reserved_ton", 0.0)
            )
        }
    }

    suspend fun createFacility(name: String, address: String, capacityTon: Double) {
        request(
            "POST", "/admin/tpa", JSONObject()
                .put("name", name).put("address", address).put("capacity_ton", capacityTon)
        )
    }

    suspend fun updateFacility(facility: WasteFacility) {
        request(
            "PATCH", "/admin/tpa/${facility.id}", JSONObject()
                .put("name", facility.name).put("address", facility.address)
                .put("capacity_ton", facility.capacityTon)
        )
    }

    suspend fun assignReport(reportId: Int, workerId: Int, vehicleId: Int, tpaId: Int) {
        request(
            "PATCH", "/admin/laporan/$reportId/tugaskan", JSONObject()
                .put("worker_id", workerId).put("vehicle_id", vehicleId).put("tpa_id", tpaId)
        )
    }

    suspend fun workerTasks(): List<WasteRecord> {
        val data = request("GET", "/petugas/tugas").optJSONArray("tasks") ?: JSONArray()
        return (0 until data.length()).map { index -> mapReport(data.getJSONObject(index)) }
    }

    suspend fun markTaskTransported(reportId: Int) {
        request("PATCH", "/petugas/tugas/$reportId/diangkut", JSONObject())
    }

    suspend fun completeTask(reportId: Int) {
        request("PATCH", "/petugas/tugas/$reportId/selesai", JSONObject())
    }

    suspend fun dailyReports(from: String, to: String): List<DailyReport> {
        val data =
            request("GET", "/admin/laporan-harian?from=$from&to=$to").optJSONArray("daily_reports")
                ?: JSONArray()
        return (0 until data.length()).map { index ->
            val item = data.getJSONObject(index)
            DailyReport(
                value(item, "date"),
                item.optInt("total_waste"),
                item.optDouble("weight_kg"),
                item.optInt("b3_alerts")
            )
        }
    }

    suspend fun statistics(): AdminStatistics {
        val data = request("GET", "/admin/statistik").optJSONObject("statistics") ?: JSONObject()
        val monthlyJson = data.optJSONArray("monthly") ?: JSONArray()
        val typeJson = data.optJSONArray("by_type") ?: JSONArray()
        val monthly = (0 until monthlyJson.length()).map { index ->
            val item = monthlyJson.getJSONObject(index)
            MonthlyStat(value(item, "period", "periode"), item.optInt("total", 0))
        }
        val byType = (0 until typeJson.length()).map { index ->
            val item = typeJson.getJSONObject(index)
            TypeStat(
                value(item, "type", "jenis_sampah"),
                item.optInt("total", 0),
                item.optDouble("weight_kg", 0.0)
            )
        }
        return AdminStatistics(
            monthly,
            byType,
            data.optInt("completed_reports", data.optInt("total_selesai", 0))
        )
    }

    suspend fun logout() {
        val currentToken = token ?: return
        try {
            request("POST", "/auth/logout", JSONObject(), authToken = currentToken)
        } catch (cancelled: CancellationException) {
            throw cancelled
        } catch (_: Exception) {
            // Sesi lokal tetap ditutup walau server sedang tidak terjangkau.
        } finally {
            if (token == currentToken) token = null
        }
    }
}
