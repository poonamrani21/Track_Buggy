package com.infostride.trackbuggy.data.repository.datasource

import com.infostride.trackbuggy.data.model.login.Login
import com.infostride.trackbuggy.data.model.login.LoginResponse
import com.infostride.trackbuggy.data.model.user_details.UserDetailsResponse
import retrofit2.Response

interface TrackDeviceDataSource {

    suspend fun loginUser(login : Login): Response<LoginResponse>
    suspend fun getUser() : Response<UserDetailsResponse>
    suspend fun updateUser(user: UserDetailsResponse.User) : Response<UserDetailsResponse>
}