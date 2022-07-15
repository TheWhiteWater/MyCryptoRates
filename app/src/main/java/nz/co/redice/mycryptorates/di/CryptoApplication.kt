package nz.co.redice.mycryptorates.di

import androidx.multidex.MultiDexApplication
import androidx.work.Configuration
import nz.co.redice.mycryptorates.data.database.AppDatabase
import nz.co.redice.mycryptorates.data.mapper.CoinMapper
import nz.co.redice.mycryptorates.data.network.ApiFactory
import nz.co.redice.mycryptorates.data.network.workers.RefreshDataWorkerFactory

class CryptoApplication : MultiDexApplication(), Configuration.Provider {

    val component by lazy { DaggerApplicationComponent.factory().create(this) }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(
                RefreshDataWorkerFactory(
                    AppDatabase.getInstance(this).coinInfoDao(),
                    ApiFactory.apiService,
                    CoinMapper()
                )
            )
            .build()
    }
}