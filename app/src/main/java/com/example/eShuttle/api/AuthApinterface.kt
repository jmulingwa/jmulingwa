package com.example.eShuttle.api

import com.example.eShuttle.models.OtpModel
import com.example.eShuttle.models.PassResetModel
import com.example.eShuttle.models.User
import com.example.eShuttle.responses.LoginResponse
import com.example.eShuttle.responses.PassResetReponse
import com.example.eShuttle.responses.RegisterResponse
import com.example.eShuttle.responses.ValidateResponse
import retrofit2.http.*

interface AuthApinterface {

    @Headers("Content-Type:application/json")
    @POST("api/authenticate")
    suspend fun loginUser(
        @Body() user: User
    ): LoginResponse


    // Register user
    @Headers("Content-Type:application/json")
    @POST("api/register/riders")
    suspend fun registerUser(
        @Body() user: User
    ): RegisterResponse

    //Sms validate
    @Headers("Content-Type:application/json")
    @PUT("api/validate/riders")
    suspend fun otpValidate(
        @Body() otpModel: OtpModel
    ): ValidateResponse


    //request reset with email
    @Headers("Content-Type:application/json")
    @POST("api/resetpwd/init")
    suspend fun requestPasswordResetWithPhone(
        @Body() passResetModel: PassResetModel
    ): PassResetReponse


    // password reset finish
    @Headers("Content-Type: application/json")
    @POST("api/resetpwd/finish")
    suspend fun finishPasswordReset(
       @Body() passResetModel: PassResetModel
    ): PassResetReponse


}