package com.manimarank.ptvshows.data.repository

import com.manimarank.ptvshows.data.local.TvSeriesDatabase
import com.manimarank.ptvshows.data.mappers.toCastEntity
import com.manimarank.ptvshows.data.mappers.toSeasonEntity
import com.manimarank.ptvshows.data.mappers.toTvSeries
import com.manimarank.ptvshows.data.mappers.toTvSeriesEntity
import com.manimarank.ptvshows.data.remote.TvSeriesApi
import com.manimarank.ptvshows.domain.model.TvSeries
import com.manimarank.ptvshows.domain.model.TvSeriesList
import com.manimarank.ptvshows.domain.repository.TvSeriesRepository
import javax.inject.Inject

/**
 * Data level Repository Implementation for TV Series
 */
class TvSeriesRepositoryImpl @Inject constructor(
    private val tvSeriesApi: TvSeriesApi,
    private val tvSeriesDatabase: TvSeriesDatabase,
): TvSeriesRepository {
    override suspend fun getTvSeriesList(
        forceFetchFromRemote: Boolean,
        page: Int
    ): TvSeriesList {
        if (forceFetchFromRemote) {
            val apiRes = tvSeriesApi.getPopularTvSeries(page)
            val tvSeriesEntities = apiRes.results?.filterNotNull()?.let {
                it.map { tvSeriesDto -> tvSeriesDto.toTvSeriesEntity() }
            } ?: listOf()

            // Update in local
            tvSeriesDatabase.tvSeriesDao.upsertTvSeriesList(tvSeriesEntities)
            return TvSeriesList(
                tvSeriesEntities.map { it.toTvSeries() },
                apiRes.total_pages
            )
        } else {
            val list = tvSeriesDatabase.tvSeriesDao.getTvSeriesList()
            return TvSeriesList(
                list.map { tvSeriesEntity -> tvSeriesEntity.toTvSeries() }
            )
        }
    }

    override suspend fun getTvSeries(
        id: Int,
        forceFetchFromRemote: Boolean
    ): TvSeries {
        if (forceFetchFromRemote) {
            val apiRes = tvSeriesApi.getTvSeriesDetails(id)
            val apiCastRes = tvSeriesApi.getTvSeriesCastDetails(id)
            val tvSeriesEntity = apiRes?.toTvSeriesEntity()
            val seasonsEntity = apiRes?.seasons?.mapNotNull { seasonDto -> seasonDto?.toSeasonEntity(tvSeriesEntity?.id ?: -1) }
            val castListEntity = apiCastRes?.cast?.mapNotNull { cast -> cast?.toCastEntity(tvSeriesEntity?.id ?: -1) }

            // Update in local
            tvSeriesEntity?.let { tvSeriesDatabase.tvSeriesDao.upsertTvSeries(it) }
            seasonsEntity?.let { tvSeriesDatabase.tvSeriesDao.upsertSeasonsForSeries(it) }
            castListEntity?.let { tvSeriesDatabase.tvSeriesDao.upsertCastForSeries(it) }
            return tvSeriesEntity?.toTvSeries(seasonsEntity, castListEntity) ?: TvSeries()
        } else {
            val tvSeriesWithSeasonEntity = tvSeriesDatabase.tvSeriesDao.getTvSeriesById(id)
            val seasonsEntity = tvSeriesWithSeasonEntity?.seasons
            val castListEntity = tvSeriesWithSeasonEntity?.castList
            return tvSeriesWithSeasonEntity?.tvSeriesEntity?.toTvSeries(seasonsEntity, castListEntity) ?: TvSeries()
        }
    }

    override suspend fun searchTvSeries(
        searchTerm: String,
        forceFetchFromRemote: Boolean,
        page: Int
    ): TvSeriesList {
        if (forceFetchFromRemote) {
            val apiRes = tvSeriesApi.searchTvSeries(searchTerm, page)
            val tvSeriesEntities = apiRes.results?.filterNotNull()?.let {
                it.map { tvSeriesDto -> tvSeriesDto.toTvSeriesEntity() }
            } ?: listOf()
            tvSeriesDatabase.tvSeriesDao.upsertTvSeriesList(tvSeriesEntities)
            return TvSeriesList(
                tvSeriesEntities.map { it.toTvSeries() },
                apiRes.total_pages
            )
        } else {
            val list = tvSeriesDatabase.tvSeriesDao.filterTvSeries(searchTerm)
            return TvSeriesList(
                list.map { tvSeriesEntity -> tvSeriesEntity.toTvSeries() }
            )
        }
    }
}