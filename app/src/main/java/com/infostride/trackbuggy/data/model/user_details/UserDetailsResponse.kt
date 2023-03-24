package com.infostride.trackbuggy.data.model.user_details


import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class UserDetailsResponse(
    val `data`: User,
    val message: String,
    val status: Boolean
) : Parcelable {
    @Parcelize
    data class User(
        val address: String,
        val city: City,
        val country: Country,
        val country_id: Int,
        val created_at: String,
        val deleted_at: String,
        val devices: List<String>,
        val dob: String,
        val email: String,
        val email_verified_at: String,
        val fax: String,
        val first_name: String,
        val full_name: String,
        val id: Int,
        val is_active: Boolean,
        val last_name: String,
        val phone_number: String,
        val pm_last_four: String,
        val pm_type: String,
        val profile_completed: Boolean,
        val profile_picture: String,
        val state: State,
        val state_id: Int,
        val status: Int,
        val status_label: String,
        val stripe_id: String,
        val trial_ends_at: String,
        val updated_at: String,
        val zip_code: String
    ) : Parcelable {
        @Parcelize
        data class City(
            val id: Int,
            val name: String
        ) : Parcelable

        @Parcelize
        data class Country(
            val id: Int,
            val name: String
        ) : Parcelable

        @Parcelize
        data class State(
            val id: Int,
            val name: String
        ) : Parcelable
    }
}