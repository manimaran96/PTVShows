package com.manimarank.ptvshows.domain.use_case

import com.manimarank.ptvshows.data.mappers.toDisplayError
import com.manimarank.ptvshows.domain.model.TvSeriesList
import com.manimarank.ptvshows.domain.repository.TvSeriesRepository
import com.manimarank.ptvshows.util.AppConstants
import com.manimarank.ptvshows.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Use case class for TV Series List
 */
class TvSeriesListUseCase @Inject constructor(
    private val repository: TvSeriesRepository
) {
    operator fun invoke(page: Int, forceFetchFromRemote: Boolean = false): Flow<Resource<TvSeriesList>> = flow {
        emit(Resource.Loading(true))
        val list = try {
            repository.getTvSeriesList(page = page, forceFetchFromRemote = AppConstants.isOnline && forceFetchFromRemote)
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(message = e.toDisplayError()))
            return@flow
        }
        emit(Resource.Success(list))
        emit(Resource.Loading(false))
        return@flow
    }
}