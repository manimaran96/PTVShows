package com.manimarank.ptvshows.presentation.tv_series_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manimarank.ptvshows.domain.use_case.TvSeriesDetailsUseCase
import com.manimarank.ptvshows.util.AppConstants
import com.manimarank.ptvshows.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * View model for TV Series Details
 */
@HiltViewModel
class TvSeriesDetailsViewModel @Inject constructor(
    private val tvSeriesDetailsUseCase: TvSeriesDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val tvSeriesId = savedStateHandle.get<Int>(AppConstants.keyTvSeriesId)

    private var _state = MutableStateFlow(TvSeriesDetailsState())
    val state = _state.asStateFlow()

    init {
        getTvSeries(tvSeriesId ?: -1)
    }

    private fun getTvSeries(id: Int) {
        _state.update {
            it.copy(isLoading = true)
        }
        tvSeriesDetailsUseCase(seriesId = id).onEach { result ->
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
        }.launchIn(viewModelScope)
    }
}