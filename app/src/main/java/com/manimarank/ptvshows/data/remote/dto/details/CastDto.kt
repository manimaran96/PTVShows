package com.manimarank.ptvshows.data.remote.dto.details

/**
 * API Data model for Cast
 */
data class CastDto(
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
)