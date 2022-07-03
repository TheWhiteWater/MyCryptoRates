package nz.co.redice.mycryptorates.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import nz.co.redice.mycryptorates.data.network.workers.ChildWorkerFactory
import nz.co.redice.mycryptorates.data.network.workers.RefreshDataWorker

@Module
interface WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey (RefreshDataWorker::class)
    fun bindRefreshDataWorkerFactory(worker: RefreshDataWorker.Factory): ChildWorkerFactory
}