package nz.co.redice.mycryptorates.presentation

import android.app.Application
import nz.co.redice.mycryptorates.di.DaggerApplicationComponent

class CoinApp : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}