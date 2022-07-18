package nz.co.redice.mycryptorates.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        CoinInfoDbModel::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun coinInfoDao(): CoinInfoDao
}


