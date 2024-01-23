package com.manimarank.ptvshows.data.repository

import com.manimarank.ptvshows.data.local.TvSeriesDatabase
import com.manimarank.ptvshows.data.local.entity.SeasonEntity
import com.manimarank.ptvshows.data.local.entity.TvSeriesEntity
import com.manimarank.ptvshows.data.mappers.toDisplayError
import com.manimarank.ptvshows.data.mappers.toSeasonEntity
import com.manimarank.ptvshows.data.mappers.toTvSeries
import com.manimarank.ptvshows.data.mappers.toTvSeriesEntity
import com.manimarank.ptvshows.data.remote.TvSeriesApi
import com.manimarank.ptvshows.domain.model.TvSeries
import com.manimarank.ptvshows.domain.model.TvSeriesList
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
    ): Flow<Resource<TvSeriesList>> {
        return flow {
            emit(Resource.Loading(true))
            val localTvSeriesList = tvSeriesDatabase.tvSeriesDao.getTvSeriesList()
            val shouldLoadFromLocal = localTvSeriesList.isNotEmpty() && !forceFetchFromRemote

            if (shouldLoadFromLocal) {
                emit(Resource.Success(
                    TvSeriesList(
                        localTvSeriesList.map { tvSeriesEntity -> tvSeriesEntity.toTvSeries() }
                    )
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
                TvSeriesList(
                    tvSeriesEntities.map { it.toTvSeries() },
                    tvSeriesListFromApi.total_pages
                )
            ))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getTvSeries(id: Int, forceFetchFromRemote: Boolean): Flow<Resource<TvSeries>> {
        return flow {

            emit(Resource.Loading(true))

            val tvSeriesEntity: TvSeriesEntity?
            val seasonsEntity: List<SeasonEntity>?
            if (forceFetchFromRemote) {
                val tvSeriesFromApi = try {
                    tvSeriesApi.getTvSeriesDetails(id)
                } catch (e: Exception) {
                    e.printStackTrace()
                    emit(Resource.Error(message = e.toDisplayError()))
                    return@flow
                }
                tvSeriesEntity = tvSeriesFromApi?.toTvSeriesEntity()
                seasonsEntity = tvSeriesFromApi?.seasons?.mapNotNull { seasonDto -> seasonDto?.toSeasonEntity(tvSeriesEntity?.id ?: -1) }

                // Update in local
                tvSeriesEntity?.let { tvSeriesDatabase.tvSeriesDao.upsertTvSeries(it) }
                seasonsEntity?.let { tvSeriesDatabase.tvSeriesDao.upsertSeasonsForSeries(seasonsEntity) }

            } else {
                val tvSeriesWithSeasonEntity = tvSeriesDatabase.tvSeriesDao.getTvSeriesById(id)
                tvSeriesEntity = tvSeriesWithSeasonEntity?.tvSeriesEntity
                seasonsEntity = tvSeriesWithSeasonEntity?.seasons
            }

            val tvSeries: TvSeries? = tvSeriesEntity?.toTvSeries(seasonsEntity)

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
    ): Flow<Resource<TvSeriesList>> {
        return flow {
            emit(Resource.Loading(true))
            val localTvSeriesList = tvSeriesDatabase.tvSeriesDao.filterTvSeries(searchTerm)
            val shouldLoadFromLocal = localTvSeriesList.isNotEmpty() && !forceFetchFromRemote

            if (shouldLoadFromLocal) {
                emit(Resource.Success(
                    TvSeriesList(
                        localTvSeriesList.map { tvSeriesEntity -> tvSeriesEntity.toTvSeries() },
                    )
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
                TvSeriesList(
                    tvSeriesEntities.map { it.toTvSeries() },
                    totalPage = tvSeriesListFromApi.total_pages
                )
            ))
            emit(Resource.Loading(false))
        }
    }
}