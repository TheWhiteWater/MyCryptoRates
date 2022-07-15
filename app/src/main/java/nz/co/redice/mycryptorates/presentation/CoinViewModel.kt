package nz.co.redice.mycryptorates.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nz.co.redice.mycryptorates.data.network.ApiService
import nz.co.redice.mycryptorates.data.network.model.CoinPriceHistoryContainerDto
import nz.co.redice.mycryptorates.domain.*
import javax.inject.Inject


class CoinViewModel @Inject constructor(
    private val getCoinInfoUseCase: GetCoinInfoUseCase,
    private val getCoinInfoListUseCase: GetCoinInfoListUseCase,
    private val getTopCoinListUseCase: GetTopCoinListUseCase,
    private val startGettingTopCoinListInBackgroundUseCase: StartGettingTopCoinListInBackgroundUseCase,
    private val stopGettingTopCoinListInBackgroundUseCase: StopGettingTopCoinListInBackgroundUseCase,
    private val apiService: ApiService
) : ViewModel() {

    private val priceHistoryList = mutableListOf<Entry>()

    init {
        stopGettingTopCoinListInBackgroundUseCase()
//        startLoadingDataInForeground()
    }

    private val _selectedCoinInfo = MutableLiveData<CoinInfo>()
    val selectedCoinInfo: LiveData<CoinInfo>
        get() = _selectedCoinInfo

    private val _isViewModelOnCleared = MutableLiveData<Any>()
    val isViewModelOnCleared: LiveData<Any>
        get() = _isViewModelOnCleared

    private val _lineDataSet = MutableLiveData(LineDataSet(priceHistoryList, "Empty"))
    val lineDataSet: LiveData<LineDataSet>
        get() = _lineDataSet


    private fun startLoadingDataInForeground() {
        viewModelScope.launch {
            while (true) {
                getTopCoinListUseCase()
                delay(1000 * 60)
            }
        }
    }

    fun coinInfoList() = getCoinInfoListUseCase()

    fun getDetailInfo(fSym: String) = getCoinInfoUseCase(fSym)

    fun selectedCoinInfo(coinInfo: CoinInfo) {
        _selectedCoinInfo.value = coinInfo
    }

    fun getHistoricalRequest(fSymbol: String, timeLimit: String) {
        priceHistoryList.clear()
        viewModelScope.launch {
            var jsonContainer = CoinPriceHistoryContainerDto()
            when (timeLimit) {
                ONE_HOUR -> jsonContainer =
                    apiService.getMinutelyPriceHistory(fSym = fSymbol, limit = timeLimit)
                TWENTY_FOUR_HOURS -> jsonContainer =
                    apiService.getHourlyPriceHistory(fSym = fSymbol, limit = timeLimit)
                SEVEN_DAYS -> jsonContainer =
                    apiService.getDaylyPriceHistory(fSym = fSymbol, limit = timeLimit)
                ONE_MONTH -> jsonContainer =
                    apiService.getDaylyPriceHistory(fSym = fSymbol, limit = timeLimit)
                THREE_MONTHS -> jsonContainer =
                    apiService.getDaylyPriceHistory(fSym = fSymbol, limit = timeLimit)
                ONE_YEAR -> jsonContainer =
                    apiService.getDaylyPriceHistory(fSym = fSymbol, limit = timeLimit)
                ALL_DATA -> jsonContainer = apiService.getAllPriceHistory(fSym = fSymbol)
            }
            val listContainer = jsonContainer.priceList
            listContainer?.list?.let {
                _lineDataSet.value?.clear()
                val list = mutableListOf<Entry>()
                var count = 1f
                it.forEach { historyPriceDto ->
                    list.add(Entry(count++, historyPriceDto.price))
                }
                _lineDataSet.value = LineDataSet(list, fSymbol)
            }
        }

    }

    override fun onCleared() {
        _isViewModelOnCleared.value = true
//        startGettingTopCoinListInBackgroundUseCase()
        super.onCleared()
    }

    companion object {
        const val TWENTY_FOUR_HOURS = "24"
        const val ONE_HOUR = "60"
        const val SEVEN_DAYS = "7"
        const val ONE_MONTH = "30"
        const val THREE_MONTHS = "90"
        const val ONE_YEAR = "365"
        const val ALL_DATA = "allData"
    }
}