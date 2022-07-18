package nz.co.redice.mycryptorates.presentation

import androidx.lifecycle.*
import com.github.mikephil.charting.data.LineDataSet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nz.co.redice.mycryptorates.EMPTY_LABEL
import nz.co.redice.mycryptorates.domain.*
import javax.inject.Inject


@HiltViewModel
class SharedViewModel @Inject constructor(
    private val getCoinInfoUseCase: GetCoinInfoUseCase,
    private val getCoinInfoListUseCase: GetCoinInfoListUseCase,
    private val getTopCoinListUseCase: GetTopCoinListUseCase,
    private val getHistoryDataUseCase: GetHistoryDataUseCase,
) : ViewModel() {


    init {
        viewModelScope.launch {
            //background updates. 15 min interval
            getTopCoinListUseCase()
        }
        viewModelScope.launch {
            //foreground updates. 30-60 sec interval
            launchPeriodicRequests()
        }
    }

    private suspend fun launchPeriodicRequests() {
//        TODO()
    }


    private val _selectedCoinInfo = MutableLiveData<CoinInfo>()
    val selectedCoinInfo: LiveData<CoinInfo>
        get() = _selectedCoinInfo

    private val _lineDataSet = MutableLiveData(LineDataSet(listOf(), EMPTY_LABEL))
    val lineDataSet: LiveData<LineDataSet>
        get() = _lineDataSet


    fun coinInfoList() = getCoinInfoListUseCase()

    fun getDetailInfo(fSym: String) = getCoinInfoUseCase(fSym)

    fun selectedCoinInfo(coinInfo: CoinInfo) {
        _selectedCoinInfo.value = coinInfo
    }

    fun makeHistoryRequest(fSymbol: String, timeLimit: String) {
        viewModelScope.launch {
            _lineDataSet.value = getHistoryDataUseCase(fSymbol, timeLimit)
        }
    }

}