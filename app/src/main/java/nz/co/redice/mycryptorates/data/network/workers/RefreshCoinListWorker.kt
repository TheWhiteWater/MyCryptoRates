package nz.co.redice.mycryptorates.data.network.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import nz.co.redice.mycryptorates.data.database.CoinInfoDao
import nz.co.redice.mycryptorates.data.mapper.CoinMapper
import nz.co.redice.mycryptorates.data.network.ApiService
import java.util.concurrent.TimeUnit

@HiltWorker
class RefreshCoinListWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val coinInfoDao: CoinInfoDao,
    private val apiService: ApiService,
    private val mapper: CoinMapper
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        try {
            val topCoins = apiService.getTopCoinsInfo(limit = 50)
            val fSyms = mapper.mapNamesListToString(topCoins)
            val gsonContainer = apiService.getFullPriceList(fSyms = fSyms)
            val coinInfoDtoList = mapper.mapJsonContainerToListCoinInfo(gsonContainer)
            val dbModelList = coinInfoDtoList.map { mapper.mapDtoToDbModel(it) }
            coinInfoDao.insertPriceList(dbModelList)
        } catch (e: Exception) {
        }
        return Result.success()
    }


    companion object {
        const val NAME = "RefreshDataWorker"
        const val TAG = "RefreshCoinList"

        private fun createConstraints() = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<RefreshCoinListWorker>(15, TimeUnit.MINUTES)
                .setConstraints(createConstraints())
                .addTag(TAG)
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                .build()
    }
}

