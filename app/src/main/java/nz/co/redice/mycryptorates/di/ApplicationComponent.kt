package nz.co.redice.mycryptorates.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, WorkerModule::class])
interface ApplicationComponent {

    fun getActivityComponentFactory(): ActivityComponent.Factory
    fun inject(application: CryptoApplication)


    @Component.Factory
    interface ApplicationComponentFactory {
        fun create(
            @BindsInstance context: Context,
        ): ApplicationComponent
    }
}