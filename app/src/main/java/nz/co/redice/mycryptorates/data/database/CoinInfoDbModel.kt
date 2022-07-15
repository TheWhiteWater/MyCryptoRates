package nz.co.redice.mycryptorates.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "full_price_list")
data class CoinInfoDbModel(
    @PrimaryKey
    val fromSymbol: String,
    val toSymbol: String?,
    val price: Long?,
    val lastUpdate: Long?,
    val highDay: Long?,
    val lowDay: Long?,
    val lastMarket: String?,
    val imageUrl: String
)