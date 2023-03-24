package com.infostride.trackbuggy.domain.di

import com.infostride.trackbuggy.domain.repository.TrackDeviceRepository
import com.infostride.trackbuggy.domain.usecase.AuthUseCase
import com.infostride.trackbuggy.domain.usecase.ProfileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun providesAuthUseCase(repository: TrackDeviceRepository) : AuthUseCase{
        return AuthUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesProfileUseCase(repository: TrackDeviceRepository) : ProfileUseCase{
        return ProfileUseCase(repository)
    }

}