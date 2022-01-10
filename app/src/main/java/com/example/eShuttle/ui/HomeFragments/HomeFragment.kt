package com.example.eShuttle.ui.HomeFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.eShuttle.R
import com.example.eShuttle.api.HomeApiInterface
import com.example.eShuttle.databinding.FragmentHomeBinding
import com.example.eShuttle.repository.UserRepository
import com.example.eShuttle.base.BaseFragment
import com.example.eShuttle.responses.getUserResponse
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

        viewModel.user.observe(viewLifecycleOwner, Observer {response ->
            when(response){
                is Resource.Success ->{
                    updateUI(response.value)
                }
            }

        })

        //Logout here
//        binding.imageMenu.setOnClickListener {
//            logout()
//        }

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

        //handle menu clicks
        binding.topBar.setOnMenuItemClickListener {menuItem ->
            when(menuItem.itemId){
                R.id.logout ->{
                    logout()
                    true
                }
                R.id.profileFragment ->{
                    val username = binding.textUsername.text.toString()
                    val phoneNumber = binding.phoneNumber.text.toString()
                    val email = binding.emailId.text.toString()
                    val bundle = Bundle()
                    bundle.putString("username",username)
                    bundle.putString("email",email)
                    bundle.putString("phoneNumber",phoneNumber)
                    view?.let { Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_profileFragment,bundle) }
                    true


                }
                else -> {
                    true
                }
            }
        }

    }

    private fun updateUI(response: getUserResponse) {
        with(binding){
            textUsername.text = response.firstName
            emailId.text = response.email
            phoneNumber.text = response.login
        }
    }

    override fun onPause() {
        viewModel.user.removeObservers(viewLifecycleOwner)
        super.onPause()
    }


}