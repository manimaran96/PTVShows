package com.manimarank.ptvshows.presentation.tv_series_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

/**
 * TV Series List Screen
 */
@Composable
fun TvSeriesListScreen(
    navController: NavHostController,
) {
    val tvSeriesListViewModel = hiltViewModel<TvSeriesListViewModel>()
    val tvSeriesListState = tvSeriesListViewModel.tvSeriesListState.collectAsState().value

    if (tvSeriesListState.tvSeriesList.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp)
        ) {
            items(tvSeriesListState.tvSeriesList.size) { index ->
                Text(
                    tvSeriesListState.tvSeriesList[index].name ?: ""
                )
                Spacer(modifier = Modifier.height(16.dp))

            }
        }
    }

}