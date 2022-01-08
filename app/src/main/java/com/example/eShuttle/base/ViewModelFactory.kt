package com.example.eShuttle.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.eShuttle.repository.AuthRepository
import com.example.eShuttle.repository.BaseRepository
import com.example.eShuttle.repository.UserRepository
import com.example.eShuttle.viewModels.AuthViewModel
import com.example.eShuttle.viewModels.UserViewModel

class ViewModelFactory(
    private val repository: BaseRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(repository as AuthRepository) as T
            modelClass.isAssignableFrom(UserViewModel::class.java) -> UserViewModel(repository as UserRepository) as T
            else -> throw IllegalArgumentException("viewModelClass Not found!")

        }
    }
}