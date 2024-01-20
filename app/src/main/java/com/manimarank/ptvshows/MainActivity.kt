package com.manimarank.ptvshows

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.manimarank.ptvshows.presentation.tv_series_list.TvSeriesListScreen
import com.manimarank.ptvshows.presentation.tv_series_list.TvSeriesListViewModel
import com.manimarank.ptvshows.ui.theme.PTVShowsTheme
import com.manimarank.ptvshows.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PTVShowsTheme {
                val viewModel = hiltViewModel<TvSeriesListViewModel>()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PTVShowNavigation()
                }
            }
        }
    }
}


/**
 * App Navigation Host
 */
@Composable
fun PTVShowNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.TvSeriesList.route
    ) {
        composable(Screen.TvSeriesList.route) {
            TvSeriesListScreen(
                navController = navController,
            )
        }
    }
}