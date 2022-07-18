package nz.co.redice.mycryptorates.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.LineDataSet

interface CoinRepository {
    fun getCoinInfoList(): LiveData<List<CoinInfo>>
    fun getCoinInfo(fSymbol: String): LiveData<CoinInfo>
    fun startWorkerLoadingData()
    fun stopWorkerLoadingData()
    suspend fun getHistoricalRequest(fSymbol: String,timeLimit: String): LineDataSet
}