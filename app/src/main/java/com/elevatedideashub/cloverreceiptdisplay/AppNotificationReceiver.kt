package com.elevatedideashub.cloverreceiptdisplay

import android.content.Context
import android.util.Log
import com.clover.sdk.v1.app.AppNotification

class AppNotificationReceiver : com.clover.sdk.v1.app.AppNotificationReceiver() {
    override fun onReceive(context: Context?, notification: AppNotification?) {
        if (notification != null) {
            Log.d("Event", notification.appEvent)
            Log.d("Payload", notification.payload)
        }
    }
}