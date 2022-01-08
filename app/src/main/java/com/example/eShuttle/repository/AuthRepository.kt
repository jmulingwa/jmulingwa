package com.example.eShuttle.repository

import com.example.eShuttle.api.AuthApinterface
import com.example.eShuttle.datastore.UserPreferences
import com.example.eShuttle.models.OtpModel
import com.example.eShuttle.models.PassResetModel
import com.example.eShuttle.models.User

class AuthRepository(
    private val api: AuthApinterface,
    private val userPreferences: UserPreferences
) : BaseRepository() {

    // call the register function
    suspend fun registerUser(
        user: User
    ) = safeApiCall {
        api.registerUser(user)
    }

    // validate otp
    suspend fun otpValidate(
        otp: OtpModel
    ) = safeApiCall {
        api.otpValidate(otp)
    }

    //call the login method in a coroutine scope from the network
    suspend fun loginUser(
        user: User
    ) = safeApiCall {
        api.loginUser(user)
    }

    // save token in a  coroutine scope
    suspend fun saveToken(authToken: String) {
        userPreferences.saveAuthToken(authToken)
    }

    //save phoneNumber to datastore
    suspend fun savePhoneNumber(phoneNumber:String){
        userPreferences.savePhoneNumber(phoneNumber)
    }

    // call api to request  reset with email
    suspend fun requestPasswordResetWithPhone(
        phoneNumber: PassResetModel
    ) = safeApiCall {
        api.requestPasswordResetWithPhone(phoneNumber)
    }

    // call password reset finish
    suspend fun  finishPasswordReset(
        passResetModel: PassResetModel
    )= safeApiCall {
        api.finishPasswordReset(passResetModel)
    }

}