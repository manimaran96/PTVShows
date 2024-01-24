package com.manimarank.ptvshows.domain.use_case

import com.manimarank.ptvshows.data.mappers.toDisplayError
import com.manimarank.ptvshows.domain.model.TvSeries
import com.manimarank.ptvshows.domain.repository.TvSeriesRepository
import com.manimarank.ptvshows.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Use case class for TV Series Details
 */
class TvSeriesDetailsUseCase @Inject constructor(
    private val repository: TvSeriesRepository
) {
    operator fun invoke(seriesId: Int, forceFetchFromRemote: Boolean = true): Flow<Resource<TvSeries>> = flow {
        emit(Resource.Loading(true))
        val tvSeries = try {
            repository.getTvSeries(id = seriesId, forceFetchFromRemote = forceFetchFromRemote)
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(message = e.toDisplayError()))
            return@flow
        }
        emit(Resource.Success(tvSeries))
        emit(Resource.Loading(false))
        return@flow
    }
}