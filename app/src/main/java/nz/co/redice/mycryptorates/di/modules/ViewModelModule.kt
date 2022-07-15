package nz.co.redice.mycryptorates.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import nz.co.redice.mycryptorates.di.ApplicationScope
import nz.co.redice.mycryptorates.di.keys.ViewModelKey
import nz.co.redice.mycryptorates.presentation.CoinViewModel

@Module
interface ViewModelModule {

    @ApplicationScope
    @Binds
    @IntoMap
    @ViewModelKey(CoinViewModel::class)
    fun bindCoinViewModel(viewModel: CoinViewModel): ViewModel
}