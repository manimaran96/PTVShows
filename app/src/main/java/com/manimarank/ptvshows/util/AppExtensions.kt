package com.manimarank.ptvshows.util

import android.content.Context
import android.content.res.Configuration
import com.manimarank.ptvshows.domain.model.TvSeries

/**
 * Extension method to return valid page
 */
fun List<TvSeries>.getValidPage(currentPage: Int, totalMaxPage: Int = 500): Int {
    val paginationSize = 20
    val pageNow = (if (this.isNotEmpty() && (this.size/paginationSize) > currentPage) (this.size/paginationSize) else currentPage) + 1
    return if (pageNow <= totalMaxPage) pageNow else - 1
}

/**
 * Context extension method to return is Landscape mode or not
 */
fun Context.isLandscape(): Boolean {
    val orientation = resources.configuration.orientation
    return orientation == Configuration.ORIENTATION_LANDSCAPE
}

/**
 * Context extension method to return max grid row items count based on orientation
 */
fun Context.gridItemRowCount(): Int {
    return if (isLandscape()) 5 else 2
}