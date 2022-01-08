package com.example.eShuttle.ui.AuthFragments

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.eShuttle.R
import com.example.eShuttle.base.BaseFragment
import com.example.eShuttle.databinding.FragmentResetPasswordBinding
import com.example.eShuttle.models.PassResetModel
import com.example.eShuttle.repository.AuthRepository
import com.example.eShuttle.utils.Resource
import com.example.eShuttle.utils.visible
import com.example.eShuttle.viewModels.AuthViewModel

class ResetPasswordFragment : BaseFragment<
        AuthViewModel,
        FragmentResetPasswordBinding,
        AuthRepository>() {

    //Override the base fragment methods
    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentResetPasswordBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = AuthRepository(retrofitInstance.api, userPreferences)


    // Write business logic here
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.goBack.setOnClickListener {
            findNavController().navigate(
                R.id.action_resetPasswordFragment_to_loginFragment
            )
        }

       binding.btnNext.setOnClickListener {
          resetPassword()

       }

    }

    private fun resetPassword() {
        val phoneNumber = "254"+binding.editTextUsername.text.toString()
        if (phoneNumber.isEmpty()){
            binding.textInputUsername.error ="Invalid phone number!"
        }else if (phoneNumber.length <12){
            binding.textInputUsername.error ="phone number should be maximum 9 digits!"
        }else if (phoneNumber.length >12){
            binding.textInputUsername.error ="phone number length error,Please correct your phone number!"

        }
        else{
            binding.textInputUsername.error =null
            val login = PassResetModel(phoneNumber,null,null)
            viewModel.requestPassReset(login)

            viewModel.requestPassReset.observe(viewLifecycleOwner, Observer {
                binding
                when(it){
                    is Resource.Success ->{

                        //Display success dialog and navigate
                        val view = View.inflate(requireContext(),R.layout.success_item,null)
                        val builder = AlertDialog.Builder(requireContext(),R.style.fullScreenDialog)
                        builder.setView(view)

                        val dialog = builder.create()
                        dialog.show()
                        dialog.window?.setBackgroundDrawableResource(R.color.white)

//                        val width = ViewGroup.LayoutParams.MATCH_PARENT
//                        val height = ViewGroup.LayoutParams.MATCH_PARENT
//                        dialog.window?.setLayout(width, height)
                        Handler().postDelayed({
                            dialog.dismiss()
                        },3000)
                        findNavController().navigate(
                            R.id.action_resetPasswordFragment_to_confirmPasswordResetFragment
                        )
                    }
                    is Resource.Loading ->{
                        binding.progressBar.visible(true)
                    }
                    is Resource.Error ->{
                        //Display sucess dialog and navigate
                        val view = View.inflate(requireContext(),R.layout.error_item,null)
                        val builder = AlertDialog.Builder(requireContext())
                        builder.setView(view)

                        val dialog = builder.create()
                        dialog.show()
                        dialog.window?.setBackgroundDrawableResource(R.color.transparent)
                        Handler().postDelayed({
                            dialog.dismiss()
                        },2000)
                        binding.progressBar.visible(false)

                    }
                }
            })
        }
    }
}