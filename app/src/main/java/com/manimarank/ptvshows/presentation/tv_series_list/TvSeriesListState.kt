package com.manimarank.ptvshows.presentation.tv_series_list

import com.manimarank.ptvshows.domain.model.TvSeries

/**
 * Presentation state for TV Series List
 */
data class TvSeriesListState(
    val isLoading: Boolean = false,
    val page: Int = 1,
    val tvSeriesList: List<TvSeries> = emptyList(),
    val totalPages: Int? = null,
    val error: String? = null,
    val pullToRefresh: Boolean = false
)