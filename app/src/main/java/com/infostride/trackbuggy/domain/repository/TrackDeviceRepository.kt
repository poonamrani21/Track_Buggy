package com.infostride.trackbuggy.domain.repository

import com.infostride.trackbuggy.data.model.login.Login
import com.infostride.trackbuggy.data.model.login.LoginResponse
import com.infostride.trackbuggy.data.model.user_details.UserDetailsResponse
import com.infostride.trackbuggy.utils.Resource


interface TrackDeviceRepository {

    suspend fun loginUser(login: Login) : Resource<LoginResponse>
    suspend fun getUser() : Resource<UserDetailsResponse>
    suspend fun updateUser(user: UserDetailsResponse.User) : Resource<UserDetailsResponse>
}