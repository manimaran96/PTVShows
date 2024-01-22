package com.manimarank.ptvshows.data.repository

import com.manimarank.ptvshows.data.local.TvSeriesDatabase
import com.manimarank.ptvshows.data.mappers.toDisplayError
import com.manimarank.ptvshows.data.mappers.toTvSeries
import com.manimarank.ptvshows.data.mappers.toTvSeriesEntity
import com.manimarank.ptvshows.data.remote.TvSeriesApi
import com.manimarank.ptvshows.domain.model.TvSeries
import com.manimarank.ptvshows.domain.repository.TvSeriesRepository
import com.manimarank.ptvshows.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Data level Repository Implementation for TV Series
 */
class TvSeriesRepositoryImpl @Inject constructor(
    private val tvSeriesApi: TvSeriesApi,
    private val tvSeriesDatabase: TvSeriesDatabase
): TvSeriesRepository {
    override suspend fun getTvSeriesList(
        forceFetchFromRemote: Boolean,
        page: Int
    ): Flow<Resource<List<TvSeries>>> {
        return flow {
            emit(Resource.Loading(true))
            val localTvSeriesList = tvSeriesDatabase.tvSeriesDao.getTvSeriesList()
            val shouldLoadFromLocal = localTvSeriesList.isNotEmpty() && !forceFetchFromRemote

            if (shouldLoadFromLocal) {
                emit(Resource.Success(
                    data = localTvSeriesList.map { tvSeriesEntity ->
                        tvSeriesEntity.toTvSeries()
                    }
                ))

                emit(Resource.Loading(false))
                return@flow
            }

            val tvSeriesListFromApi = try {
                tvSeriesApi.getPopularTvSeries(page)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = e.toDisplayError()))
                return@flow
            }

            val tvSeriesEntities = tvSeriesListFromApi.results?.filterNotNull()?.let {
                it.map { tvSeriesDto ->
                    tvSeriesDto.toTvSeriesEntity()
                }
            } ?: listOf()

            tvSeriesDatabase.tvSeriesDao.upsertTvSeriesList(tvSeriesEntities)

            emit(Resource.Success(
                tvSeriesEntities.map { it.toTvSeries() }
            ))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getTvSeries(id: Int): Flow<Resource<TvSeries>> {
        return flow {

            emit(Resource.Loading(true))

            val tvSeriesEntity = tvSeriesDatabase.tvSeriesDao.getTvSeriesById(id)

            val tvSeriesFromApi = try {
                tvSeriesApi.getTvSeriesDetails(id)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = e.toDisplayError()))
                return@flow
            }

            val tvSeries: TvSeries? =  (tvSeriesFromApi?.toTvSeriesEntity() ?: tvSeriesEntity)?.toTvSeries()

            if (tvSeries != null) {
                emit(Resource.Success(tvSeries))
                emit(Resource.Loading(false))
                return@flow
            }

            emit(Resource.Error("No record found"))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun searchTvSeries(
        searchTerm: String,
        forceFetchFromRemote: Boolean,
        page: Int
    ): Flow<Resource<List<TvSeries>>> {
        return flow {
            emit(Resource.Loading(true))
            val localTvSeriesList = tvSeriesDatabase.tvSeriesDao.filterTvSeries(searchTerm)
            val shouldLoadFromLocal = localTvSeriesList.isNotEmpty() && !forceFetchFromRemote

            if (shouldLoadFromLocal) {
                emit(Resource.Success(
                    data = localTvSeriesList.map { tvSeriesEntity ->
                        tvSeriesEntity.toTvSeries()
                    }
                ))

                emit(Resource.Loading(false))
                return@flow
            }

            val tvSeriesListFromApi = try {
                tvSeriesApi.searchTvSeries(searchTerm, page)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = e.toDisplayError()))
                return@flow
            }

            val tvSeriesEntities = tvSeriesListFromApi.results?.filterNotNull()?.let {
                it.map { tvSeriesDto ->
                    tvSeriesDto.toTvSeriesEntity()
                }
            } ?: listOf()

            tvSeriesDatabase.tvSeriesDao.upsertTvSeriesList(tvSeriesEntities)

            emit(Resource.Success(
                tvSeriesEntities.map { it.toTvSeries() }
            ))
            emit(Resource.Loading(false))
        }
    }
}