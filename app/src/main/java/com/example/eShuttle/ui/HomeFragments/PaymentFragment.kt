package com.example.eShuttle.ui.HomeFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.eShuttle.R
import com.example.eShuttle.api.HomeApiInterface
import com.example.eShuttle.base.BaseFragment
import com.example.eShuttle.databinding.FragmentPaymentBinding
import com.example.eShuttle.repository.UserRepository
import com.example.eShuttle.viewModels.UserViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class PaymentFragment : BaseFragment<
        UserViewModel,
        FragmentPaymentBinding,
        UserRepository>(){

    override fun getViewModel() =UserViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentPaymentBinding.inflate(inflater,container,false)


    override fun getFragmentRepository(): UserRepository {
        //since this fragment requires a authToken to be available
        // we retrieve the saved token from datastore
        val token = runBlocking { userPreferences.jwt_authToken.first() }
        println("token JJJJJJJ : $token")

        // we now create api instance to  pass our bearer token to the request
        val api = retrofitInstance.buildApi(HomeApiInterface::class.java)
        return UserRepository(api)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Write business logic here
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(
                R.id.action_paymentFragment_to_pendingPayBookingsFragment
            )
        }

    }
    }

