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


class Main : Activity() {

    private var qrImage: ImageView? = null

    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("Broadcast", "Received")
            val cloverPaymentId = intent?.getStringExtra(Intents.EXTRA_CLOVER_PAYMENT_ID)
            val qrCodeContent = "${context?.getString(R.string.host)}receipt/receiptNumber/${cloverPaymentId}/${
                context?.getString(R.string.clover_business_id)
            }"
            Log.d("QR Code", qrCodeContent)
            val userMessage = context?.getString(R.string.receipt_scanning_instructions)
            if (qrCodeContent != null && userMessage!=null) {
                displayQRCodeAndMessage(qrCodeContent, userMessage)
            }
        //Toast.makeText(applicationContext, "received", Toast.LENGTH_SHORT)
        }
    }

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

    override fun onStart() {
        super.onStart()
       /* val filter = IntentFilter()
        filter.addAction(Intents.ACTION_PAYMENT_PROCESSED)*/

       // registerReceiver(receiver, filter)
        Log.d("Main", "On Start")
    }

    override fun onStop() {
        super.onStop()
        //unregisterReceiver(receiver)
        Log.d("Main", "On Stop")
    }

        /*

        displayQRCodeAndMessage(this.getString(R.string.loyalty_card_redemption_instructions), "QRCODE")
        val closeButton = findViewById<Button>(R.id.closeButton)
        closeButton.setOnClickListener {
            moveTaskToBack(true)
        }
    }

    override fun onNewIntent(intent: Intent) {
        qrImage!!.visibility = View.VISIBLE
        val size = 512
        val qrCodeContent = getString(R.string.app_url)
        val bits = QRCodeWriter().encode(qrCodeContent, BarcodeFormat.QR_CODE, size, size)
        val qrBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
            for (x in 0 until size) {
                for (y in 0 until size) {
                    it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        }

        qrImage!!.setImageBitmap(qrBitmap)
    }*/

    private fun displayQRCodeAndMessage(qrCodeContents: String, message: String) {
        setContentView(R.layout.qr_code_display)


        val qrImage = findViewById<ImageView>(R.id.qrCodeView)
        val closeButton = findViewById<Button>(R.id.closeButton)


        val instructionsText = findViewById<TextView>(R.id.textView)
        instructionsText.text = message

        closeButton.setOnClickListener {
            moveTaskToBack(true)
        }

        val size = 512
        val bits = QRCodeWriter().encode(qrCodeContents, BarcodeFormat.QR_CODE, size, size)
        val qrBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
            for (x in 0 until size) {                for (y in 0 until size) {
                it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
            }
            }
        }

        qrImage.setImageBitmap(qrBitmap)


    }

}