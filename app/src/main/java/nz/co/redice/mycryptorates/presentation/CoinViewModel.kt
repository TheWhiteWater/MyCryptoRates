package nz.co.redice.mycryptorates.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nz.co.redice.mycryptorates.domain.CoinInfo
import nz.co.redice.mycryptorates.domain.GetCoinInfoListUseCase
import nz.co.redice.mycryptorates.domain.GetCoinInfoUseCase
import nz.co.redice.mycryptorates.domain.LoadDataUseCase
import javax.inject.Inject

class CoinViewModel @Inject constructor(
    private val getCoinInfoUseCase: GetCoinInfoUseCase,
    private val getCoinInfoListUseCase: GetCoinInfoListUseCase,
    loadDataUseCase: LoadDataUseCase,
) : ViewModel() {


    init {
        loadDataUseCase()
        Log.d("TAG", "view model init: $this")
    }

    private val _selectedCoinInfo = MutableLiveData<CoinInfo>()
    val selectedCoinInfo: LiveData<CoinInfo>
        get() = _selectedCoinInfo

    fun coinInfoList() = getCoinInfoListUseCase()

    fun getDetailInfo(fSym: String) = getCoinInfoUseCase(fSym)

    fun selectedCoinInfo(coinInfo: CoinInfo) {
        _selectedCoinInfo.value = coinInfo
    }

}