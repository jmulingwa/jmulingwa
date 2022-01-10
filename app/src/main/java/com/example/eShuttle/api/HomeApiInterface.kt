package com.example.eShuttle.api
import com.example.eShuttle.models.BookingModel
import com.example.eShuttle.models.User
import com.example.eShuttle.responses.BookingResponse
import com.example.eShuttle.responses.ProfileResponse
import com.example.eShuttle.responses.getUserResponse
import okhttp3.ResponseBody
import retrofit2.http.*

interface HomeApiInterface {

    //get user
    @GET("api/account")
    suspend fun getUser(): getUserResponse

    //Logout user
    @GET("api/logout")
    suspend fun  logout(): ResponseBody

    //booking
    @Headers("Content-Type: application/json")
    @POST("api/riders/bookride")
    suspend fun  bookRide(
        @Body() bookingModel: BookingModel
    ): BookingResponse

    //Update profile
    @Headers("Content-Type: application/json")
    @POST("api/rider/profile")
    suspend fun updateUserProfile(
        @Body() user :  User
    ): ProfileResponse


}