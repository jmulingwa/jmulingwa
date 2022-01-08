package com.example.eShuttle.ui.AuthFragments

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.eShuttle.R
import com.example.eShuttle.base.BaseFragment
import com.example.eShuttle.databinding.FragmentConfirmPasswordResetBinding
import com.example.eShuttle.models.PassResetModel
import com.example.eShuttle.repository.AuthRepository
import com.example.eShuttle.utils.Resource
import com.example.eShuttle.viewModels.AuthViewModel

class ConfirmPasswordResetFragment : BaseFragment<
        AuthViewModel,
        FragmentConfirmPasswordResetBinding,
        AuthRepository>() {

    //Override the base fragment methods
    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentConfirmPasswordResetBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = AuthRepository(retrofitInstance.api, userPreferences)

    // Write business logic here
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.goBack.setOnClickListener {
            findNavController().navigate(
                R.id.action_confirmPasswordResetFragment_to_resetPasswordFragment
            )
        }
        binding.btnSubmit.setOnClickListener {
            //collect user inputs
            val key = binding.passwordKey.text.toString()
            val newPassword = binding.newPassword.text.toString()

            if (key.isEmpty()){
                binding.keyInField.error ="Please provide a valid key!"
            }
            if (newPassword.length <6){
                binding.newPasswordIn.error = "Password should not be less than 6 characters!"
            }
            else{

                val passResetModel = PassResetModel(null,key,newPassword)
                viewModel.finishPasswordReset(passResetModel)

                viewModel.finishPasswordReset.observe(viewLifecycleOwner, Observer { response ->

                    when(response){
                        is Resource.Loading ->{}
                        is Resource.Error ->{
                            Toast.makeText(requireContext(), "There  was an error,Please try again", Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Success ->{
                            val view = View.inflate(requireContext(), R.layout.success_item,null)
                            val builder = AlertDialog.Builder(requireContext())
                            builder.setView(view)

                            val dialog = builder.create()
                            dialog.show()
                            dialog.window?.setBackgroundDrawableResource(R.color.transparent)
                            Handler().postDelayed({
                                dialog.dismiss()
                                findNavController().navigate(
                                    R.id.action_confirmPasswordResetFragment_to_loginFragment
                                )
                            },2000)
                        }
                    }
                })
            }
        }


    }
}