package com.manimarank.ptvshows.domain.model

/**
 * Model class for TV Series List
 */
data class TvSeriesList(
    var tvSeriesList: List<TvSeries> = emptyList(),
    var totalPage: Int? = -1,
)
