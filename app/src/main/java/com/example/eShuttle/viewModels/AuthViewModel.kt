package com.example.eShuttle.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.eShuttle.base.BaseViewModel
import com.example.eShuttle.models.OtpModel
import com.example.eShuttle.models.PassResetModel
import com.example.eShuttle.models.User
import com.example.eShuttle.repository.AuthRepository
import com.example.eShuttle.responses.*
import com.example.eShuttle.utils.Resource
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository
) : BaseViewModel(repository) {

    //mutable livedata for response
    private val _loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    private val _registerResponse: MutableLiveData<Resource<RegisterResponse>> = MutableLiveData()
    private val _validateResponse: MutableLiveData<Resource<ValidateResponse>> = MutableLiveData()
    private val _requestPassReset: MutableLiveData<Resource<PassResetReponse>> = MutableLiveData()
    private val _finishPasswordReset: MutableLiveData<Resource<PassResetReponse>> = MutableLiveData()

    // immutable and is the one we will access  outside class
    val loginResponse: LiveData<Resource<LoginResponse>>
        get() = _loginResponse

    val registerResponse: LiveData<Resource<RegisterResponse>>
        get() = _registerResponse

    val validateResponse: LiveData<Resource<ValidateResponse>>
        get() = _validateResponse

    val  requestPassReset:LiveData<Resource<PassResetReponse>>
        get() = _requestPassReset
    val finishPasswordReset: LiveData<Resource<PassResetReponse>>
        get() = _finishPasswordReset

    // call login from repository
    fun loginUser(
        user: User
    ) = viewModelScope.launch {
        _loginResponse.value = Resource.Loading
        _loginResponse.value = repository.loginUser(user)
    }

    //call saveToken from repository
    suspend fun saveAuthToken(authToken: String) {
        repository.saveToken(authToken)
    }

    //call savePhoneNumber
    suspend fun savePhoneNumber(phoneNumber: String){
        repository.savePhoneNumber(phoneNumber)
    }
    // call the register function from repository
    fun registerUser(
        user: User
    ) = viewModelScope.launch {
        _registerResponse.value = repository.registerUser(user)
    }

    //call validate otp from repository

    fun otpValidate(
        otp: OtpModel
    ) = viewModelScope.launch {
        _validateResponse.value = repository.otpValidate(otp)
    }

    // call request pass reset from repository
    fun requestPassReset(phoneNumber: PassResetModel) = viewModelScope.launch {
        _requestPassReset.value =repository.requestPasswordResetWithPhone(phoneNumber)
    }

    // call finish password reset
    fun finishPasswordReset(passResetModel: PassResetModel) = viewModelScope.launch {
        _finishPasswordReset.value = repository.finishPasswordReset(passResetModel)
    }
}