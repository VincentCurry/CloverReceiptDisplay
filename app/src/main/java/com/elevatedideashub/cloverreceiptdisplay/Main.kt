package com.elevatedideashub.cloverreceiptdisplay

import android.app.Activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color

import android.widget.TextView

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.clover.sdk.v1.Intents
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter


class Main : Activity() {

    private var qrImage: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qr_code_display)
        qrImage = findViewById<ImageView>(R.id.qrCodeView)
        qrImage!!.visibility = View.GONE
        val closeButton = findViewById<Button>(com.elevatedideashub.cloverreceiptdisplay.R.id.closeButton)
        val instructionsText = findViewById<TextView>(R.id.textView)

        instructionsText.text="Scan QR Code to Redeem loyalty card"
        closeButton.setOnClickListener {
            moveTaskToBack(true) }
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
    }
}