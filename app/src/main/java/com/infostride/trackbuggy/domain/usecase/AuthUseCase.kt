package com.infostride.trackbuggy.domain.usecase

import android.util.Log
import com.infostride.trackbuggy.data.model.login.Login
import com.infostride.trackbuggy.data.model.login.LoginResponse
import com.infostride.trackbuggy.domain.repository.TrackDeviceRepository
import com.infostride.trackbuggy.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val repository: TrackDeviceRepository
    ) {

    fun loginUser(email : String, password : String,latitude: String,longitude: String,device_token: String) : Flow<Resource<LoginResponse>> = flow{
        emit(Resource.Loading())
        try {
            val login = Login(email, password,latitude,longitude,device_token)
            val response = repository.loginUser(login)
            Log.i("AuthUseCase","I dey here, ${response.data?.data?.token}")
            emit (response)
        }catch (e : HttpException){
            Log.i("AuthUseCase","${e.localizedMessage}")
            emit (Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }catch (e : IOException){
            Log.i("AuthUseCase","${e.localizedMessage}")
            emit (Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }

}