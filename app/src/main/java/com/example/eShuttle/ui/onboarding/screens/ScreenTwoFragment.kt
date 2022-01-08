package com.example.eShuttle.ui.onboarding.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.eShuttle.databinding.FragmentScreenOneBinding
import com.example.eShuttle.repository.AuthRepository
import com.example.eShuttle.ui.AuthActivity
import com.example.eShuttle.ui.SplashScreenActivity
import com.example.eShuttle.base.BaseFragment
import com.example.eShuttle.viewModels.AuthViewModel

class ScreenTwoFragment : BaseFragment<
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

            //open an activity
            (activity as? SplashScreenActivity)?.let{
                val intent = Intent (it, AuthActivity::class.java)
                it.startActivity(intent)

                //Setting onBoarding to true
                onBoardingFinished()
            }
        }
    }
    private fun onBoardingFinished(){
        //use shared pref
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()

    }
}