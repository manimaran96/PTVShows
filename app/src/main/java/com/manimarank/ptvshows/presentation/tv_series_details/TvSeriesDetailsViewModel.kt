package com.manimarank.ptvshows.presentation.tv_series_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manimarank.ptvshows.domain.repository.TvSeriesRepository
import com.manimarank.ptvshows.util.AppConstants
import com.manimarank.ptvshows.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * View model for TV Series Details
 */
@HiltViewModel
class TvSeriesDetailsViewModel @Inject constructor(
    private val tvSeriesRepository: TvSeriesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val tvSeriesId = savedStateHandle.get<Int>(AppConstants.keyTvSeriesId)

    private var _state = MutableStateFlow(TvSeriesDetailsState())
    val state = _state.asStateFlow()

    init {
        getTvSeries(tvSeriesId ?: -1)
    }

    private fun getTvSeries(id: Int) {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }

            tvSeriesRepository.getTvSeries(id).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = result.message
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _state.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { tvSeries ->
                            _state.update {
                                it.copy(tvSeries = tvSeries)
                            }
                        }
                    }
                }
            }
        }
    }
}