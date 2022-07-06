@file:Suppress("SpellCheckingInspection")

package nz.co.redice.mycryptorates.data.network

import nz.co.redice.mycryptorates.data.network.model.CoinInfoJsonContainerDto
import nz.co.redice.mycryptorates.data.network.model.CoinNameListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top/totalvolfull")
    suspend fun getTopCoinsInfo(
        @Query(QUERY_PARAM_APY_KEY) apiKey: String = "ddc53a64e0d5f3d58d20e0765534af255eb649361a24697d3a207c96676a75a1",
        @Query(QUERY_PARAM_LIMIT) limit: Int = 10,
        @Query(QUERY_PARAM_TO_SYMBOL) tSym: String = CURRENCY
    ): CoinNameListDto


    @GET("pricemultifull")
    suspend fun getFullPriceList(
        @Query(QUERY_PARAM_APY_KEY) apiKey: String = "ddc53a64e0d5f3d58d20e0765534af255eb649361a24697d3a207c96676a75a1",
        @Query(QUERY_PARAM_FROM_SYMBOLS) fSyms: String,
        @Query(QUERY_PARAM_TO_SYMBOLS) tSyms: String = CURRENCY
        ): CoinInfoJsonContainerDto

    companion object {
        const val QUERY_PARAM_APY_KEY = "api_key"
        const val QUERY_PARAM_LIMIT = "limit"
        const val QUERY_PARAM_TO_SYMBOL = "tsym"

        const val QUERY_PARAM_TO_SYMBOLS = "tsyms"
        const val QUERY_PARAM_FROM_SYMBOLS = "fsyms"
        const val CURRENCY = "USD"
    }
}