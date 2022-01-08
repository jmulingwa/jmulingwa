package com.example.eShuttle.ui.HomeFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.eShuttle.R
import com.example.eShuttle.api.HomeApiInterface
import com.example.eShuttle.databinding.FragmentHomeBinding
import com.example.eShuttle.repository.UserRepository
import com.example.eShuttle.base.BaseFragment
import com.example.eShuttle.utils.Resource
import com.example.eShuttle.viewModels.UserViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class   HomeFragment : BaseFragment<
        UserViewModel,
        FragmentHomeBinding,
        UserRepository>() {

    //Override the base fragment methods
    override fun getViewModel() = UserViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): UserRepository {
        // retrieve your saved token from datastore
        val token = runBlocking { userPreferences.jwt_authToken.first() }

        println("token JJJJJJJ : $token")
        //create api instance
        val api  = retrofitInstance.buildApi(HomeApiInterface::class.java, token)
        return UserRepository(api)
    }


    // Write business logic here
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getUser()

        viewModel.user.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success ->{
                    updateUI(it.value.firstName)
                }
            }

        })

        //Logout here
        binding.imageMenu.setOnClickListener {
            logout()
        }

        //navigate to booking
        binding.layoutBooking.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_bookingFragment
            )
        }

        //Navigate to payments  fragment
        binding.layoutPayment.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_pendingPayBookingsFragment
            )
        }

    }

    private fun updateUI(firstName: String) {
        with(binding){
            textUsername.text = firstName
        }
    }

}