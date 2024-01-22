package com.manimarank.ptvshows.data.remote.dto

import com.manimarank.ptvshows.data.remote.dto.details.GenreDto
import com.manimarank.ptvshows.data.remote.dto.details.EpisodeToAirDto
import com.manimarank.ptvshows.data.remote.dto.details.NetworkDto
import com.manimarank.ptvshows.data.remote.dto.details.ProductionCompanyDto
import com.manimarank.ptvshows.data.remote.dto.details.ProductionCountryDto
import com.manimarank.ptvshows.data.remote.dto.details.SeasonDto
import com.manimarank.ptvshows.data.remote.dto.details.SpokenLanguageDto

/**
 * API Data model for TV Series
 */
data class TvSeriesDto(
    val id: Int?,
    val name: String?,
    val adult: Boolean?,
    val backdrop_path: String?,
    val poster_path: String?,
    val overview: String?,
    val popularity: Double?,
    val vote_average: Double?,
    val vote_count: Int?,
    val first_air_date: String?,
    val origin_country: List<String?>?,
    val original_name: String?,
    val original_language: String?,

    // Details
    val tagline: String? = null,
    val type: String? = null,
    val status: String? = null,
    val number_of_episodes: Int? = null,
    val number_of_seasons: Int? = null,
    val languages: List<String?>? = null,
    val genres: List<GenreDto?>? = null,
    val homepage: String? = null,
    val last_air_date: String? = null,
    val in_production: Boolean? = null,
    val networks: List<NetworkDto?>? = null,
    val seasons: List<SeasonDto?>? = null,
    val spoken_languages: List<SpokenLanguageDto?>? = null,
    val next_episode_to_air: EpisodeToAirDto? = null,
    val last_episode_to_air: EpisodeToAirDto? = null,
    val production_companies: List<ProductionCompanyDto?>? = null,
    val production_countries: List<ProductionCountryDto?>? = null
)