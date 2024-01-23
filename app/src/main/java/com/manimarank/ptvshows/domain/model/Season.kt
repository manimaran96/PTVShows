package com.manimarank.ptvshows.domain.model


/**
 * Model class for Season
 */
data class Season(
    val id: Int?,
    val name: String?,
    val poster_path: String?,
    val season_number: Int?,
    val air_date: String?,
    val episode_count: Int?,
    val overview: String?,
    val vote_average: Double?
)