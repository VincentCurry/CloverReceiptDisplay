package com.elevatedideashub.cloverreceiptdisplay

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.clover.sdk.v1.Intents
import com.clover.sdk.v1.app.AppNotificationReceiver
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class BroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        when (intent.action) {
            Intents.ACTION_ORDER_CREATED -> orderWasCreated(intent)
            Intents.ACTION_PAYMENT_PROCESSED -> paymentProcessed(context, intent)
            "com.clover.intent.action.REFUND_PROCESSED" -> refundProcessed(intent)
            "com.clover.intent.action.CREDIT_PROCESSED" -> refundProcessed(intent)
            else -> {}
        }
    }

    private fun paymentProcessed(context: Context?, intent: Intent)
    {
        val merchantId = CloverReceiptApplication.merchantDetails.merchantId
        val paymentId = intent.getStringExtra(Intents.EXTRA_CLOVER_PAYMENT_ID)
        val url= URL("${context?.getString(R.string.host)}clover/payment/${merchantId}/${paymentId}")

        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "POST"
        conn.doOutput = true
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")

        conn.setRequestProperty("Content-Length", "0")
        conn.useCaches = false

        GlobalScope.launch {
            DataOutputStream(conn.outputStream)//.use { it.writeBytes(postData) }
            BufferedReader(InputStreamReader(conn.inputStream)).use { br ->
                var line: String?
                while (br.readLine().also { line = it } != null) {
                    println(line)
                }
            }
        }
    }

    private fun orderWasCreated(intent: Intent)
    {}

    private fun refundProcessed(intent: Intent)
    {}
}