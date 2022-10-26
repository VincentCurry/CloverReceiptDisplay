package com.elevatedideashub.cloverreceiptdisplay

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.clover.sdk.v1.Intents

class PaymentProcessedActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = Bundle()
        val outGoingIntent = Intent(this, QRCodeDisplay()::class.java)

        val cloverPaymentId = intent.getStringExtra(Intents.EXTRA_CLOVER_PAYMENT_ID)
        Log.d("Clover Payment ID", cloverPaymentId)

        outGoingIntent.putExtra(IntentConstants.qrIntent,
            "${this.getString(R.string.host)}receipt/receiptNumber/${cloverPaymentId}/${
                this.getString(R.string.clover_business_id)
            }")
        outGoingIntent.putExtra(IntentConstants.messageIntent, this.getString(R.string.receipt_scanning_instructions))

        outGoingIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        this.startActivity(outGoingIntent, bundle)

        finish()
    }
}