package com.infostride.trackbuggy.domain.usecase

import com.infostride.trackbuggy.data.model.user_details.UserDetailsResponse
import com.infostride.trackbuggy.domain.repository.TrackDeviceRepository
import com.infostride.trackbuggy.utils.Resource
import javax.inject.Inject

class ProfileUseCase @Inject constructor(private val repository: TrackDeviceRepository) {

    suspend fun getUser() : Resource<UserDetailsResponse>{
        return repository.getUser()
    }

    suspend fun updateUser(id : Int,user: UserDetailsResponse.User): Resource<UserDetailsResponse> {
        return repository.updateUser(user)
    }

}