package nz.co.redice.mycryptorates.presentation

import android.app.Application
import androidx.work.Configuration
import nz.co.redice.mycryptorates.data.network.workers.RefreshDataWorkerFactory
import nz.co.redice.mycryptorates.di.DaggerApplicationComponent
import javax.inject.Inject

class CoinApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: RefreshDataWorkerFactory

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }


    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }
}