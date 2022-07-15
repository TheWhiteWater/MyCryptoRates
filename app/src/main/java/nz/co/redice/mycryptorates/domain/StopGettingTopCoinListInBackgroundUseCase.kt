package nz.co.redice.mycryptorates.domain

import javax.inject.Inject

class StopGettingTopCoinListInBackgroundUseCase @Inject constructor(private val repository: CoinRepository) {
     operator fun invoke() = repository.stopWorkerLoadingData()
}