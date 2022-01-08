package com.example.eShuttle.ui.AuthFragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.phone.SmsCodeRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

class SmsBroadcastReceiver : BroadcastReceiver() {

    var smsBroadcastReceiverListener :SmsBroadcastReceiverListener ?= null

    override fun onReceive(context: Context?, intent: Intent?) {
       if (SmsCodeRetriever.SMS_CODE_RETRIEVED_ACTION == intent?.action){
           val extras =intent.extras
           val smsRetrieveStatus = extras?.get(SmsCodeRetriever.EXTRA_STATUS) as Status

           when (smsRetrieveStatus.statusCode){

               CommonStatusCodes.SUCCESS ->{
                   val messageIntent = extras.getParcelable<Intent>(SmsCodeRetriever.EXTRA_SMS_CODE)
                   smsBroadcastReceiverListener?.onFailure()

               }
           }
       }
    }

    interface  SmsBroadcastReceiverListener{
        fun onSucess(intent: Intent?)
        fun onFailure()
    }
}