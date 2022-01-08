package com.example.eShuttle.ui.AuthFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.eShuttle.R
import com.example.eShuttle.databinding.FragmentSmsValidateBinding
import com.example.eShuttle.repository.AuthRepository
import com.example.eShuttle.base.BaseFragment
import com.example.eShuttle.utils.Resource
import com.example.eShuttle.utils.visible
import com.example.eShuttle.viewModels.AuthViewModel
import android.text.Editable

import android.text.TextWatcher
import com.example.eShuttle.models.OtpModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


class SmsValidateFragment : BaseFragment<
        AuthViewModel,
        FragmentSmsValidateBinding,
        AuthRepository>() {

    //Override the base fragment methods
    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSmsValidateBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = AuthRepository(retrofitInstance.api, userPreferences)

    // Write business logic here
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.goBack.setOnClickListener {
            findNavController().navigate(
                R.id.action_smsValidateFragment_to_registerFragment
            )
        }
        //disable button login on launch
//        binding.btnValidate.enabled(false)
//
//        binding.inputCode1.addTextChangedListener {
//            val inputc4 = binding.inputCode4.text.toString().trim()
//            binding.btnValidate.enabled(inputc4.isNotEmpty() && it.toString().isNotEmpty())
//        }

        binding.inputCode1.requestFocus()
        handleValidation()
        binding.btnValidate.setOnClickListener {
            binding.progressBar.visible(true)
            verifyOtpCode()
        }
    }
    private fun verifyOtpCode(){
        val inputCode1 = binding.inputCode1.text.toString().trim()
        val inputCode2 = binding.inputCode2.text.toString().trim()
        val inputCode3 = binding.inputCode3.text.toString().trim()
        val inputCode4 = binding.inputCode4.text.toString().trim()
        val inputCode5 = binding.inputCode5.text.toString().trim()
        val inputCode6 = binding.inputCode6.text.toString().trim()

        val otpCode = inputCode1 + inputCode2 + inputCode3 + inputCode4 + inputCode5 + inputCode6

        // <<<<<<<<<<<<retrieve your saved phoneNumber from arg>>>>>>>>>>
        val smsValidateFragmentArgs : SmsValidateFragmentArgs= SmsValidateFragmentArgs.fromBundle(requireArguments())
        val phoneNumber = smsValidateFragmentArgs.phoneNumber
        println("Your phoneNumber : $phoneNumber")
        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Receiving data passed from navArgs>>>>>>>>>


        val otp = OtpModel(otpCode,phoneNumber)
        // call viewModel method
        viewModel.otpValidate(otp)

        //Observe the incoming response
        viewModel.validateResponse.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Error -> {
                    binding.progressBar.visible(false)
                    Toast.makeText(activity, "Otp validation failed", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    binding.progressBar.visible(true)
                    
                }
                is Resource.Success -> {
                    binding.progressBar.visible(false)


                    findNavController().navigate(
                        R.id.action_smsValidateFragment_to_loginFragment
                    )
                }
            }
        })

    }
    private fun handleValidation() {

        binding.inputCode1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                Toast.makeText(requireContext(), "I was reached", Toast.LENGTH_SHORT).show()
                if (charSequence.isNotEmpty()) {
                    binding.inputCode2.requestFocus()
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })



        binding.inputCode2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (charSequence.isNotEmpty()) {
                    binding.inputCode3.requestFocus()
                } else {
                    binding.inputCode1.requestFocus()
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        binding.inputCode3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (charSequence.isNotEmpty()) {
                    binding.inputCode4.requestFocus()
                } else {
                    binding.inputCode2.requestFocus()
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        binding.inputCode4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (charSequence.isNotEmpty()) {
                    binding.inputCode5.requestFocus()
                }
                else {
                    binding.inputCode3.requestFocus()
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        binding.inputCode5.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (charSequence.isNotEmpty()) {
                    binding.inputCode6.requestFocus()
                }
                else {
                    binding.inputCode4.requestFocus()
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
        binding.inputCode6.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (charSequence.isEmpty()) {
                    binding.inputCode5.requestFocus()
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }

}