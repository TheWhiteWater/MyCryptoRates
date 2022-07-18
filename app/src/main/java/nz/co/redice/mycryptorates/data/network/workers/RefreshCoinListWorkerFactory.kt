package nz.co.redice.mycryptorates.data.network.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import nz.co.redice.mycryptorates.data.database.CoinInfoDao
import nz.co.redice.mycryptorates.data.mapper.CoinMapper
import nz.co.redice.mycryptorates.data.network.ApiService
import javax.inject.Inject

class RefreshCoinListWorkerFactory @Inject constructor(
    private val coinInfoDao: CoinInfoDao,
    private val apiService: ApiService,
    private val mapper: CoinMapper
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context, workerClassName: String, workerParameters: WorkerParameters
    ): ListenableWorker {
        return RefreshCoinListWorker(
            appContext,
            workerParameters,
            coinInfoDao,
            apiService,
            mapper
        )
    }
}