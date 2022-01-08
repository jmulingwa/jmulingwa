package com.example.eShuttle.ui.AuthFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.eShuttle.base.BaseFragment
import com.example.eShuttle.databinding.FragmentMakeSelectionBinding
import com.example.eShuttle.repository.AuthRepository
import com.example.eShuttle.viewModels.AuthViewModel

class MakeSelectionFragment : BaseFragment<
        AuthViewModel,
        FragmentMakeSelectionBinding,
        AuthRepository>() {

    //Override the base fragment methods
    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMakeSelectionBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = AuthRepository(retrofitInstance.api, userPreferences)

    // Write business logic here
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //
    }
}