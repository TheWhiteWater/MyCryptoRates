package nz.co.redice.mycryptorates.data.network.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class CoinPriceHistoryContainerDto(

    @SerializedName("Data")
    @Expose
    val priceList: CoinPriceHistoryDto? = null
)
