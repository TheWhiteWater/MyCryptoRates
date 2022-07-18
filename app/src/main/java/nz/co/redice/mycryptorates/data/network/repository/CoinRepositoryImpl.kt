package nz.co.redice.mycryptorates.data.network.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import dagger.hilt.android.qualifiers.ApplicationContext
import nz.co.redice.mycryptorates.*
import nz.co.redice.mycryptorates.data.database.CoinInfoDao
import nz.co.redice.mycryptorates.data.mapper.CoinMapper
import nz.co.redice.mycryptorates.data.network.ApiService
import nz.co.redice.mycryptorates.data.network.model.CoinPriceHistoryContainerDto
import nz.co.redice.mycryptorates.data.network.workers.RefreshCoinListWorker
import nz.co.redice.mycryptorates.domain.CoinInfo
import nz.co.redice.mycryptorates.domain.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val mapper: CoinMapper,
    private val coinInfoDao: CoinInfoDao,
    private val apiService: ApiService
) : CoinRepository {

    private val workManager by lazy {
        WorkManager.getInstance(context.applicationContext)
    }

    override fun getCoinInfoList(): LiveData<List<CoinInfo>> {
        return Transformations.map(coinInfoDao.getPriceList()) { list ->
            list.map { mapper.mapDbModelToEntity(it) }
        }
    }

    override fun getCoinInfo(fSymbol: String): LiveData<CoinInfo> {
        return Transformations.map(coinInfoDao.getPriceInfoAboutCoin(fSymbol)) {
            mapper.mapDbModelToEntity(it)
        }
    }

    override fun startWorkerLoadingData() {
        workManager.enqueueUniquePeriodicWork(
            RefreshCoinListWorker.NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            RefreshCoinListWorker.workRequest
        )
    }

    override fun stopWorkerLoadingData() {
        workManager.cancelAllWorkByTag(RefreshCoinListWorker.TAG)
    }

    override suspend fun getHistoricalRequest(fSymbol: String,timeLimit: String)
    : LineDataSet {
        var jsonContainer = CoinPriceHistoryContainerDto()

        when (timeLimit) {
            ONE_HOUR -> jsonContainer =
                apiService.getMinutelyPriceHistory(fSym = fSymbol, limit = timeLimit)
            TWENTY_FOUR_HOURS -> jsonContainer =
                apiService.getHourlyPriceHistory(fSym = fSymbol, limit = timeLimit)
            SEVEN_DAYS -> jsonContainer =
                apiService.getDaylyPriceHistory(fSym = fSymbol, limit = timeLimit)
            ONE_MONTH -> jsonContainer =
                apiService.getDaylyPriceHistory(fSym = fSymbol, limit = timeLimit)
            THREE_MONTHS -> jsonContainer =
                apiService.getDaylyPriceHistory(fSym = fSymbol, limit = timeLimit)
            ONE_YEAR -> jsonContainer =
                apiService.getDaylyPriceHistory(fSym = fSymbol, limit = timeLimit)
            ALL_DATA -> jsonContainer = apiService.getAllPriceHistory(fSym = fSymbol)
        }
        val listContainer = jsonContainer.priceList

        listContainer?.list?.let {
            val entryList = mutableListOf<Entry>()
            var count = 1f
            it.forEach { historyPriceDto ->
                entryList.add(Entry(count++, historyPriceDto.price))
            }
            return LineDataSet(entryList, fSymbol)
        }
        return LineDataSet(listOf(), EMPTY_LABEL)
    }
}


