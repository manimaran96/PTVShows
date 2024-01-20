package com.manimarank.ptvshows.di

import com.manimarank.ptvshows.data.repository.TvSeriesRepositoryImpl
import com.manimarank.ptvshows.domain.repository.TvSeriesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Repository Module DI methods
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTvSeriesRepository(
        tvSeriesListRepositoryImpl: TvSeriesRepositoryImpl
    ): TvSeriesRepository
}