package nz.co.redice.mycryptorates.data.network.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class PriceHistoryDto(

    @SerializedName("time")
    @Expose
    val time: Long,

    @SerializedName("high")
    @Expose
    val price: Float,
)