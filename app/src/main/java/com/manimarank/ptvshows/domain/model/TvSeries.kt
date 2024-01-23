package com.manimarank.ptvshows.domain.model

/**
 * Model class for TV Series
 */
data class TvSeries(
    val id: Int? = null,
    val name: String? = null,
    val adult: Boolean? = false,
    val backdrop_path: String? = null,
    val poster_path: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val vote_average: Double? = null,
    val vote_count: Int? = null,
    val first_air_date: String? = null,
    val origin_country: List<String?>? = null,
    val original_name: String? = null,
    val original_language: String? = null,

    // Details
    val tagline: String? = null,
    val type: String? = null,
    val status: String? = null,
    val number_of_episodes: Int? = null,
    val number_of_seasons: Int? = null,
    val languages: List<String?>? = null,
    val genres: List<String?>? = null,
    val homepage: String? = null,
    val last_air_date: String? = null,
    val in_production: Boolean? = null,
    val networks: List<String?>? = null,
    val spoken_languages: List<String?>? = null,
    val production_companies: List<String?>? = null,
    val production_countries: List<String?>? = null,
    val seasons: List<Season?>? = null,
    val cast_list: List<Cast?>? = null,
)