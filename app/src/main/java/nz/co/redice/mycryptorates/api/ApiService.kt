package nz.co.redice.mycryptorates.api

import io.reactivex.Single
import nz.co.redice.mycryptorates.pojo.CoinInfoListOfData
import nz.co.redice.mycryptorates.pojo.CoinPriceInfoRawData
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top/totalvolfull")
    fun getTopCoinsInfo(
        @Query(QUERY_PARAM_APY_KEY) apiKey: String = "af1f4293ebff768310af445e9e0573e652a2f3fd6a0eef1d667c331eb41439b9",
        @Query(QUERY_PARAM_LIMIT) limit: Int = 10,
        @Query(QUERY_PARAM_TO_SYMBOL) tSym: String = CURRENCY
    ): Single<CoinInfoListOfData>


    @GET("pricemultifull")
    fun getFullPriceList(
        @Query(QUERY_PARAM_APY_KEY) apiKey: String = "af1f4293ebff768310af445e9e0573e652a2f3fd6a0eef1d667c331eb41439b9",
        @Query(QUERY_PARAM_FROM_SYMBOLS) fSyms: String,
        @Query(QUERY_PARAM_TO_SYMBOLS) tSyms: String = CURRENCY
        ): Single<CoinPriceInfoRawData>

    companion object {
        const val QUERY_PARAM_APY_KEY = "api_key"
        const val QUERY_PARAM_LIMIT = "limit"
        const val QUERY_PARAM_TO_SYMBOL = "tsym"

        const val QUERY_PARAM_TO_SYMBOLS = "tsyms"
        const val QUERY_PARAM_FROM_SYMBOLS = "fsyms"
        const val CURRENCY = "NZD"
    }
}