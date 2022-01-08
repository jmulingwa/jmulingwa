package com.example.eShuttle.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.eShuttle.databinding.FragmentViewPagerBinding
import com.example.eShuttle.repository.AuthRepository
import com.example.eShuttle.base.BaseFragment
import com.example.eShuttle.ui.onboarding.screens.ScreenOneFragment
import com.example.eShuttle.ui.onboarding.screens.ScreenTwoFragment
import com.example.eShuttle.viewModels.AuthViewModel

class ViewPagerFragment : BaseFragment<
        AuthViewModel,
        FragmentViewPagerBinding,
        AuthRepository>() {

    //Override the base fragment methods
    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentViewPagerBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = AuthRepository(retrofitInstance.api, userPreferences)

    // Write business logic here
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Inflate the layout for this fragment
        val fragmentList = arrayListOf<Fragment>(
            ScreenOneFragment(),
            ScreenTwoFragment()
        )

        // init viewPager Adapter
        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
       binding.splashViewPager.adapter = adapter

    }
}

