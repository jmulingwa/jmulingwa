package com.example.eShuttle.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.eShuttle.api.HomeApiInterface
import com.example.eShuttle.api.RetrofitClient
import com.example.eShuttle.datastore.UserPreferences
import com.example.eShuttle.repository.BaseRepository
import com.example.eShuttle.ui.AuthActivity
import com.example.eShuttle.utils.startNewActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

abstract class BaseFragment<
        VM : BaseViewModel,
        VB : ViewBinding, R : BaseRepository> : Fragment() {

    protected lateinit var binding: VB
    protected val retrofitInstance = RetrofitClient()
    protected lateinit var viewModel: VM
    protected lateinit var userPreferences: UserPreferences



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //initialize sharedPrefs
        userPreferences = UserPreferences(requireContext())

        binding = getFragmentBinding(inflater, container)

        //get actual repository
        val factory = ViewModelFactory(getFragmentRepository())

        //get actual viewModel
        viewModel = ViewModelProvider(this, factory).get(getViewModel())

        //to prevent a ANR when retrieving auth key we retrieve the token when fragment launches because its stored in main mEmory
        lifecycleScope.launch { userPreferences.jwt_authToken.first() }

        return binding.root
    }

    //Returns  the correct viewModel
    abstract fun getViewModel(): Class<VM>

    // Returns the correct fragmentBinding
    abstract fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): VB

    //Returns the correct Repository
    abstract fun getFragmentRepository(): R

    //Call the repository logout from here to be able to logout user from any screen b
    // because Base fragment is extended by every fragment
    fun logout() = lifecycleScope.launch {
       // get token
         val authToken = userPreferences.jwt_authToken.first()
         val api = retrofitInstance.buildApi(HomeApiInterface::class.java, authToken)

        viewModel.logout(api)

        //clear token from userPreferences after logout
        userPreferences.clearToken()

        //finally start new Activity
        requireActivity().startNewActivity(AuthActivity::class.java)

    }

}