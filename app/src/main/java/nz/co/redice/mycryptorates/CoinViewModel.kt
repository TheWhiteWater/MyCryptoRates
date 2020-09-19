package nz.co.redice.mycryptorates

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import nz.co.redice.mycryptorates.api.ApiFactory
import nz.co.redice.mycryptorates.database.AppDataBase

class CoinViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDataBase.getInstance(application)
    val priceList = db.coinPriceInfoDao().getPriceList()
    private val compositeDisposable = CompositeDisposable()
    private val TAG = this::class.java.simpleName.toString()


    fun loadData() {

        val disposable = ApiFactory.apiService.getTopCoinsInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                it.data?.map {
                    it.coinInfo?.name
                }?.joinToString(",")
            }
            .map {
                Log.d(TAG, it)
                it
            }
            .flatMap {
                ApiFactory.apiService.getFullPriceList(fSyms = it)
            }
            .subscribe({
                Log.d(TAG, it.toString())
            }, {
                Log.e(TAG, it.message.toString())
            })

//val disposable = ApiFactory.apiService.getFullPriceList(fSyms = "BTC,UNI,ETH,LINK,TRX,BCH,XRP,EOS,NEO,BNB")
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                Log.d(TAG, it.toString())
//            }, {
//                Log.e(TAG, it.message.toString())
//            })


        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}