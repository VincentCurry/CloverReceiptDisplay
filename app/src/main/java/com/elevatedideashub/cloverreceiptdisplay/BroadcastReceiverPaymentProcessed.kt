package com.elevatedideashub.cloverreceiptdisplay

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.clover.sdk.v1.Intents

class BroadcastReceiverPaymentProcessed : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action

        if(action==Intents.ACTION_PAYMENT_PROCESSED) {
            val cloverPaymentId = intent.getStringExtra(Intents.EXTRA_CLOVER_PAYMENT_ID)

            val bundle = Bundle()

            val intent = Intent(context, QRCodeDisplay()::class.java)
            intent.putExtra(Intents.EXTRA_CLOVER_PAYMENT_ID, cloverPaymentId)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            ContextCompat.startActivity(context, intent, bundle)
        }
    }

}