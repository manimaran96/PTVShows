package com.manimarank.ptvshows.domain.repository

import com.manimarank.ptvshows.domain.model.TvSeries
import com.manimarank.ptvshows.domain.model.TvSeriesList
import com.manimarank.ptvshows.util.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Domain Repository for TV Series
 */
interface TvSeriesRepository {
    suspend fun getTvSeriesList(
        forceFetchFromRemote: Boolean = false,
        page: Int
    ): TvSeriesList

    suspend fun getTvSeries(
        id: Int,
        forceFetchFromRemote: Boolean = true
    ): TvSeries

    suspend fun searchTvSeries(
        searchTerm: String,
        forceFetchFromRemote: Boolean = false,
        page: Int
    ): TvSeriesList
}