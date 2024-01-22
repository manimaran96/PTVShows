package com.manimarank.ptvshows.data.remote.dto.details


/**
 * API Data model for Season
 */
data class SeasonDto(
    val air_date: String?,
    val episode_count: Int?,
    val id: Int?,
    val name: String?,
    val overview: String?,
    val poster_path: String?,
    val season_number: Int?,
    val vote_average: Double?
)