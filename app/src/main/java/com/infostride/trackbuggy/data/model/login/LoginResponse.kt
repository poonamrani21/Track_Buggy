package com.infostride.trackbuggy.data.model.login

data class LoginResponse(
    val `data`: Data,
    val message: String,
    val status: Boolean
) {
    data class Data(
        val email: String,
        val first_name: String,
        val id: Int,
        val last_name: String,
        val token: String
    )
}