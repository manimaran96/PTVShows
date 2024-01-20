package com.manimarank.ptvshows.data.remote.dto

/**
 * API Data model for TV Series List
 */
data class TvSeriesListDto(
    val page: Int?,
    val results: List<TvSeriesDto?>?,
    val total_pages: Int?,
    val total_results: Int?
)