package nz.co.redice.mycryptorates.presentation

import androidx.lifecycle.ViewModel
import nz.co.redice.mycryptorates.domain.GetCoinInfoListUseCase
import nz.co.redice.mycryptorates.domain.GetCoinInfoUseCase
import nz.co.redice.mycryptorates.domain.LoadDataUseCase
import javax.inject.Inject

class CoinViewModel @Inject constructor(
    private val getCoinInfoUseCase : GetCoinInfoUseCase,
    private val getCoinInfoListUseCase : GetCoinInfoListUseCase,
    loadDataUseCase : LoadDataUseCase,
) : ViewModel() {


    init {
            loadDataUseCase()
    }

    fun coinInfoList() = getCoinInfoListUseCase()

    fun getDetailInfo(fSym: String) = getCoinInfoUseCase(fSym)

}