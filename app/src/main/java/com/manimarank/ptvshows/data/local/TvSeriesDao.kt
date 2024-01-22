package com.manimarank.ptvshows.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.manimarank.ptvshows.data.local.entity.TvSeriesEntity


/**
 * Local db DAO interface for TV Series
 */
@Dao
interface TvSeriesDao {
    @Upsert
    suspend fun upsertTvSeriesList(tvSeriesList: List<TvSeriesEntity>)

    @Query("SELECT * FROM TvSeriesEntity")
    suspend fun getTvSeriesList(): List<TvSeriesEntity>

    @Query("SELECT * FROM TvSeriesEntity WHERE id = :id")
    suspend fun getTvSeriesById(id: Int): TvSeriesEntity?

    @Query("SELECT * FROM TvSeriesEntity WHERE name LIKE '%' || :searchTerm || '%'")
    suspend fun filterTvSeries(searchTerm: String?): List<TvSeriesEntity>
}