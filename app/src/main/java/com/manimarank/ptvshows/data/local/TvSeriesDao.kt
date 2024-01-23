package com.manimarank.ptvshows.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.manimarank.ptvshows.data.local.entity.SeasonEntity
import com.manimarank.ptvshows.data.local.entity.TvSeriesEntity
import com.manimarank.ptvshows.data.local.entity.TvSeriesWithSeasons


/**
 * Local db DAO interface for TV Series
 */
@Dao
interface TvSeriesDao {
    @Upsert
    suspend fun upsertTvSeriesList(tvSeriesList: List<TvSeriesEntity>)

    @Upsert
    suspend fun upsertTvSeries(tvSeriesEntity: TvSeriesEntity)

    @Query("SELECT * FROM TvSeriesEntity ORDER BY id DESC")
    suspend fun getTvSeriesList(): List<TvSeriesEntity>

    @Transaction
    @Query("SELECT * FROM TvSeriesEntity WHERE id = :id")
    suspend fun getTvSeriesById(id: Int): TvSeriesWithSeasons?

    @Query("SELECT * FROM TvSeriesEntity WHERE name LIKE '%' || :searchTerm || '%'")
    suspend fun filterTvSeries(searchTerm: String?): List<TvSeriesEntity>

    @Upsert
    suspend fun upsertSeasonsForSeries(seasons: List<SeasonEntity>)
}