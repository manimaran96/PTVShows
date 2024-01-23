package com.manimarank.ptvshows.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TvSeriesWithSeasonsAndCast(
    @Embedded val tvSeriesEntity: TvSeriesEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "series_id"
    )
    val seasons: List<SeasonEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "series_id"
    )
    val castList: List<CastEntity>
)