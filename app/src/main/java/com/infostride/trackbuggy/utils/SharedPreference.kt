package com.infostride.trackbuggy.utils

import android.content.SharedPreferences
import com.google.gson.Gson
import com.infostride.trackbuggy.data.model.login.LoginResponse
import com.infostride.trackbuggy.utils.Constants.USER_DATA
import javax.inject.Inject

class SharedPreference @Inject constructor(
    private val sharedPreferences : SharedPreferences
) {

    fun isFirstAppLaunch(): Boolean {
        return sharedPreferences.getBoolean(Constants.IS_FIRST_APP_LAUNCH, true)
    }

    fun saveFirstAppLaunch(value: Boolean) {
        sharedPreferences.edit().putBoolean(Constants.IS_FIRST_APP_LAUNCH, value).apply()
    }

    fun userIsLoggedIn() : Boolean {
        val token = sharedPreferences.getString(Constants.USER_IS_LOGGED_IN, null)
        return token != null
    }

    fun saveUserAccessToken(token: String) {
        sharedPreferences.edit().putString(Constants.USER_IS_LOGGED_IN, token).apply()
    }

    fun deleteAccessToken(): Boolean {
        sharedPreferences.edit().remove(Constants.USER_IS_LOGGED_IN).apply()
        return userIsLoggedIn()
    }

    fun saveUserData(userData: LoginResponse.Data) { sharedPreferences.edit().putString(USER_DATA, Gson().toJson(userData) ).apply() }
    fun getUserData():LoginResponse.Data?
    {
        if (sharedPreferences!!.getString(USER_DATA, "") != "") {
            return Gson().fromJson(sharedPreferences!!.getString(USER_DATA, ""), LoginResponse.Data::class
                .java)
        } else {
            return null
        }
    }
}