package com.manimarank.ptvshows.presentation.tv_series_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manimarank.ptvshows.domain.repository.TvSeriesRepository
import com.manimarank.ptvshows.util.Resource
import com.manimarank.ptvshows.util.getValidPage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * View model for TV Series List
 */
@HiltViewModel
class TvSeriesListViewModel @Inject constructor(
    private val tvSeriesRepository: TvSeriesRepository
) : ViewModel() {

    private var _tvSeriesListState = MutableStateFlow(TvSeriesListState())
    val tvSeriesListState = _tvSeriesListState.asStateFlow()

    init {
        getPopularTvSeriesList(false)
    }

    fun fetchTvSeriesFromRemote() {
        _tvSeriesListState.update {
            it.copy(
                page = 1,
                tvSeriesList = emptyList()
            )
        }
        getPopularTvSeriesList(true)
    }

    fun loadTvSeries() {
       getPopularTvSeriesList(true)
    }


    private fun getPopularTvSeriesList(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            tvSeriesRepository.getTvSeriesList(
                forceFetchFromRemote,
                _tvSeriesListState.value.page
            ).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _tvSeriesListState.update {
                            it.copy(
                                isLoading = false,
                                error = result.message
                            )
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { tvSeriesList ->
                            _tvSeriesListState.update {
                                val fullList = it.tvSeriesList + tvSeriesList.tvSeriesList
                                it.copy(
                                    tvSeriesList = fullList,
                                    page = fullList.getValidPage(it.page)
                                )
                            }
                        }
                    }

                    is Resource.Loading -> {
                        // Hiding full screen loader while paginating
                        if (_tvSeriesListState.value.tvSeriesList.isEmpty() || tvSeriesListState.value.isLoading) {
                            _tvSeriesListState.update {
                                it.copy(isLoading = result.isLoading)
                            }
                        }
                    }
                }
            }
        }
    }


}