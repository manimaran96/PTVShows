package com.manimarank.ptvshows.presentation.search

import com.manimarank.ptvshows.domain.model.TvSeries

/**
 * Presentation state for TV Series Search
 */
data class TvSeriesSearchState(
    val isLoading: Boolean = false,
    val page: Int = 1,
    val tvSeriesList: List<TvSeries> = emptyList(),
    val error: String? = null,
)
