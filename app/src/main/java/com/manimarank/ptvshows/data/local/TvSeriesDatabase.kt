package com.manimarank.ptvshows.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.manimarank.ptvshows.data.local.entity.CastEntity
import com.manimarank.ptvshows.data.local.entity.SeasonEntity
import com.manimarank.ptvshows.data.local.entity.TvSeriesEntity


/**
 * Room data base for TV Series
 */
@Database(
    entities = [TvSeriesEntity::class, SeasonEntity::class, CastEntity::class],
    version = 1
)
abstract class TvSeriesDatabase: RoomDatabase() {
    abstract val tvSeriesDao: TvSeriesDao
}