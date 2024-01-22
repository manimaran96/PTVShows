package com.manimarank.ptvshows.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manimarank.ptvshows.domain.repository.TvSeriesRepository
import com.manimarank.ptvshows.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * View model for TV Series Search
 */
@HiltViewModel
class TvSeriesSearchViewModel @Inject constructor(
    private val tvSeriesRepository: TvSeriesRepository
) : ViewModel() {

    private var _state = MutableStateFlow(TvSeriesSearchState())
    val state = _state.asStateFlow()
    fun searchTvSeriesList(searchQuery: String, forceFetchFromRemote: Boolean) {

        _state.update {
            it.copy(
                tvSeriesList = emptyList(),
                isLoading = true
            )
        }

        viewModelScope.launch {
            tvSeriesRepository.searchTvSeries(
                searchQuery,
                forceFetchFromRemote,
                _state.value.page
            ).collectLatest { result ->
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
                                it.copy(
                                    tvSeriesList = _state.value.tvSeriesList + tvSeriesList,
                                    page = _state.value.page + 1
                                )
                            }
                        }
                    }

                    is Resource.Loading -> {
                        _state.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                }
            }
        }
    }

}