package com.manimarank.ptvshows.di

import android.app.Application
import androidx.room.Room
import com.manimarank.ptvshows.data.local.TvSeriesDatabase
import com.manimarank.ptvshows.data.remote.TvSeriesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * App Module DI methods
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    fun provideTvSeriesApi(): TvSeriesApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(TvSeriesApi.baseUrl)
            .client(client)
            .build()
            .create(TvSeriesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTvSeriesDataBase(application: Application): TvSeriesDatabase {
        return Room.databaseBuilder(
            application,
            TvSeriesDatabase::class.java,
            "tv_series.db"
        ).build()
    }
}