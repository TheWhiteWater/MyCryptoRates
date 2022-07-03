package nz.co.redice.mycryptorates.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import nz.co.redice.mycryptorates.presentation.CoinApp
import nz.co.redice.mycryptorates.presentation.CoinDetailFragment
import nz.co.redice.mycryptorates.presentation.CoinListActivity

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class, WorkerModule::class])
interface ApplicationComponent {

    fun inject(activity: CoinListActivity)
    fun inject(fragment: CoinDetailFragment)
    fun inject(application: CoinApp)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application,
        ): ApplicationComponent
    }
}