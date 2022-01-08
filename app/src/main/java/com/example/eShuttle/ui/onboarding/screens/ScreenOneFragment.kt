package com.example.eShuttle.ui.onboarding.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.eShuttle.R
import com.example.eShuttle.databinding.FragmentScreenOneBinding
import com.example.eShuttle.repository.AuthRepository
import com.example.eShuttle.base.BaseFragment
import com.example.eShuttle.viewModels.AuthViewModel

class ScreenOneFragment : BaseFragment<
        AuthViewModel,
        FragmentScreenOneBinding,
        AuthRepository>() {

    //Override the base fragment methods
    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentScreenOneBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = AuthRepository(retrofitInstance.api, userPreferences)

    // Write business logic here
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.nextBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_screenOneFragment_to_screenTwoFragment
            )
        }
    }
}