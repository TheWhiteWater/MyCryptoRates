package nz.co.redice.mycryptorates.di.modules

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import nz.co.redice.mycryptorates.data.database.AppDatabase
import nz.co.redice.mycryptorates.data.database.CoinInfoDao
import nz.co.redice.mycryptorates.data.network.ApiFactory
import nz.co.redice.mycryptorates.data.network.ApiService
import nz.co.redice.mycryptorates.data.network.repository.CoinRepositoryImpl
import nz.co.redice.mycryptorates.di.ApplicationScope
import nz.co.redice.mycryptorates.domain.CoinRepository


@Module
interface DataModule  {

    @Binds
    @ApplicationScope
    fun bindCoinRepository(impl: CoinRepositoryImpl): CoinRepository

    companion object {
        @Provides
        @ApplicationScope
        fun provideCoinInfoDao(context: Context): CoinInfoDao {
             return AppDatabase.getInstance(context).coinInfoDao()
        }

        @Provides
        @ApplicationScope
        fun provideCoinApiService(): ApiService {
            return ApiFactory.apiService
        }
    }
}