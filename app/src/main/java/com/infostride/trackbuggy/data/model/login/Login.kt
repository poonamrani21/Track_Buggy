package com.infostride.trackbuggy.data.model.login

data class Login(
    val email: String,
    val password: String,
    val latitude: String,
    val longitude: String,
    val device_token: String
)