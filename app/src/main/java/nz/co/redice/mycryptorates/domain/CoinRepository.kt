package nz.co.redice.mycryptorates.domain

import androidx.lifecycle.LiveData

interface CoinRepository  {
fun getCoinInfoList(): LiveData<List<CoinInfo>>
fun getCoinInfo(fSymbol:String): LiveData<CoinInfo>
suspend fun loadData()
}