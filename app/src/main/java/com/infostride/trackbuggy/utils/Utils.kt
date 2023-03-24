package com.infostride.trackbuggy.utils

import com.infostride.trackbuggy.data.model.login.ValidationResult
import java.util.regex.Pattern


object Utils {
    val emailRegex = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+")

    fun validateLoginRequest(email: String,password: String) : ValidationResult {
        if (email.isBlank() && password.isBlank()) return ValidationResult(false,"Email and password cannot be blank")
        if (email.isBlank()) return ValidationResult(false,"Please enter a email")
        if (!emailRegex.matcher(email).matches()) return ValidationResult(false,"Please enter a valid email")
        if (password.isBlank()) return ValidationResult(false,"Please enter a password")
        return ValidationResult(true)
    }


}