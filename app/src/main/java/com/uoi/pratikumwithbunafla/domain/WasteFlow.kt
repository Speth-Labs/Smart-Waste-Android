package com.uoi.pratikumwithbunafla.domain

/** Shared status rules for reports handled by Admin and Petugas. */
object WasteFlow {
    const val WAITING = "menunggu_verifikasi"
    const val PROCESSING = "diproses"
    const val ASSIGNED = "ditugaskan"
    const val TRANSPORTED = "diangkut"
    const val DONE = "selesai"
    const val REJECTED = "ditolak"

    val stages = listOf(WAITING, ASSIGNED, TRANSPORTED, DONE)

    fun isInProgress(status: String): Boolean =
        status == PROCESSING || status == ASSIGNED || status == TRANSPORTED

    fun label(status: String): String = when (status) {
        WAITING -> "Menunggu Verifikasi"
        PROCESSING -> "Diproses"
        ASSIGNED -> "Ditugaskan"
        TRANSPORTED -> "Diangkut"
        DONE -> "Selesai"
        REJECTED -> "Ditolak"
        else -> status.replace('_', ' ').replaceFirstChar { it.uppercase() }
    }

    fun next(status: String): String? {
        val index = stages.indexOf(status)
        if (index < 0) return null
        return stages.getOrNull(index + 1)
    }

    fun previous(status: String): String? {
        val index = stages.indexOf(status)
        if (index < 0) return null
        return stages.getOrNull(index - 1)
    }

    fun progress(status: String): Float {
        val index = stages.indexOf(status).coerceAtLeast(0)
        return index.toFloat() / (stages.lastIndex.coerceAtLeast(1)).toFloat()
    }
}
