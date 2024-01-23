package com.manimarank.ptvshows.data.mappers

import com.manimarank.ptvshows.data.local.entity.CastEntity
import com.manimarank.ptvshows.data.local.entity.SeasonEntity
import com.manimarank.ptvshows.data.local.entity.TvSeriesEntity
import com.manimarank.ptvshows.data.remote.dto.TvSeriesDto
import com.manimarank.ptvshows.data.remote.dto.details.CastDto
import com.manimarank.ptvshows.data.remote.dto.details.SeasonDto
import com.manimarank.ptvshows.domain.model.Cast
import com.manimarank.ptvshows.domain.model.Season
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

fun TvSeriesEntity.toTvSeries(seasonEntities: List<SeasonEntity>? = null, castListEntity: List<CastEntity>? = null): TvSeries {
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
        production_countries = production_countries.toStrList(),
        seasons = seasonEntities?.map { seasonEntity ->  seasonEntity.toSeason() },
        cast_list = castListEntity?.map { castEntity ->  castEntity.toCast() }
    )
}

fun SeasonDto.toSeasonEntity(seriesId: Int): SeasonEntity {
    return  SeasonEntity(
        id = id,
        name = name,
        poster_path = poster_path,
        season_number = season_number,
        air_date = air_date,
        episode_count = episode_count,
        overview = overview,
        vote_average = vote_average,
        series_id = seriesId
    )
}

fun SeasonEntity.toSeason(): Season {
    return  Season(
        id = id,
        name = name,
        poster_path = poster_path,
        season_number = season_number,
        air_date = air_date,
        episode_count = episode_count,
        overview = overview,
        vote_average = vote_average
    )
}

fun CastDto.toCastEntity(seriesId: Int): CastEntity {
    return  CastEntity(
        id = id,
        name = name,
        profile_path = profile_path,
        character = character,
        order = order,
        adult = adult,
        credit_id = credit_id,
        gender = gender,
        known_for_department = known_for_department,
        original_name = original_name,
        popularity = popularity,
        series_id = seriesId
    )
}

fun CastEntity.toCast(): Cast {
    return  Cast(
        id = id,
        name = name,
        profile_path = profile_path,
        character = character,
        order = order,
        adult = adult,
        credit_id = credit_id,
        gender = gender,
        known_for_department = known_for_department,
        original_name = original_name,
        popularity = popularity,
    )
}


fun Exception.toDisplayError(): String {
    return when(this) {
        is IOException -> "Error loading data"
        is HttpException -> "Error loading data"
        else -> "Something went wrong!"
    }
}