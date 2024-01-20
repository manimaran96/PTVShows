package com.manimarank.ptvshows.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.manimarank.ptvshows.data.local.entity.TvSeriesEntity


/**
 * Room data base for TV Series
 */
@Database(
    entities = [TvSeriesEntity::class],
    version = 1
)
abstract class TvSeriesDatabase: RoomDatabase() {
    abstract val tvSeriesDao: TvSeriesDao
}