package com.example.eShuttle.repository

import com.example.eShuttle.api.HomeApiInterface
import com.example.eShuttle.models.BookingModel
import com.example.eShuttle.models.User

class UserRepository(
    private val apiInterface: HomeApiInterface
) : BaseRepository() {

    // get user data
    suspend fun getUser() = safeApiCall {
        apiInterface.getUser()
    }

    suspend fun  bookRide(
        bookingModel: BookingModel
    ) = safeApiCall {
        apiInterface.bookRide(bookingModel)
    }

    suspend fun updateUserProfile(
        user: User
    ) = safeApiCall {
        apiInterface.updateUserProfile(user)
    }


}