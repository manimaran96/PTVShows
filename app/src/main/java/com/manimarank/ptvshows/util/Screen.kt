package com.manimarank.ptvshows.util

sealed class Screen(val route: String) {
    data object TvSeriesList : Screen("tvSeriesList")
    data object TvSeriesDetails : Screen("tvSeriesDetails")
    data object TvSeriesSearch : Screen("tvSeriesSearch")
}