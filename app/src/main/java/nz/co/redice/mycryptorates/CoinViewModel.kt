package nz.co.redice.mycryptorates

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import nz.co.redice.mycryptorates.api.ApiFactory
import nz.co.redice.mycryptorates.database.AppDataBase
import nz.co.redice.mycryptorates.pojo.CoinPriceInfo
import nz.co.redice.mycryptorates.pojo.CoinPriceInfoRawData
import java.util.concurrent.TimeUnit

class CoinViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDataBase.getInstance(application)
    val priceList = db.coinPriceInfoDao().getPriceList()
    private val compositeDisposable = CompositeDisposable()
    private val TAG = this::class.java.simpleName.toString()

    init {
        loadData()
    }

    private fun loadData() {

        val disposable = ApiFactory.apiService.getTopCoinsInfo()

            .map {
                it.data?.map {
                    it.coinInfo?.name
                }?.joinToString(",")
            }
            .flatMap {
                ApiFactory.apiService.getFullPriceList(fSyms = it)
            }
            .map { getPriceListFromRawData(it) }
            .subscribeOn(Schedulers.io())
            .delaySubscription(5, TimeUnit.SECONDS)
            .repeat()
            .retry()
            .subscribe({
                it?.let { list -> db.coinPriceInfoDao().insertPriceList(list) }
            }, {
                Log.e(TAG, it.stackTraceToString())
            })

        compositeDisposable.add(disposable)
    }


    private fun getPriceListFromRawData(coinPriceInfoRawData: CoinPriceInfoRawData)
            : List<CoinPriceInfo>? {

        val jsonObject = coinPriceInfoRawData.CoinPriceInfoGsonObject ?: return null
        val result = ArrayList<CoinPriceInfo>()

        val coinKeySet = jsonObject.keySet()

        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinPriceInfo::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    fun getDetailInfo(fSym: String) = db.coinPriceInfoDao().getPriceInfoAboutCoin(fSym)


    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}