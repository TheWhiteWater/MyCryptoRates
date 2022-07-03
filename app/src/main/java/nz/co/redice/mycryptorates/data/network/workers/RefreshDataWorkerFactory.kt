package nz.co.redice.mycryptorates.data.network.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import javax.inject.Inject
import javax.inject.Provider

class RefreshDataWorkerFactory @Inject constructor(
    private val workerFactory:
    @JvmSuppressWildcards Map<Class<out ListenableWorker>, Provider<ChildWorkerFactory>>
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context, workerClassName: String, workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            RefreshDataWorker::class.qualifiedName -> {
                val childWorkerFactory = workerFactory[RefreshDataWorker::class.java]?.get()
                childWorkerFactory?.create(appContext, workerParameters)
            }
            else -> null
        }
    }
}