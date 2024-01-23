package com.manimarank.ptvshows.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Utility for Date conversions
 */
object DateUtils {
    private val displayFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")

    fun displayDate(date: String?): String {
        return if (!date.isNullOrEmpty()) LocalDate.parse(date).format(displayFormatter) else ""
    }
}

