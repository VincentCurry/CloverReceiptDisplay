package com.elevatedideashub.cloverreceiptdisplay

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.clover.sdk.v1.Intents.EXTRA_ORDER_ID

class RedeemLoyaltyCardActivity : Activity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = Bundle()
        val outGoingIntent = Intent(this, QRCodeDisplay()::class.java)
        val cloverOrderId = intent.getStringExtra(EXTRA_ORDER_ID)

        while(!CloverReceiptApplication.merchantDetails.isLoaded){
            Thread.sleep(100)
        }
        val merchantId = CloverReceiptApplication.merchantDetails.merchantId

        outGoingIntent.putExtra(IntentConstants.messageIntent, this.getString( R.string.loyalty_card_redemption_instructions))
        outGoingIntent.putExtra(IntentConstants.qrIntent, "${this.getString(R.string.host)}loyaltyCardRedemption/${cloverOrderId}/${this.getString(R.string.clover_business_id)}/${merchantId}")

        outGoingIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK


        this.startActivity( outGoingIntent, bundle)

        finish()
}
}