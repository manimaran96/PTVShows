package com.manimarank.ptvshows.util

import com.manimarank.ptvshows.domain.model.TvSeries

/**
 * Extension method to return valid page
 */
fun List<TvSeries>.getValidPage(currentPage: Int, totalMaxPage: Int = 500): Int {
    val paginationSize = 20
    val pageNow = (if (this.isNotEmpty() && (this.size/paginationSize) > currentPage) (this.size/paginationSize) else currentPage) + 1
    return if (pageNow <= totalMaxPage) pageNow else - 1
}