package nz.co.redice.mycryptorates.data.network

import nz.co.redice.mycryptorates.data.network.model.CoinInfoJsonContainerDto
import nz.co.redice.mycryptorates.data.network.model.CoinNameListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top/totalvolfull")
    suspend fun getTopCoinsInfo(
        @Query(QUERY_PARAM_APY_KEY) apiKey: String = "af1f4293ebff768310af445e9e0573e652a2f3fd6a0eef1d667c331eb41439b9",
        @Query(QUERY_PARAM_LIMIT) limit: Int = 10,
        @Query(QUERY_PARAM_TO_SYMBOL) tSym: String = CURRENCY
    ): CoinNameListDto


    @GET("pricemultifull")
    suspend fun getFullPriceList(
        @Query(QUERY_PARAM_APY_KEY) apiKey: String = "af1f4293ebff768310af445e9e0573e652a2f3fd6a0eef1d667c331eb41439b9",
        @Query(QUERY_PARAM_FROM_SYMBOLS) fSyms: String,
        @Query(QUERY_PARAM_TO_SYMBOLS) tSyms: String = CURRENCY
        ): CoinInfoJsonContainerDto

    companion object {
        const val QUERY_PARAM_APY_KEY = "api_key"
        const val QUERY_PARAM_LIMIT = "limit"
        const val QUERY_PARAM_TO_SYMBOL = "tsym"

        const val QUERY_PARAM_TO_SYMBOLS = "tsyms"
        const val QUERY_PARAM_FROM_SYMBOLS = "fsyms"
        const val CURRENCY = "NZD"
    }
}