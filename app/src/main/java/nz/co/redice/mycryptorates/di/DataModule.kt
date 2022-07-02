package nz.co.redice.mycryptorates.di

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import nz.co.redice.mycryptorates.data.database.AppDatabase
import nz.co.redice.mycryptorates.data.database.CoinInfoDao
import nz.co.redice.mycryptorates.data.network.repository.CoinRepositoryImpl
import nz.co.redice.mycryptorates.domain.CoinRepository

@Module
interface DataModule {

    @Binds
    fun bindCoinRepository(impl: CoinRepositoryImpl): CoinRepository

    companion object {
        @Provides
        fun provideCoinInfoDao(
            application: Application
        ): CoinInfoDao {
             return AppDatabase.getInstance(application).coinInfoDao()
        }
    }
}