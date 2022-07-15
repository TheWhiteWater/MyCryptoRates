package nz.co.redice.mycryptorates.di

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import nz.co.redice.mycryptorates.di.modules.DataModule
import nz.co.redice.mycryptorates.di.modules.ViewModelModule
import nz.co.redice.mycryptorates.presentation.CoinDetailFragment
import nz.co.redice.mycryptorates.presentation.HostActivity

@ApplicationScope
@Component(modules = [
    DataModule::class,
    ViewModelModule::class
])
interface ApplicationComponent {

    fun inject(application: CryptoApplication)
    fun inject(activity: HostActivity)
    fun inject(fragment: CoinDetailFragment)



    @Component.Factory
    interface ApplicationComponentFactory {
        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }
}