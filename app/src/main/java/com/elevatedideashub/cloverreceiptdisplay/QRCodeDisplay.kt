package com.elevatedideashub.cloverreceiptdisplay

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter


class QRCodeDisplay : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(this.packageName,"On create")
        setContentView(R.layout.qr_code_display)
        setSystemUiVisibility()

        var qrImage = findViewById<ImageView>(R.id.qrCodeView)
        var closeButton = findViewById<Button>(R.id.closeButton)
        var instructionsText = findViewById<TextView>(R.id.textView)

        instructionsText.text = intent.getStringExtra(IntentConstants.messageIntent)

        closeButton.setOnClickListener {
            //moveTaskToBack(true)
            //deleteCache()
            finish()
        }

        //moveTaskToBack(true) }

        val size = 512
        val qrCodeContent = intent.getStringExtra(IntentConstants.qrIntent)

        if(qrCodeContent==null) {
            moveTaskToBack(true)
        } else {
            val bits = QRCodeWriter().encode(qrCodeContent, BarcodeFormat.QR_CODE, size, size)
            val qrBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
                for (x in 0 until size) {
                    for (y in 0 until size) {
                        it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                    }
                }
            }

            qrImage.setImageBitmap(qrBitmap)

            Log.d("QR Code Content", qrCodeContent)
            Log.d("Instructions", intent.getStringExtra(IntentConstants.messageIntent))
        }

    }

    override fun onStart() {
        super.onStart()
        Log.d("QRCodeDisplay", "On start")
    }

    override fun onResume() {
        super.onResume()
        Log.d("QRCodeDisplay", "On Resume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("QRCodeDisplay", "On Pause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("QRCodeDisplay", "On Stop")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d("QRCodeDisplay", "onNewIntent")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("QRCodeDisplay", "On Destroy")
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