package nz.co.redice.mycryptorates.domain

import javax.inject.Inject

class GetHistoryDataUseCase @Inject constructor(private val repository: CoinRepository) {
     suspend operator fun invoke(fSymbol: String, timeLimit: String) = repository.getHistoricalRequest(fSymbol, timeLimit)
}