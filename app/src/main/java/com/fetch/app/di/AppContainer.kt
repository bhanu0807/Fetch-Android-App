package com.fetch.app.di

import com.fetch.app.service.FetchApiService
import com.fetch.app.repository.NetworkRewardsRepository
import com.fetch.app.repository.RewardsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppContainer {
    private const val BASE_URL = "https://fetch-hiring.s3.amazonaws.com/"

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideFetchApiService(retrofit: Retrofit): FetchApiService =
        retrofit.create(FetchApiService::class.java)

    @Provides
    @Singleton
    fun provideRewardRepository(apiService: FetchApiService): RewardsRepository =
        NetworkRewardsRepository(apiService)
}

