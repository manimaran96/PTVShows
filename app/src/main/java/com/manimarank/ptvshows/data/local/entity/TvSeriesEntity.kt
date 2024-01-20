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
    val adult: Boolean?,
    val backdrop_path: String?,
    val first_air_date: String?,
    val genre_ids: String?,
    val name: String?,
    val origin_country: String?,
    val original_language: String?,
    val original_name: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val vote_average: Double?,
    val vote_count: Int?
)
