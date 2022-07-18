package nz.co.redice.mycryptorates.domain

import javax.inject.Inject

class GetTopCoinListUseCase @Inject constructor(private val repository: CoinRepository) {
     suspend operator fun invoke() = repository.startWorkerLoadingData()
}