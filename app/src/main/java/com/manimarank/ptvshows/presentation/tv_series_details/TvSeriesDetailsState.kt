package com.manimarank.ptvshows.presentation.tv_series_details

import com.manimarank.ptvshows.domain.model.TvSeries

/**
 * Presentation state for TV Series Details
 */
data class TvSeriesDetailsState(
    val isLoading: Boolean = false,
    val tvSeries: TvSeries? = null,
    val error: String? = null
)
