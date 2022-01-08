package com.example.eShuttle.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.example.eShuttle.R
import com.example.eShuttle.datastore.UserPreferences
import com.example.eShuttle.ui.HomeFragments.HomeActivity
import com.example.eShuttle.utils.startNewActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val userPreferences = UserPreferences(this)

        //get token
        userPreferences.jwt_authToken.asLiveData().observe(this, Observer {
            //conditionally render activities
            val activity = if (it == null) AuthActivity::class.java else HomeActivity::class.java
            startNewActivity(activity)

        })


    }
}