package com.infostride.trackbuggy.data.api

import com.infostride.trackbuggy.data.model.login.Login
import com.infostride.trackbuggy.data.model.login.LoginResponse
import com.infostride.trackbuggy.data.model.user_details.UserDetailsResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("login")
    suspend fun loginUser(@Body login : Login): Response<LoginResponse>



    @GET("profile")
    suspend fun getUser() : Response<UserDetailsResponse>

    @PUT("update-profile")
    suspend fun updateUser( @Body user: UserDetailsResponse.User) : Response<UserDetailsResponse>

}