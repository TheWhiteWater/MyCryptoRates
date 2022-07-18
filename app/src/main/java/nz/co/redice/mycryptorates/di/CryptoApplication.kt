package nz.co.redice.mycryptorates.di

import androidx.multidex.MultiDexApplication
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import nz.co.redice.mycryptorates.data.network.workers.RefreshCoinListWorkerFactory
import javax.inject.Inject

@HiltAndroidApp
class CryptoApplication : MultiDexApplication(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: RefreshCoinListWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
                .setWorkerFactory(workerFactory)
                .setMinimumLoggingLevel(android.util.Log.DEBUG)
                .build()
    }
}