package com.example.eShuttle.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.eShuttle.R
import com.example.eShuttle.databinding.FragmentSplashBinding
import com.example.eShuttle.datastore.UserPreferences
import com.example.eShuttle.ui.HomeFragments.HomeActivity

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        val view = binding.root
        //Navigate to another fragment after some delay
        Handler().postDelayed({

            if (onBoardingFinished()){
                val userPreferences = UserPreferences(requireContext())

                //get token
                userPreferences.jwt_authToken.asLiveData().observe(viewLifecycleOwner, Observer {
                    //conditionally render activities
                    if (it == null) startActivity(Intent(requireContext(),AuthActivity::class.java)) else startActivity(Intent(requireContext(), HomeActivity::class.java))

                })
            }else{
                findNavController().navigate(
                    R.id.action_splashFragment_to_screenOneFragment
                )
            }

        }, 8000)
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_splash, container, false)

        binding.webView.loadUrl("https://api.eshuttle.co.ke/splash.html")
        return view

    }

    private fun onBoardingFinished() : Boolean{
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)


    }

//    //Destroy the fragment
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }

}