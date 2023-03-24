package com.infostride.trackbuggy.data.repository

import com.infostride.trackbuggy.data.model.login.Login
import com.infostride.trackbuggy.data.model.login.LoginResponse
import com.infostride.trackbuggy.data.model.user_details.UserDetailsResponse
import com.infostride.trackbuggy.data.repository.datasource.TrackDeviceDataSource
import com.infostride.trackbuggy.domain.repository.TrackDeviceRepository
import com.infostride.trackbuggy.utils.Resource
import retrofit2.Response
import javax.inject.Inject

class TrackDeviceRepositoryImpl @Inject constructor(
    private val remoteDataSource: TrackDeviceDataSource
) : TrackDeviceRepository {

    override suspend fun loginUser(login: Login): Resource<LoginResponse> {
        return responseToString(remoteDataSource.loginUser(login))
    }

    override suspend fun getUser(): Resource<UserDetailsResponse> {
        return responseToUserResult(remoteDataSource.getUser())

    }

    override suspend fun updateUser(
        user: UserDetailsResponse.User
    ): Resource<UserDetailsResponse> {
        return responseToUserResult(remoteDataSource.updateUser(user))
    }

    private fun responseToString(response: Response<LoginResponse>) : Resource<LoginResponse>{
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(message = "${response.errorBody()?.string()}")
    }

    private fun responseToUserResult(response : Response<UserDetailsResponse>) : Resource<UserDetailsResponse>{
        if (response.isSuccessful){
            response.body()?.let { result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(message = "${response.errorBody()?.string()}")
    }



}