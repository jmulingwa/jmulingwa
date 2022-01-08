package com.example.eShuttle.api
import com.example.eShuttle.models.BookingModel
import com.example.eShuttle.responses.BookingResponse
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
    @POST("api/riders/getquote")
    suspend fun  bookRide(
        @Body() bookingModel: BookingModel
    ): BookingResponse


}