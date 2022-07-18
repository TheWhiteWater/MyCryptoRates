package nz.co.redice.mycryptorates.data.mapper

import com.google.gson.Gson
import nz.co.redice.mycryptorates.data.database.CoinInfoDbModel
import nz.co.redice.mycryptorates.data.network.model.CoinInfoDto
import nz.co.redice.mycryptorates.data.network.model.CoinInfoJsonContainerDto
import nz.co.redice.mycryptorates.data.network.model.CoinNameListDto
import nz.co.redice.mycryptorates.domain.CoinInfo
import java.sql.Timestamp
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CoinMapper @Inject constructor() {

    fun mapDtoToDbModel(dto: CoinInfoDto) = CoinInfoDbModel(
        fromSymbol = dto.fromSymbol,
        toSymbol = dto.toSymbol,
        price = dto.price,
        lastUpdate = dto.lastUpdate,
        highDay = dto.highDay,
        lowDay = dto.lowDay,
        lastMarket = dto.lastMarket,
        imageUrl = BASE_IMAGE_URL + dto.imageUrl
    )

    fun mapJsonContainerToListCoinInfo(jsonContainer: CoinInfoJsonContainerDto): List<CoinInfoDto> {
        val result = mutableListOf<CoinInfoDto>()
        val jsonObject = jsonContainer.json ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val coinInfoDto = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinInfoDto::class.java
                )
                result.add(coinInfoDto)
            }
        }
        return result
    }

    fun mapNamesListToString(nameListDto: CoinNameListDto): String {
        return nameListDto.names?.map {
            it.coinName?.name
        }?.joinToString(",") ?: ""
    }

    fun mapDbModelToEntity(dbModel: CoinInfoDbModel) = CoinInfo(
        fromSymbol = dbModel.fromSymbol,
        toSymbol = dbModel.toSymbol,
        price = convertLongIntoCurrency(dbModel.price),
        lastUpdate = convertTimestampToTime(dbModel.lastUpdate),
        highDay = convertLongIntoCurrency(dbModel.highDay),
        lowDay = convertLongIntoCurrency(dbModel.lowDay),
        lastMarket = dbModel.lastMarket,
        imageUrl = dbModel.imageUrl
    )




    private fun convertTimestampToTime(timestamp: Long?): String {
        if (timestamp == null) return EMPTY_STRING
        val stamp = Timestamp(timestamp * 1000)
        val date = Date(stamp.time)
        val pattern = "HH:mm:ss"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    private fun convertLongIntoCurrency(num: Long?): String {
        return if (num != null) {
            val format: NumberFormat = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 2
            format.currency = Currency.getInstance("USD")
            format.format(num)
        } else
            ""
    }

    companion object {
        const val BASE_IMAGE_URL = "https://cryptocompare.com"
        const val EMPTY_STRING = ""
    }

}