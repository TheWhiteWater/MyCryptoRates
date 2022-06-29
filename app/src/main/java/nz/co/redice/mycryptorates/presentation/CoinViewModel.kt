package nz.co.redice.mycryptorates.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import nz.co.redice.mycryptorates.data.network.repository.CoinRepositoryImpl
import nz.co.redice.mycryptorates.domain.GetCoinInfoListUseCase
import nz.co.redice.mycryptorates.domain.GetCoinInfoUseCase
import nz.co.redice.mycryptorates.domain.LoadDataUseCase

class CoinViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = CoinRepositoryImpl(application)
    private val getCoinInfoUseCase = GetCoinInfoUseCase(repository)
    private val getCoinInfoListUseCase = GetCoinInfoListUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)
    fun coinInfoList() = getCoinInfoListUseCase()

    init {
            loadDataUseCase()
    }

    fun getDetailInfo(fSym: String) = getCoinInfoUseCase(fSym)

}