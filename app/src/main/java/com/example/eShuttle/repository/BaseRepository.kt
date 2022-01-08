package com.example.eShuttle.repository

import com.example.eShuttle.api.HomeApiInterface
import com.example.eShuttle.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

//making abstract avoids creating the instance of the repository directly
// but inherit this class to the actual concrete repository class
abstract class BaseRepository {

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                //Handle errors
                when (throwable) {
                    is HttpException -> {
                        Resource.Error(false, throwable.code(), throwable.response()?.errorBody())
                    }
                    else -> {
                        Resource.Error(true, null, null)
                    }
                }
            }
        }
    }
    //We define it here ato enable a user to be able to logout from any screen
    // because we'll call this function in baseviewModel
    suspend fun  logout(api: HomeApiInterface) = safeApiCall {
        api.logout()
    }
}