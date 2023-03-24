package com.infostride.trackbuggy.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.infostride.trackbuggy.data.model.user_details.UserDetailsResponse
import com.infostride.trackbuggy.domain.usecase.ProfileUseCase
import com.infostride.trackbuggy.utils.Resource
import com.infostride.trackbuggy.utils.SharedPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase,
    private val sharedPreference: SharedPreference
) : ViewModel() {

    val user : MutableLiveData<Resource<UserDetailsResponse>> = MutableLiveData()

    fun getUser() = viewModelScope.launch(IO) {
        user.postValue(Resource.Loading())
        try {
            val apiResult = profileUseCase.getUser()
            user.postValue(apiResult)
        }catch (e : Exception){
            user.postValue(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    fun logoutUser() = viewModelScope.launch {
        sharedPreference.deleteAccessToken()
    }


}