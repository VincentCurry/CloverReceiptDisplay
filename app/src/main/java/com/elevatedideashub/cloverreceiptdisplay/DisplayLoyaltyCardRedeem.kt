package com.elevatedideashub.cloverreceiptdisplay

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.clover.sdk.v1.Intents
import com.clover.sdk.v3.merchant.MerchantDevicesV2Connector
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter

class DisplayLoyaltyCardRedeem : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.qr_code_display)

        setSystemUiVisibility()

        val deviceConnector = MerchantDevicesV2Connector(this)
        val deviceId = deviceConnector.device.id

        Log.d("DeviceId", deviceId)
        //val cloverPaymentId = intent.getStringExtra(Intents.EXTRA_CLOVER_PAYMENT_ID)
        val qrImage = findViewById<ImageView>(R.id.qrCodeView)
        val closeButton = findViewById<Button>(R.id.closeButton)
        val instructionsText = findViewById<TextView>(R.id.textView)

        closeButton.setOnClickListener {
            moveTaskToBack(true) }

        instructionsText.text="Scan QR Code to Redeem loyalty card"
        val size = 512
        val qrCodeContent = "${getString(R.string.host)}${deviceId}/${getString(R.string.clover_business_id)}"
        val bits = QRCodeWriter().encode(qrCodeContent, BarcodeFormat.QR_CODE, size, size)
        val qrBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
            for (x in 0 until size) {                for (y in 0 until size) {
                it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
            }
            }
        }

        qrImage.setImageBitmap(qrBitmap)
    }


    private fun setSystemUiVisibility() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }
}