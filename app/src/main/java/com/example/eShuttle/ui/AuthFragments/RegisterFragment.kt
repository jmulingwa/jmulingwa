package com.example.eShuttle.ui.AuthFragments

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.eShuttle.R
import com.example.eShuttle.base.BaseFragment
import com.example.eShuttle.databinding.FragmentRegisterBinding
import com.example.eShuttle.models.User
import com.example.eShuttle.repository.AuthRepository
import com.example.eShuttle.utils.Resource
import com.example.eShuttle.utils.visible
import com.example.eShuttle.viewModels.AuthViewModel
import kotlinx.coroutines.launch


class RegisterFragment : BaseFragment<
        AuthViewModel,
        FragmentRegisterBinding,
        AuthRepository>() {

    //Override the base fragment methods
    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentRegisterBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = AuthRepository(retrofitInstance.api, userPreferences)

    // Write business logic here
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.goBack.setOnClickListener {
            findNavController().navigate(
                R.id.action_registerFragment_to_loginFragment
            )
        }

        binding.btnRegister.setOnClickListener {
            handRegistration()
        }
        binding.textBackLogin.setOnClickListener {
            findNavController().navigate(
                R.id.action_registerFragment_to_loginFragment
            )
        }

    }

    //Handle registration
    private fun handRegistration() {
        // collect user inputs
        val firstName = binding.editTextFirstName.text.toString().trim()
        val email = binding.editTextLastEmal.text.toString().trim()
        val phone = "254"+binding.editTextPhone.text.toString().trim()
        println("Jose: $phone")

        val password =binding.editTextPassword.text.toString().trim()
        val confirm = binding.editTextPasswordConfirm.text.toString().trim()
        when{
            firstName.isEmpty() ->{
                binding.textInputFirstName.error ="Please provide first name!"
                binding.progressBar.visible(false)
            }
            email.isEmpty() ->{
               binding.textInputEmail.error = "Please provide an email address!"
                binding.progressBar.visible(false)
            }
            !email.isEmailValid() ->{
                binding.textInputEmail.error = "Please input a valid email"
            }
            phone.length <9 ->{
                binding.textInputPhone.error ="Phone number must be 9 characters!"
                binding.progressBar.visible(false)
            }
            password.length <6 ->{
                binding.textInputPassword.error ="Password too short!"
                binding.progressBar.visible(false)
            }
            confirm !=password ->{
                binding.textInputConfirmPass.error ="Passwords should match!"
            }

            else ->{
                // call viewModel Method registerUser
                binding.progressBar.visible(true)
                val user = User(null,firstName,null,null,email,null,null,phone,password,confirm,null,null)
                viewModel.registerUser(user)

                binding.progressBar.visible(true)

                //Observe registration response
                viewModel.registerResponse.observe(viewLifecycleOwner, Observer { response ->
//            binding.progressBar.visible(false)
                    when (response) {

                        is Resource.Error -> {
                            binding.progressBar.visible(false)

                            val view = View.inflate(requireContext(),R.layout.error_item,null)
                            val builder = AlertDialog.Builder(requireContext(),R.style.fullScreenDialog)
                            builder.setView(view)

                            val dialog = builder.create()
                            dialog.show()
                            dialog.window?.setBackgroundDrawableResource(R.color.white)
                            Handler().postDelayed({
                                dialog.dismiss()
                            },2000)

                        }

                        is Resource.Loading -> {
                            binding.progressBar.visible(true)
                        }
                        is Resource.Success -> {
//                    val bottomSheetDialog = BottomSheetDialog(requireContext())
//                    bottomSheetDialog.setContentView(view)
//                    bottomSheetDialog.show()
//                            Toast.makeText(requireContext(), "Nimeitwa", Toast.LENGTH_SHORT).show()
//                            lifecycleScope.launch {
//                                //store phone number to user preferences
//                                viewModel.savePhoneNumber(response.value.phone)
//                                Toast.makeText(requireContext(), "Nimeitwa", Toast.LENGTH_SHORT).show()
//
//                            }

                            val view = View.inflate(requireContext(),
                                R.layout.success_item,null)
                            val builder = AlertDialog.Builder(requireContext())
                            builder.setView(view)

                            val dialog = builder.create()
                            dialog.show()
                            dialog.window?.setBackgroundDrawableResource(com.example.eShuttle.R.color.transparent)
                            Handler().postDelayed({
                                dialog.dismiss()

                                //<<<<<<<<Parsing data to safeArgs in navigation Components>>>>>>>>
                                var action = RegisterFragmentDirections.actionRegisterFragmentToSmsValidateFragment(phone)
                                //<<<<<<<< END >>>>>>>>

                                findNavController().navigate(
                                action
                                )
                            },2000)

                        }
                        else -> {
                            TODO()
                        }
                    }
                })

            }
        }


    }
    fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }


}