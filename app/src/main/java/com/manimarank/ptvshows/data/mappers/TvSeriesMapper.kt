package com.manimarank.ptvshows.data.mappers

import com.manimarank.ptvshows.data.local.entity.TvSeriesEntity
import com.manimarank.ptvshows.data.remote.dto.TvSeriesDto
import com.manimarank.ptvshows.domain.model.TvSeries
import okio.IOException
import retrofit2.HttpException

/**
 * Mapper functions for TV Series items
 */

private const val dataSplitter = "|"

fun List<Any?>?.toStr(): String {
    return try {
        this?.joinToString(dataSplitter) ?: ""
    } catch (e: Exception) {
        ""
    }
}

fun String?.toStrList(): List<String> {
    return try {
        this?.split(dataSplitter) ?: listOf()
    } catch (e: Exception) {
        listOf()
    }
}

fun TvSeriesDto.toTvSeriesEntity(): TvSeriesEntity {
    return TvSeriesEntity(
        id = id ?: -1,
        name = name ?: "",
        adult = adult ?: false,
        backdrop_path = backdrop_path ?: "",
        poster_path = poster_path ?: "",
        overview = overview ?: "",
        popularity = popularity ?: 0.0,
        vote_average = vote_average ?: 0.0,
        vote_count = vote_count ?: 0,
        first_air_date = first_air_date ?: "",
        origin_country = origin_country.toStr(),
        original_name = original_name ?: "",
        original_language = original_language ?: "",
        tagline = tagline ?: "",
        type = type ?: "",
        status = status ?: "",
        number_of_episodes = number_of_episodes ?: 0,
        number_of_seasons = number_of_seasons ?: 0,
        languages = languages.toStr(),
        genres = genres?.map { it?.name ?: "" }.toStr(),
        homepage = homepage ?: "",
        last_air_date = last_air_date ?: "",
        in_production = in_production ?: false,
        networks = networks?.map { it?.name ?: "" }?.toStr(),
        spoken_languages = spoken_languages?.map { it?.english_name ?: "" }?.toStr(),
        production_companies = production_companies?.map { it?.name ?: "" }?.toStr(),
        production_countries = production_countries?.map { it?.name ?: "" }?.toStr(),
    )
}

fun TvSeriesEntity.toTvSeries(): TvSeries {
    return TvSeries(
        id = id,
        name = name,
        adult = adult,
        backdrop_path = backdrop_path,
        poster_path = poster_path,
        overview = overview,
        popularity = popularity,
        vote_average = vote_average,
        vote_count = vote_count,
        first_air_date = first_air_date,
        origin_country = origin_country.toStrList(),
        original_name = original_name,
        original_language = original_language,
        tagline = tagline,
        type = type,
        status = status,
        number_of_episodes = number_of_episodes,
        number_of_seasons = number_of_seasons,
        languages = languages.toStrList(),
        genres = genres.toStrList(),
        homepage = homepage,
        last_air_date = last_air_date,
        in_production = in_production,
        networks = networks.toStrList(),
        spoken_languages = spoken_languages.toStrList(),
        production_companies = production_companies.toStrList(),
        production_countries = production_countries.toStrList()
    )
}

fun Exception.toDisplayError(): String {
    return when(this) {
        is IOException -> "Error loading data"
        is HttpException -> "Error loading data"
        else -> "Something went wrong!"
    }
}