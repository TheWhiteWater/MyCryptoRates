package nz.co.redice.mycryptorates.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nz.co.redice.mycryptorates.data.database.AppDatabase
import nz.co.redice.mycryptorates.data.database.CoinInfoDao
import nz.co.redice.mycryptorates.data.network.ApiFactory
import nz.co.redice.mycryptorates.data.network.ApiService
import nz.co.redice.mycryptorates.data.network.repository.CoinRepositoryImpl
import nz.co.redice.mycryptorates.domain.CoinRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindCoinRepository(impl: CoinRepositoryImpl): CoinRepository

    companion object {
        private const val DB_NAME = "main.db"


        @Provides
        @Singleton
        fun provideCoinInfoDao(database: AppDatabase): CoinInfoDao {
            return database.coinInfoDao()
        }

        @Provides
        @Singleton
        fun provideCoinDatabase(@ApplicationContext context: Context): AppDatabase {
            return return Room.databaseBuilder(context, AppDatabase::class.java,DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }


        @Provides
        @Singleton
        fun provideCoinApiService(): ApiService {
            return ApiFactory.apiService
        }
    }
}