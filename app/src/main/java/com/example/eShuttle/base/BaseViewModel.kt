package com.example.eShuttle.base

import androidx.lifecycle.ViewModel
import com.example.eShuttle.api.HomeApiInterface
import com.example.eShuttle.repository.BaseRepository

abstract class BaseViewModel(
    private val repository: BaseRepository,

) : ViewModel() {

    suspend fun  logout(api: HomeApiInterface) = repository.logout(api)

}