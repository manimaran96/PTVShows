package com.manimarank.ptvshows.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity model for TV Series
 */
@Entity
data class TvSeriesEntity(
    @PrimaryKey
    val id: Int,
    val name: String?,
    val adult: Boolean?,
    val backdrop_path: String?,
    val poster_path: String?,
    val overview: String?,
    val popularity: Double?,
    val vote_average: Double?,
    val vote_count: Int?,
    val first_air_date: String?,
    val origin_country: String?,
    val original_name: String?,
    val original_language: String?,
    val tagline: String? = null,
    val type: String? = null,
    val status: String? = null,
    val number_of_episodes: Int? = null,
    val number_of_seasons: Int? = null,
    val languages: String? = null,
    val genres: String? = null,
    val homepage: String? = null,
    val last_air_date: String? = null,
    val in_production: Boolean? = null,
    val networks: String? = null,
    val spoken_languages: String? = null,
    val production_companies: String? = null,
    val production_countries: String? = null
)
