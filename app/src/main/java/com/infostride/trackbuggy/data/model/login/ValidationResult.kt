package com.infostride.trackbuggy.data.model.login

data class ValidationResult(
    val successful: Boolean,
    val error: String? = null
)
