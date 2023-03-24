package com.infostride.trackbuggy.data.repository.datasourceImpl

import com.infostride.trackbuggy.data.api.ApiService
import com.infostride.trackbuggy.data.model.login.Login
import com.infostride.trackbuggy.data.model.login.LoginResponse
import com.infostride.trackbuggy.data.model.user_details.UserDetailsResponse
import com.infostride.trackbuggy.data.repository.datasource.TrackDeviceDataSource
import retrofit2.Response
import javax.inject.Inject

class TrackDeviceDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : TrackDeviceDataSource {

    override suspend fun loginUser(login: Login): Response<LoginResponse> { return apiService.loginUser(login) }
    override suspend fun getUser(): Response<UserDetailsResponse> {
      return  apiService.getUser()
    }

    override suspend fun updateUser(user: UserDetailsResponse.User): Response<UserDetailsResponse> {
       return  apiService.updateUser(user)
    }

}