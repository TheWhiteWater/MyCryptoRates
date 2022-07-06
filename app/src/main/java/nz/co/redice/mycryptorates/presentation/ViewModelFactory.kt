package nz.co.redice.mycryptorates.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import nz.co.redice.mycryptorates.di.ActivityScope
import javax.inject.Inject


@ActivityScope
class ViewModelFactory @Inject constructor(
    private val viewModels: @JvmSuppressWildcards Map<Class<out ViewModel>, ViewModel>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModels[modelClass] as T
    }


}