package nz.co.redice.mycryptorates.presentation

import android.app.Application
import androidx.work.Configuration
import nz.co.redice.mycryptorates.data.database.AppDatabase
import nz.co.redice.mycryptorates.data.mapper.CoinMapper
import nz.co.redice.mycryptorates.data.network.ApiFactory
import nz.co.redice.mycryptorates.data.network.workers.RefreshDataWorkerFactory
import nz.co.redice.mycryptorates.di.DaggerApplicationComponent

class CoinApp : Application(), Configuration.Provider {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(
                RefreshDataWorkerFactory(
                    AppDatabase.getInstance(this).coinInfoDao(),
                    ApiFactory.apiService,
                    CoinMapper()
                )
            ).build()

    }
}