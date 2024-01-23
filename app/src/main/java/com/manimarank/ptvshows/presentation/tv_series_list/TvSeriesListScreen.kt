package com.manimarank.ptvshows.presentation.tv_series_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import com.manimarank.ptvshows.presentation.components.pullrefresh.pullRefresh
import com.manimarank.ptvshows.presentation.components.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.manimarank.ptvshows.R
import com.manimarank.ptvshows.presentation.components.AppLoader
import com.manimarank.ptvshows.presentation.components.EmptyWidget
import com.manimarank.ptvshows.presentation.components.ErrorWidget
import com.manimarank.ptvshows.presentation.components.TvSeriesItem
import com.manimarank.ptvshows.presentation.components.pullrefresh.PullRefreshIndicator
import com.manimarank.ptvshows.util.Screen

/**
 * TV Series List Screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TvSeriesListScreen(
    navController: NavHostController,
) {
    val viewModel = hiltViewModel<TvSeriesListViewModel>()
    val state = viewModel.state.collectAsState().value

    val pullRefreshState = rememberPullRefreshState(state.pullToRefresh, { viewModel.fetchTvSeriesFromRemote() })

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Image(
                            painterResource(R.drawable.ptv_shows_logo),
                            modifier = Modifier.size(40.dp),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(stringResource(id = R.string.popular_tv_shows))
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.TvSeriesSearch.route)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = stringResource(id = R.string.search)
                        )
                    }
                }
            )

        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .pullRefresh(pullRefreshState)
                .fillMaxSize(),
        ) {
            when {
                state.isLoading -> AppLoader()
                state.error != null -> ErrorWidget(state.error)
                state.tvSeriesList.isEmpty() -> EmptyWidget()
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

                            if (index >= state.tvSeriesList.size - 1 && state.page != -1) {
                                viewModel.loadTvSeries()
                            }

                        }

                    }
                }
            }

            PullRefreshIndicator(state.pullToRefresh, pullRefreshState, Modifier.align(Alignment.TopCenter))

        }
    }

}