package com.elevatedideashub.cloverreceiptdisplay

import android.app.Activity
import android.R

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color

import android.widget.TextView

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.clover.sdk.v1.Intents
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter


class Main : Activity() {
    val EXTRA_DATA = "data"

    private var qrImage: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.elevatedideashub.cloverreceiptdisplay.R.layout.qr_code_display)
        qrImage = findViewById<ImageView>(com.elevatedideashub.cloverreceiptdisplay.R.id.qrCodeView)
        qrImage!!.visibility = View.GONE
    }

    override fun onNewIntent(intent: Intent) {
        qrImage!!.visibility = View.VISIBLE
        val size = 512
        val qrCodeContent = getString(com.elevatedideashub.cloverreceiptdisplay.R.string.app_url)
        val hints = hashMapOf<EncodeHintType, Int>().also { it[EncodeHintType.MARGIN] = 1 } // Make the QR code buffer border narrower
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