package com.example.eShuttle.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.eShuttle.base.BaseViewModel
import com.example.eShuttle.models.BookingModel
import com.example.eShuttle.models.User
import com.example.eShuttle.repository.UserRepository
import com.example.eShuttle.responses.BookingResponse
import com.example.eShuttle.responses.ProfileResponse
import com.example.eShuttle.responses.getUserResponse
import com.example.eShuttle.utils.Resource
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: UserRepository
) : BaseViewModel(repository) {

    //Declare mutable livedata
    private val _user: MutableLiveData<Resource<getUserResponse>> = MutableLiveData()
    private val _bookRide : MutableLiveData<Resource<BookingResponse>> = MutableLiveData()
    private val _updateUserProfile: MutableLiveData<Resource<ProfileResponse>> = MutableLiveData()

    //provide their getters so that they can be observed outside and cannot be changed
    val user: LiveData<Resource<getUserResponse>>
        get() = _user

    val  bookRide: LiveData<Resource<BookingResponse>>
        get() = _bookRide

    val updateUserProfile: LiveData<Resource<ProfileResponse>>
        get() = _updateUserProfile

    //#####################################################################//
    // get user
    fun getUser() = viewModelScope.launch {
        _user.value = repository.getUser()
    }

    //book a ride
    fun bookRide(
        bookingModel: BookingModel
    ) = viewModelScope.launch {
        _bookRide.value = repository.bookRide(bookingModel)
    }


    //update User Profile
    fun updateUserProfile(
        user: User
    ) = viewModelScope.launch {
        _updateUserProfile.value = repository.updateUserProfile(user)
    }

    //######################################################################//


}