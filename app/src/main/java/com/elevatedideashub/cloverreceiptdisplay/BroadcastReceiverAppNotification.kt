package com.elevatedideashub.cloverreceiptdisplay

import android.content.Context
import android.content.Intent
import android.util.Log
import com.clover.sdk.v1.app.AppNotification
import com.clover.sdk.v1.app.AppNotificationReceiver

class BroadcastReceiverAppNotification : AppNotificationReceiver() {

    override fun onReceive(context: Context?, notification: AppNotification?) {
        Log.d("message received", notification?.payload)
        val messageDisplayIntent= Intent(context, LoyaltyRedeemedMessageDisplay()::class.java)
        messageDisplayIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context?.startActivity(messageDisplayIntent)
    }
}