package com.elevatedideashub.cloverreceiptdisplay

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class LoyaltyRedeemedMessageDisplay : Activity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.qr_code_display)

        setSystemUiVisibility()

        val notificationText = findViewById<TextView>(R.id.textView)

        notificationText.text = getString(R.string.LoyaltyOfferProcessedMessage)
    }


    fun setSystemUiVisibility() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }
}