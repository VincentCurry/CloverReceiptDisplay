package com.elevatedideashub.cloverreceiptdisplay

import android.accounts.Account
import android.app.Application
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import com.clover.sdk.util.CloverAccount
import com.clover.sdk.v1.BindingException
import com.clover.sdk.v1.ClientException
import com.clover.sdk.v1.ServiceException
import com.clover.sdk.v1.merchant.MerchantConnector

class CloverReceiptApplication : Application () {
    companion object {
        var merchantDetails = Merchant("" , "", false)
    }
}



data class Merchant(
    var merchantId: String,
    var deviceId: String,
    var isLoaded: Boolean
)

class MerchantDetails ()
{

    private var account: Account? = null

    private var merchantConnector: MerchantConnector? = null
    fun getMerchantDetails(context: Context){

        class NullableVoidNullableVoidNullableMerchantAsyncTask :
            AsyncTask<Void?, Void?, com.clover.sdk.v1.merchant.Merchant?>() {
                override fun onPreExecute() {
                    super.onPreExecute()
                }

                override fun doInBackground(vararg p0: Void?): com.clover.sdk.v1.merchant.Merchant? {

                    if (account == null) {
                        account = CloverAccount.getAccount(context)
                        merchantConnector = MerchantConnector(context, account, null)
                    }

                    var merchant: com.clover.sdk.v1.merchant.Merchant? = null
                    try {
                        merchant = merchantConnector?.merchant
                    } catch (e: RemoteException) {
                        e.printStackTrace()
                    } catch (e: ClientException) {
                        e.printStackTrace()
                    } catch (e: ServiceException) {
                        e.printStackTrace()
                    } catch (e: BindingException) {
                        e.printStackTrace()
                    }
                    return merchant
                }

                override fun onPostExecute(merchant: com.clover.sdk.v1.merchant.Merchant?) {
                    Log.d("Merchant details", "OnPostExecute")
                    super.onPostExecute(merchant)
                    //if (!isFinishing) {
                    if (merchant != null) {
                        Log.d("Merchant details", "Writing")
                        Log.d("Merchant id", merchant.id)
                        Log.d("Device id", merchant.deviceId)
                        CloverReceiptApplication.merchantDetails.merchantId = merchant.id
                        CloverReceiptApplication.merchantDetails.deviceId = merchant.deviceId
                        CloverReceiptApplication.merchantDetails.isLoaded = true
                    }
                    merchantConnector?.disconnect()
                    merchantConnector = null
                    account = null
                    //}
                }

            }

        val execute = NullableVoidNullableVoidNullableMerchantAsyncTask().execute()
    }
}

class ListeningService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
        var getMerchantDetails: MerchantDetails = MerchantDetails()

        getMerchantDetails.getMerchantDetails(this)
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("ListeningService" , "onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ListeningService", "OnDestroy")
    }
}