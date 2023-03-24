package com.infostride.trackbuggy.domain.di

import com.infostride.trackbuggy.data.repository.TrackDeviceRepositoryImpl
import com.infostride.trackbuggy.data.repository.datasource.TrackDeviceDataSource
import com.infostride.trackbuggy.domain.repository.TrackDeviceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun providesTrackDeviceRepository(trackDeviceDataSource: TrackDeviceDataSource):TrackDeviceRepository {
        return TrackDeviceRepositoryImpl(trackDeviceDataSource)
    }
}
