package com.manimarank.ptvshows.data.remote

import com.manimarank.ptvshows.BuildConfig
import com.manimarank.ptvshows.data.remote.dto.TvSeriesDto
import com.manimarank.ptvshows.data.remote.dto.TvSeriesListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * API interface for TV Series API
 */
interface TvSeriesApi {

    @GET("tv/popular")
    suspend fun getPopularTvSeries(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = Companion.apiKey,
    ): TvSeriesListDto

    @GET("tv/{series_id}")
    suspend fun getTvSeriesDetails(
        @Path("series_id") seriesId: Int,
        @Query("api_key") apiKey: String = Companion.apiKey,
    ): TvSeriesDto?

    companion object {
        const val baseUrl = "https://api.themoviedb.org/3/"
        const val imageBaseUrl = "https://image.tmdb.org/t/p/w500"
        const val apiKey = BuildConfig.API_KEY
    }
}