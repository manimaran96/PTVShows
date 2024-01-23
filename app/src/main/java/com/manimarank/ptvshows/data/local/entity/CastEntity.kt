package com.manimarank.ptvshows.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity model for Cast
 */
@Entity
data class CastEntity(
    @PrimaryKey
    val id: Int?,
    val name: String?,
    val profile_path: String?,
    val character: String?,
    val order: Int?,
    val adult: Boolean?,
    val credit_id: String?,
    val gender: Int?,
    val known_for_department: String?,
    val original_name: String?,
    val popularity: Double?,
    // Series Id
    val series_id: Int?
)