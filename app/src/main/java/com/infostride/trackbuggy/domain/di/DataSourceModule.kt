package com.infostride.trackbuggy.domain.di

import com.infostride.trackbuggy.data.api.ApiService
import com.infostride.trackbuggy.data.repository.datasource.TrackDeviceDataSource
import com.infostride.trackbuggy.data.repository.datasourceImpl.TrackDeviceDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {


    @Singleton
    @Provides
    fun provideShopRemoteDataSource(apiService: ApiService) : TrackDeviceDataSource {
        return TrackDeviceDataSourceImpl(apiService)
    }

}