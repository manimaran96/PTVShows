package com.manimarank.ptvshows.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Entity model for Season
 */
@Entity
data class SeasonEntity(
    @PrimaryKey
    val id: Int?,
    val name: String?,
    val poster_path: String?,
    val season_number: Int?,
    val air_date: String?,
    val episode_count: Int?,
    val overview: String?,
    val vote_average: Double?,
    // Series Id
    val series_id: Int?
)