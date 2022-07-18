package nz.co.redice.mycryptorates.data.network.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class CoinPriceHistoryDto(

    @SerializedName("TimeFrom")
    @Expose
    val timeFrom: Long? = null,

    @SerializedName("TimeTo")
    @Expose
    val timeTo: Long? = null,

    @SerializedName("Data")
    @Expose
    val list: List<PriceHistoryDto>? = null
)
