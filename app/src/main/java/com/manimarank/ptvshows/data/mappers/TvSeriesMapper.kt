package com.manimarank.ptvshows.data.mappers

import com.manimarank.ptvshows.data.local.entity.TvSeriesEntity
import com.manimarank.ptvshows.data.remote.dto.TvSeriesDto
import com.manimarank.ptvshows.domain.model.TvSeries
import okio.IOException
import retrofit2.HttpException

/**
 * Mapper functions for TV Series items
 */


fun String?.toIntList(): List<Int> {
    return try {
        this?.split(",")?.map { it.toInt() } ?: listOf()
    } catch (e: Exception) {
        listOf()
    }
}

fun List<Any?>?.toStr(): String {
    return try {
        this?.joinToString(",") ?: ""
    } catch (e: Exception) {
        ""
    }
}

fun String?.toStrList(): List<String> {
    return try {
        this?.split(",") ?: listOf()
    } catch (e: Exception) {
        listOf()
    }
}

fun TvSeriesDto.toTvSeriesEntity(): TvSeriesEntity {
    return TvSeriesEntity(
        id = id ?: -1,
        name = name ?: "",
        adult = adult ?: false,
        first_air_date = first_air_date ?: "",
        backdrop_path = backdrop_path ?: "",
        original_language = original_language ?: "",
        overview = overview ?: "",
        poster_path = poster_path ?: "",
        vote_average = vote_average ?: 0.0,
        popularity = popularity ?: 0.0,
        vote_count = vote_count ?: 0,
        original_name = original_name ?: "",
        origin_country = origin_country.toStr(),
        genre_ids = genre_ids.toStr(),
    )
}

fun TvSeriesEntity.toTvSeries(): TvSeries {
    return TvSeries(
        id = id,
        name = name,
        backdrop_path = backdrop_path,
        original_language = original_language,
        overview = overview,
        poster_path = poster_path,
        vote_average = vote_average,
        popularity = popularity,
        vote_count = vote_count,
        adult = adult,
        original_name = original_name,
        origin_country = origin_country.toStrList(),
        genre_ids = genre_ids.toIntList(),
        first_air_date = first_air_date
    )
}

fun Exception.toDisplayError(): String {
    return when(this) {
        is IOException -> "Error loading data"
        is HttpException -> "Error loading data"
        else -> "Something went wrong!"
    }
}