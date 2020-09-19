package nz.co.redice.mycryptorates.pojo

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class CoinPriceInfoRawData(
    @SerializedName("RAW")
    @Expose
    val CoinPriceInfoGsonObject: JsonObject? = null
)