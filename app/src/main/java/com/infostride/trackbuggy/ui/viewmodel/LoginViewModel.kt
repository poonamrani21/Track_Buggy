package com.infostride.trackbuggy.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.infostride.trackbuggy.data.model.login.LoginResponse
import com.infostride.trackbuggy.domain.usecase.AuthUseCase
import com.infostride.trackbuggy.utils.Resource
import com.infostride.trackbuggy.utils.SharedPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val sharedPrefUtil: SharedPreference,
) : ViewModel(){

    val successful : MutableLiveData<LoginResponse?> = MutableLiveData()
    val error : MutableLiveData<String?> = MutableLiveData()

    private fun saveUserAccessToken(token: String) = sharedPrefUtil.saveUserAccessToken(token)

    fun loginUser(email: String, password: String,latitude: String, longitude: String, device_token: String){
        authUseCase.loginUser(email = email,password= password,latitude, longitude = longitude, device_token = device_token).onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    Log.i("LoginViewModel","I dey here, Loading")
                }
                is Resource.Error -> {
                    error.postValue("${result.message}")
                    successful.postValue(result.data)
                    Log.i("LoginViewModel","I dey here, Error ${result.message}")
                }
                is Resource.Success -> {
                    successful.postValue(result.data)
                    saveUserAccessToken("${result.data?.data?.id}")
                    Log.i("LoginViewModel","I dey here, Success ${result.data?.data?.id}")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun navigated(){
        successful.postValue(null)
        error.postValue(null)
    }

}