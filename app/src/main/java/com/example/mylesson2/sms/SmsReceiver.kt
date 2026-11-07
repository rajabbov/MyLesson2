package com.example.mylesson2.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class SmsReceiver :BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("onReceive",intent.toString())
    }
}