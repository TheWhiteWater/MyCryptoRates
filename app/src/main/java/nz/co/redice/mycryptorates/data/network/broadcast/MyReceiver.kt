package nz.co.redice.mycryptorates.data.network.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import nz.co.redice.mycryptorates.data.network.repository.CoinRepositoryImpl
import javax.inject.Inject

class MyReceiver @Inject constructor(
    private val repository: CoinRepositoryImpl
) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
           ACTION_VIEW_MODEL_CLEARED -> {
               repository.startWorkerLoadingData()
               Log.d("TAAG", "onReceive: worker started")
           }

        }
    }

    companion object {
        const val ACTION_VIEW_MODEL_CLEARED = "cleared"
    }
}