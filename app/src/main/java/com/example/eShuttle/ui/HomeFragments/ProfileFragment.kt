package com.example.eShuttle.ui.HomeFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.eShuttle.R
import com.example.eShuttle.api.HomeApiInterface
import com.example.eShuttle.base.BaseFragment
import com.example.eShuttle.databinding.ContentProfileBinding
import com.example.eShuttle.databinding.FragmentProfileBinding
import com.example.eShuttle.models.User
import com.example.eShuttle.repository.UserRepository
import com.example.eShuttle.utils.DatePickerFragment
import com.example.eShuttle.utils.Resource
import com.example.eShuttle.viewModels.UserViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class ProfileFragment : BaseFragment<
        UserViewModel,
        FragmentProfileBinding,
        UserRepository>(){

    override fun getViewModel() = UserViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentProfileBinding.inflate(inflater,container,false)


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

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(
                R.id.action_profileFragment_to_homeFragment
            )
        }
        //getting  binding for included layouts
        val contentProfileBinding :ContentProfileBinding = binding.contentLayout
        //Write business logic here

        val fullname = arguments!!.getString("username")
        val email = arguments!!.getString("email")
        val phoneNumber =arguments!!.getString("phoneNumber")

//        Toast.makeText(context,phoneNumber,Toast.LENGTH_SHORT).show()

        binding.fullName.text = fullname
        binding.emailId.text = email
        contentProfileBinding.editTextPhoneNumber.setText(phoneNumber)
        contentProfileBinding.editTextEmailAddress.setText(email)
        contentProfileBinding.editTextfirstName.setText(fullname)


        //<<<<<<<<<<<<<<<<<<<<GETTING DATE FROM THE DatePICKER DIALOG>>>>>>>>>>>>//
        contentProfileBinding.apply {
            editTextDateOfBirth.setOnClickListener {
                val datePickerFragment = DatePickerFragment()
                val supportFragmentManager = requireActivity().supportFragmentManager


                //setFragment result listener
                supportFragmentManager.setFragmentResultListener(
                    "REQUEST_KEY",
                    viewLifecycleOwner
                ){
                        resultKey,bundle -> if (resultKey =="REQUEST_KEY"){
                    val date = bundle.getString("SELECTED_DATE")

                    //update UI
                    editTextDateOfBirth.setText(date)
                }
                }

                // show
                datePickerFragment.show(supportFragmentManager, "DatePickerFragment")
            }
        }
        //<<<<<<<<<<<<<<<<<<<END of collecting date>>>>>>>>>>>>>>>>>>>>>>>>//

        contentProfileBinding.btnUpdate.setOnClickListener {

            //collect the user inputs to update the profile
            val firstName = contentProfileBinding.editTextfirstName.text.toString()
            val phone = contentProfileBinding.editTextPhoneNumber.text.toString()
            val emailAddress = contentProfileBinding.editTextEmailAddress.text.toString()
            val nationalIdNumber =contentProfileBinding.editTextIdNumber.text.toString()
            val dateOfBirth = contentProfileBinding.editTextDateOfBirth.text.toString()
            val gender = contentProfileBinding.editTextGender.text.toString()
            println("Gender is $gender")
            println("id is $nationalIdNumber")
            println("firstName is $firstName")

            //handle validations
            //create a user object
            val user = User(null,firstName,null,null,emailAddress,
                nationalIdNumber,gender,phone,null,null,null,dateOfBirth)

            //Send the data
            viewModel.updateUserProfile(user)

            //observe the changes
            viewModel.updateUserProfile.observe(viewLifecycleOwner, Observer { response->
                when(response){

                    is Resource.Success ->{
                        Toast.makeText(requireContext(), "${response.value.message}", Toast.LENGTH_SHORT).show()
                    }

                    is Resource.Error ->{
                        Toast.makeText(requireContext(), "An error occurred", Toast.LENGTH_SHORT).show()


                    }
                    is Resource.Loading ->{
                        Toast.makeText(requireContext(), "Am loading please wait...", Toast.LENGTH_SHORT).show()
                    }

                }
            })
        }

    }
}
