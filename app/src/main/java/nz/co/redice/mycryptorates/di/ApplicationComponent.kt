package nz.co.redice.mycryptorates.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import nz.co.redice.mycryptorates.presentation.HostActivity

@ApplicationScope
@Component(modules = [DataModule::class, WorkerModule::class])
interface ApplicationComponent {

    fun inject(application: CryptoApplication)
    fun inject(activity: HostActivity)

    @Component.Factory
    interface ApplicationComponentFactory {
        fun create(
            @BindsInstance context: Context,
        ): ApplicationComponent
    }
}