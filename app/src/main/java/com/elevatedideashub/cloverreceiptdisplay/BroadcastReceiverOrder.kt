package com.elevatedideashub.cloverreceiptdisplay


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.clover.sdk.v1.Intents
import com.elevatedideashub.cloverreceiptdisplay.CloverReceiptApplication.Companion.merchantDetails
import kotlin.math.log

object IntentConstants{
    const val messageIntent = "messageTextIntent"
    const val qrIntent = "qrIntent"
}

 class BroadcastReceiverOrder : BroadcastReceiver() {

     override fun onReceive(context: Context, intent: Intent) {


     var qrCodeContent = ""
         var userMessage = ""

         val bundle = Bundle()
         val outGoingIntent = Intent(context, QRCodeDisplay()::class.java)
Log.d("---------------","-----------------------------------------")
         when (intent?.action) {
             Intents.ACTION_PAYMENT_PROCESSED -> {
                 //outGoingIntent.action = intent.action
                 val cloverPaymentId = intent.getStringExtra(Intents.EXTRA_CLOVER_PAYMENT_ID)
                 Log.d("PaymentID", cloverPaymentId)
                 outGoingIntent.putExtra(IntentConstants.qrIntent,
                     "${context.getString(R.string.host)}receipt/receiptNumber/${cloverPaymentId}/${
                         context.getString(R.string.clover_business_id)
                     }")
                 outGoingIntent.putExtra(IntentConstants.messageIntent, context.getString(R.string.receipt_scanning_instructions))
                 //outGoingIntent.addFlags(FLAG_ACTIVITY_CLEAR_TASK)
Log.d("Outgoing intents set", "true")
             }
             Intents.ACTION_MODIFY_ORDER -> {
                 while (!merchantDetails.isLoaded) {
                     Thread.sleep(100)
                 }

                 val merchantId = merchantDetails.merchantId
                 val cloverOrderId = intent.getStringExtra(Intents.EXTRA_CLOVER_ORDER_ID)

                 outGoingIntent.putExtra(IntentConstants.qrIntent,                      "${context.getString(R.string.host)}loyaltyCardRedemption/${cloverOrderId}/${context.getString(R.string.clover_business_id)}/${merchantId}")
                 outGoingIntent.putExtra(IntentConstants.messageIntent, context.getString(R.string.loyalty_card_redemption_instructions))
             }
         }

         outGoingIntent.flags = FLAG_ACTIVITY_NEW_TASK
         //outGoingIntent.addFlags(FLAG_ACTIVITY_BROUGHT_TO_FRONT)
         //outGoingIntent.addFlags(FLAG_ACTIVITY_SINGLE_TOP)
         //outGoingIntent.addFlags(FLAG_ACTIVITY_REORDER_TO_FRONT)
         //outGoingIntent.addFlags(FLAG_ACTIVITY_RESET_TASK_IF_NEEDED) - this made the screen go all blank
         //outGoingIntent.addFlags(FLAG_ACTIVITY_PREVIOUS_IS_TOP)
         //outGoingIntent.addFlags(FLAG_ACTIVITY_NEW_TASK)
         //outGoingIntent.addFlags(FLAG_ACTIVITY_SINGLE_TOP) - this would mean that the new activity doesn't create another activity in the history - i.e. no further presses of the back button
         Log.d("New activity", "About to be started")
         Log.d("Flags Count", outGoingIntent.flags.toString())

         context.startActivity(outGoingIntent, bundle)
         //ContextCompat.startActivity(context, outGoingIntent, bundle)

         Log.d("New Activity Started", "Yes!!!")
         //var qrCodeDisplay = QRCodeDisplay()

         //qrCodeDisplay.displayQRCode(qrCodeContent, userMessage)
         //qrCodeDisplay.startActivity(Intent())
         //Log.d("Flags", intent.flags.toString())
         //outGoingIntent.flags = intent.flags
         //qrCodeDisplay.startActivity(outGoingIntent)
         //context.applicationContext.startActivity(outGoingIntent, null)

     }
 }