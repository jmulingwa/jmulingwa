package com.example.eShuttle.utils

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import com.example.eShuttle.base.BaseFragment
import com.example.eShuttle.ui.AuthFragments.LoginFragment
import com.google.android.material.snackbar.Snackbar

fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}


//toggle visibility of progressbar
fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}


// enable and disable a button
fun View.enabled(enabled: Boolean) {
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.5f
}

//custom toast
//TODO

// custom snackbar
fun View.snackbar(message : String, action:(()->Unit)?=null){
    val snackbar = Snackbar.make(this,message, Snackbar.LENGTH_LONG)

    action?.let {
        snackbar.setAction("Retry"){
            it()
        }
    }
    snackbar.show()
}
//Handle api error
fun Fragment.handleApiError(
    failure : Resource.Error,
    retry: (()-> Unit)? = null
){
        when{
            failure.isNetworkError == true -> requireView()
                .snackbar("Please check your internet connection", retry)
            failure.errorCode ==400 ->{
                if (this is LoginFragment){
                    requireView().snackbar("You've entered incorrect email or password")
                }else{

//                    logout
                    (this as BaseFragment<*, *, *>).logout()

                }
            }
            else ->{
                val error = "Account not Found!"
                requireView().snackbar(error)
            }
        }
}