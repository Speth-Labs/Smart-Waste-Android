package com.uoi.pratikumwithbunafla.data.remote

import android.content.Context
import com.uoi.pratikumwithbunafla.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.security.DigestInputStream
import java.net.HttpURLConnection
import java.net.URL
import java.security.MessageDigest

data class LanAppUpdate(
    val versionCode: Int,
    val versionName: String,
    val apkUrl: String,
    val sha256: String,
    val notes: String
)

object LanAppUpdater {
    suspend fun check(context: Context): LanAppUpdate? = withContext(Dispatchers.IO) {
        val connection = (URL(BuildConfig.SMART_WASTE_UPDATE_URL).openConnection() as HttpURLConnection)
        try {
            connection.connectTimeout = 5_000
            connection.readTimeout = 8_000
            connection.setRequestProperty("Cache-Control", "no-cache")
            if (connection.responseCode !in 200..299) return@withContext null

            val json = JSONObject(connection.inputStream.bufferedReader().use { it.readText() })
            if (json.optString("package_name") != context.packageName) return@withContext null

            val versionCode = json.optInt("version_code")
            val apkUrl = json.optString("apk_url")
            val hash = json.optString("sha256").lowercase()
            val currentInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            val installedVersion = if (android.os.Build.VERSION.SDK_INT >= 28) {
                currentInfo.longVersionCode
            } else {
                @Suppress("DEPRECATION")
                currentInfo.versionCode.toLong()
            }
            if (versionCode <= installedVersion || !apkUrl.startsWith("http://") || !hash.matches(Regex("[a-f0-9]{64}"))) {
                return@withContext null
            }

            LanAppUpdate(
                versionCode = versionCode,
                versionName = json.optString("version_name", versionCode.toString()),
                apkUrl = apkUrl,
                sha256 = hash,
                notes = json.optString("notes", "Pembaruan Smart Waste tersedia.")
            )
        } finally {
            connection.disconnect()
        }
    }

    suspend fun download(context: Context, update: LanAppUpdate): File = withContext(Dispatchers.IO) {
        val folder = File(context.cacheDir, "updates").apply { mkdirs() }
        val partFile = File(folder, "SmartWaste-${update.versionCode}.apk.part")
        val apkFile = File(folder, "SmartWaste-${update.versionCode}.apk")
        val connection = (URL(update.apkUrl).openConnection() as HttpURLConnection)
        try {
            connection.connectTimeout = 8_000
            connection.readTimeout = 30_000
            if (connection.responseCode !in 200..299) {
                throw IllegalStateException("APK belum bisa diunduh dari laptop.")
            }
            connection.inputStream.use { input ->
                FileOutputStream(partFile).use { output -> input.copyTo(output) }
            }
        } finally {
            connection.disconnect()
        }

        val digest = MessageDigest.getInstance("SHA-256")
        partFile.inputStream().use { input ->
            DigestInputStream(input, digest).use { stream ->
                val buffer = ByteArray(16 * 1024)
                while (stream.read(buffer) != -1) { }
            }
        }
        val actualHash = digest.digest().joinToString("") { "%02x".format(it) }
        if (!actualHash.equals(update.sha256, ignoreCase = true)) {
            partFile.delete()
            throw IllegalStateException("Pemeriksaan keamanan APK gagal; file tidak dipasang.")
        }
        if (apkFile.exists()) apkFile.delete()
        if (!partFile.renameTo(apkFile)) {
            partFile.delete()
            throw IllegalStateException("File pembaruan gagal disiapkan.")
        }
        apkFile
    }
}
