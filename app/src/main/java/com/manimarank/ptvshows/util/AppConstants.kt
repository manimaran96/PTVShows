package com.manimarank.ptvshows.util

/**
 * App Constants
 */
object AppConstants {
    const val keyTvSeriesId = "tvSeriesId"
    const val isOnline = true
}

/**
 * Constant for Image aspect ratio
 */
object ImageAspectRatio {
    const val poster = 2f/3f
    const val cover = 16f/9f
}

/**
 * Enum values for Movie Content Ratting
 */
enum class MovieContentRatting(val string: String) {
    UA("U/A"),
    U("U"),
}