package nz.co.redice.mycryptorates.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import nz.co.redice.mycryptorates.presentation.CoinViewModel

@Module
interface ViewModelModule {

    @ActivityScope
    @Binds
    @IntoMap
    @ViewModelKey(CoinViewModel::class)
    fun bindCoinViewModel(viewModel: CoinViewModel): ViewModel
}