package com.example.eShuttle.utils

import android.app.Activity
import android.app.AlertDialog
import com.example.eShuttle.R

class CustomDialog {
    private var activity: Activity? = null
    private var alertDialog: AlertDialog? = null
    fun LoadingDialog(myActivity: Activity?) {
        activity = myActivity
    }

    fun startLoading() {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity!!.layoutInflater
        builder.setView(inflater.inflate(R.layout.success_item, null))
        alertDialog = builder.create()
        alertDialog?.show()
    }
    fun dismissDialog() {
        alertDialog!!.dismiss()
    }
}