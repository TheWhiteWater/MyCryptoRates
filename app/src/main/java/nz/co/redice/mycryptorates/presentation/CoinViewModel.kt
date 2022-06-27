package nz.co.redice.mycryptorates.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import nz.co.redice.mycryptorates.data.network.ApiFactory
import nz.co.redice.mycryptorates.data.database.AppDatabase
import nz.co.redice.mycryptorates.data.network.model.CoinInfoDto
import nz.co.redice.mycryptorates.data.network.model.CoinInfoJsonContainerDto
import java.util.concurrent.TimeUnit

class CoinViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    val priceList = db.coinInfoDao().getPriceList()
    private val compositeDisposable = CompositeDisposable()
    private val TAG = this::class.java.simpleName.toString()

    init {
        loadData()
    }

    private fun loadData() {


    }


    private fun getPriceListFromRawData(coinInfoJsonContainerDto: CoinInfoJsonContainerDto)
            : List<CoinInfoDto>? {

        val jsonObject = coinInfoJsonContainerDto.json ?: return null
        val result = ArrayList<CoinInfoDto>()

        val coinKeySet = jsonObject.keySet()

        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinInfoDto::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    fun getDetailInfo(fSym: String) = db.coinInfoDao().getPriceInfoAboutCoin(fSym)


    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}