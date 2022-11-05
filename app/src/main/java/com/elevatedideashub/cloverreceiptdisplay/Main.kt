package com.elevatedideashub.cloverreceiptdisplay

import android.app.Activity
import android.app.Service.START_STICKY
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.clover.sdk.v1.Intents
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter

object IntentConstants{
    const val messageIntent = "messageTextIntent"
    const val qrIntent = "qrIntent"
}

class Main : Activity() {

    private var qrImage: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
Log.d("Main", "On Create")
        super.onCreate(savedInstanceState)
        var getMerchantDetails: MerchantDetails = MerchantDetails()

        getMerchantDetails.getMerchantDetails(this)
        val listeningService: ListeningService = ListeningService()
        var serviceIntent: Intent = Intent()
        //serviceIntent.addCategory(START_STICKY)
        //listeningService.startService(serviceIntent)
        //finish()
        moveTaskToBack(true)
    }

}