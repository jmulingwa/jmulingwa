package com.example.eShuttle.ui.AuthFragments

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.eShuttle.R
import com.example.eShuttle.api.AuthApinterface
import com.example.eShuttle.databinding.FragmentLoginBinding
import com.example.eShuttle.models.User
import com.example.eShuttle.repository.AuthRepository
import com.example.eShuttle.ui.HomeFragments.HomeActivity
import com.example.eShuttle.base.BaseFragment
import com.example.eShuttle.utils.*
import com.example.eShuttle.viewModels.AuthViewModel
import kotlinx.coroutines.launch


class LoginFragment : BaseFragment<
        AuthViewModel,
        FragmentLoginBinding,
        AuthRepository>() {

    //Override the base fragment methods
    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() =
        AuthRepository(retrofitInstance.buildApi(AuthApinterface::class.java), userPreferences)

    // Write business logic here
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.progressBar.visible(false)

        //disable button login on launch
        binding.btnLogin.enabled(false)

        //handle validations
        binding.userPasswordEditText.addTextChangedListener {
            val phone = binding.userNameEditText.text.toString().trim()
            binding.btnLogin.enabled(phone.isNotEmpty() && it.toString().isNotEmpty())
        }

        //handle login
        binding.btnLogin.setOnClickListener {
            //calls the method that handles login
            handleLogin()
        }
        binding.registerTextView.setOnClickListener {

            //Navigate
            findNavController().navigate(
                R.id.action_loginFragment_to_registerFragment
            )
        }

        binding.forgotPassword.setOnClickListener {
            findNavController().navigate(
                R.id.action_loginFragment_to_resetPasswordFragment
            )
        }

    }

    // method to handle login
    private fun handleLogin() {
        //collect user inputs
        val phon = binding.userNameEditText.text.toString().trim()
        val phone = "254"+phon
        println(phone)
        val password = binding.userPasswordEditText.text.toString().trim()

        when {
            phone.length <9 -> {
                binding.textInPhone.error = "phone number too short"
                binding.progressBar.visible(false)

            }
            phone.length >12 -> {
                binding.textInPhone.error ="phone number too long"
                binding.progressBar.visible(false)

            }
            password.length <6 -> {
                binding.textInPassword.error = "Password too short"
                binding.progressBar.visible(false)

            }
            else ->{
                //execute login
                binding.progressBar.visible(true)
                val user = User(null,null,null,phone,null,null,null,null,password,null,null,null)
                //@todo add input validations
                viewModel.loginUser(user)

                //Observe login response livedata
                viewModel.loginResponse.observe(viewLifecycleOwner, Observer { response ->
                    binding.progressBar.visible(response is Resource.Loading)
                    when (response) {
                        is Resource.Success -> {

                            lifecycleScope.launch {
                                //store auth token to user preferences
                                viewModel.saveAuthToken(response.value.id_token)
                            }

                            //Display sucess dialog and navigate
                            val view = View.inflate(requireContext(),R.layout.success_item,null)
                            val builder = AlertDialog.Builder(requireContext())
                            builder.setView(view)

                            val dialog = builder.create()
//                            dialog.show()
                            dialog.window?.setBackgroundDrawableResource(R.color.white)
                            Handler().postDelayed({
//                                dialog?.dismiss()
                            },4000)

                            //Start home activity here
                            requireActivity().startNewActivity(HomeActivity::class.java)

                        }
                        is Resource.Loading ->{
                            binding.progressBar.visible(true)
                        }
                        is Resource.Error -> handleApiError(response){handleLogin()} //something is happening here
                    }


                })
            }
        }


    }

}