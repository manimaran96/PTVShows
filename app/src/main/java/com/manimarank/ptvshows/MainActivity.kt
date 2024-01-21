package com.manimarank.ptvshows

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.manimarank.ptvshows.presentation.tv_series_details.TvSeriesDetailsScreen
import com.manimarank.ptvshows.presentation.tv_series_list.TvSeriesListScreen
import com.manimarank.ptvshows.ui.theme.PTVShowsTheme
import com.manimarank.ptvshows.util.AppConstants
import com.manimarank.ptvshows.util.Screen
import dagger.hilt.android.AndroidEntryPoint

/**
 * App Main Activity
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PTVShowsTheme {
                CustomSystemBarsTheme(!isSystemInDarkTheme())
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PTVShowNavigation()
                }
            }
        }
    }

    /**
     * Custom system bar theme
     */
    @Composable
    private fun CustomSystemBarsTheme(lightTheme: Boolean) {
        val barColor = MaterialTheme.colorScheme.background.toArgb()
        LaunchedEffect(lightTheme) {
            if (lightTheme) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.light(
                        barColor, barColor,
                    ),
                    navigationBarStyle = SystemBarStyle.light(
                        barColor, barColor,
                    ),
                )
            } else {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.dark(
                        barColor,
                    ),
                    navigationBarStyle = SystemBarStyle.dark(
                        barColor,
                    ),
                )
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

        composable(
            Screen.TvSeriesDetails.route + "/{${AppConstants.keyTvSeriesId}}",
            arguments = listOf(
                navArgument(AppConstants.keyTvSeriesId) { type = NavType.IntType }
            )
        ) {
            TvSeriesDetailsScreen()
        }
    }
}