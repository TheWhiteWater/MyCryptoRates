package nz.co.redice.mycryptorates.di

import dagger.Subcomponent
import nz.co.redice.mycryptorates.presentation.CoinDetailFragment
import nz.co.redice.mycryptorates.presentation.CoinListFragment
import nz.co.redice.mycryptorates.presentation.HostActivity

@ActivityScope
@Subcomponent (modules = [ViewModelModule::class])
interface ActivityComponent {

    fun inject(activity: HostActivity)
    fun inject(fragment: CoinDetailFragment)
    fun inject(fragment: CoinListFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ActivityComponent
    }
}