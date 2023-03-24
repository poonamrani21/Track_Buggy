package com.infostride.trackbuggy.domain.di

import com.infostride.trackbuggy.domain.usecase.AuthUseCase
import com.infostride.trackbuggy.domain.usecase.ProfileUseCase
import com.infostride.trackbuggy.ui.viewmodel.LoginViewModel
import com.infostride.trackbuggy.ui.viewmodel.ProfileViewModel
import com.infostride.trackbuggy.ui.viewmodel.SplashViewModel
import com.infostride.trackbuggy.utils.SharedPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ViewModelModule {

    @Singleton
    @Provides
    fun provideSplashViewModel(sharedPreference: SharedPreference):SplashViewModel
    {
        return SplashViewModel(sharedPreference)
    }

    @Singleton
    @Provides
    fun providesLoginViewModel(authUseCase: AuthUseCase,sharedPreference: SharedPreference) : LoginViewModel{
        return LoginViewModel(authUseCase,sharedPreference)
    }

    @Singleton
    @Provides
    fun providesProfileViewModel(profileUseCase: ProfileUseCase, sharedPreference: SharedPreference) : ProfileViewModel{
        return ProfileViewModel(profileUseCase,sharedPreference)
    }

}