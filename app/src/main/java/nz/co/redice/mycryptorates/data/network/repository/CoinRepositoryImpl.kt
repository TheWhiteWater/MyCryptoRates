package nz.co.redice.mycryptorates.data.network.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import nz.co.redice.mycryptorates.data.database.CoinInfoDao
import nz.co.redice.mycryptorates.data.mapper.CoinMapper
import nz.co.redice.mycryptorates.data.network.ApiService
import nz.co.redice.mycryptorates.data.network.workers.RefreshDataWorker
import nz.co.redice.mycryptorates.domain.CoinInfo
import nz.co.redice.mycryptorates.domain.CoinRepository
import java.util.*
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val context: Context,
    private val mapper: CoinMapper,
    private val coinInfoDao: CoinInfoDao,
    private val apiService: ApiService
) : CoinRepository {

    private val workManager by lazy {
        WorkManager.getInstance(context.applicationContext) }

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
        Log.d("RefreshCoinList", "startWorkerLoadingData: started")
        workManager.enqueueUniquePeriodicWork(
            RefreshDataWorker.NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            RefreshDataWorker.workRequest
        )
    }

    override fun stopWorkerLoadingData() {
        workManager.cancelAllWorkByTag(RefreshDataWorker.TAG)
    }


    override suspend fun loadData() {
        try {
            val topCoins = apiService.getTopCoinsInfo(limit = 50)
            val fSyms = mapper.mapNamesListToString(topCoins)
            val gsonContainer = apiService.getFullPriceList(fSyms = fSyms)
            val coinInfoDtoList = mapper.mapJsonContainerToListCoinInfo(gsonContainer)
            val dbModelList = coinInfoDtoList.map { mapper.mapDtoToDbModel(it) }
            coinInfoDao.insertPriceList(dbModelList)
        } catch (e: Exception) {
        }
    }
}


