package com.manimarank.ptvshows.presentation.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.manimarank.ptvshows.R
import com.manimarank.ptvshows.presentation.components.AppLoader
import com.manimarank.ptvshows.presentation.components.EmptyWidget
import com.manimarank.ptvshows.presentation.components.ErrorWidget
import com.manimarank.ptvshows.presentation.components.SearchAskWidget
import com.manimarank.ptvshows.presentation.components.TvSeriesItem

/**
 * TV Series Search Screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TvSeriesSearchScreen(
    navController: NavHostController,
) {
    val viewModel = hiltViewModel<TvSeriesSearchViewModel>()
    val state = viewModel.state.collectAsState().value

    var searchQuery by remember { mutableStateOf("") }
    var searchActive by remember { mutableStateOf(false) }

    Scaffold (
        topBar = {
            val searchBarPadding = if (searchActive) PaddingValues(0.dp) else PaddingValues(horizontal = 16.dp, vertical = 4.dp)
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(searchBarPadding),
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = { newQuery ->
                    searchActive = false
                    searchQuery = newQuery
                    viewModel.searchTvSeriesList(searchQuery = searchQuery, true)
                },
                placeholder = { Text(text = stringResource(R.string.search_here)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    if (searchActive) {
                        Icon(
                            modifier = Modifier.clickable {
                                if (searchQuery.isNotEmpty()) {
                                    searchQuery = ""
                                } else {
                                    searchActive = false
                                }
                            },
                            imageVector = Icons.Default.Clear,
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = null
                        )
                    }
                },
                content = {},
                active = searchActive,
                onActiveChange = {
                    searchActive = it
                },
                tonalElevation = 8.dp,
            )
        }
    ) {innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {

            when {
                state.isLoading -> AppLoader()
                state.error != null -> ErrorWidget(state.error)
                searchQuery.isEmpty() -> SearchAskWidget()
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
                                viewModel.searchTvSeriesList(searchQuery = searchQuery, true, loadMore = true)
                            }
                        }
                    }
                }
            }
        }
    }

}