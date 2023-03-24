package com.infostride.trackbuggy.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.infostride.trackbuggy.utils.SharedPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    sharedPreference: SharedPreference
) : ViewModel() {

    val loggedIn : Boolean = sharedPreference.userIsLoggedIn()

}