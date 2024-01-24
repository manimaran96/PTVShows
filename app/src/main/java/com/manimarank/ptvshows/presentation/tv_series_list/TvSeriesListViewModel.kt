package com.manimarank.ptvshows.presentation.tv_series_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manimarank.ptvshows.domain.use_case.TvSeriesListUseCase
import com.manimarank.ptvshows.util.NetworkManger
import com.manimarank.ptvshows.util.Resource
import com.manimarank.ptvshows.util.getValidPage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * View model for TV Series List
 */
@HiltViewModel
class TvSeriesListViewModel @Inject constructor(
    private val tvSeriesListUseCase: TvSeriesListUseCase,
    private val networkManger: NetworkManger
) : ViewModel() {

    private var _state = MutableStateFlow(TvSeriesListState())
    val state = _state.asStateFlow()

    init {
        getPopularTvSeriesList(networkManger.isConnected())
    }

    fun fetchTvSeriesFromRemote() {
        _state.update {
            it.copy(
                page = 1,
                tvSeriesList = emptyList(),
                networkDisconnected = !networkManger.isConnected()
            )
        }
        if (networkManger.isConnected())
            getPopularTvSeriesList(true)
    }

    fun loadTvSeries() {
       if (networkManger.isConnected())
           getPopularTvSeriesList(true)
    }


    private fun getPopularTvSeriesList(forceFetchFromRemote: Boolean) {
        tvSeriesListUseCase(_state.value.page, forceFetchFromRemote = forceFetchFromRemote).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }

                is Resource.Success -> {
                    result.data?.let { tvSeriesList ->
                        _state.update {
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
                    if (_state.value.tvSeriesList.isEmpty() || state.value.isLoading) {
                        _state.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                }
            }
        }.launchIn(viewModelScope)
    }


}