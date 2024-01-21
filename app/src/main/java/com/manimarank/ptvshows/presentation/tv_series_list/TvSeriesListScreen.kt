package com.manimarank.ptvshows.presentation.tv_series_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.manimarank.ptvshows.presentation.components.AppLoader
import com.manimarank.ptvshows.presentation.components.EmptyWidget
import com.manimarank.ptvshows.presentation.components.ErrorWidget
import com.manimarank.ptvshows.presentation.components.TvSeriesItem

/**
 * TV Series List Screen
 */
@Composable
fun TvSeriesListScreen(
    navController: NavHostController,
) {
    val viewModel = hiltViewModel<TvSeriesListViewModel>()
    val state = viewModel.tvSeriesListState.collectAsState().value

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            when {
                state.isLoading -> {
                    AppLoader()
                }
                state.error != null -> {
                    ErrorWidget(state.error)
                }
                state.tvSeriesList.isEmpty() -> {
                    EmptyWidget()
                }
                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp)
                    ) {
                        items(state.tvSeriesList.size) { index ->
                            TvSeriesItem(
                                tvSeries = state.tvSeriesList[index],
                                navHostController = navController
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }

        }
    }

}