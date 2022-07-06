package nz.co.redice.mycryptorates.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import nz.co.redice.mycryptorates.R
import nz.co.redice.mycryptorates.databinding.ActivityHostBinding
import nz.co.redice.mycryptorates.di.CryptoApplication
import javax.inject.Inject

class HostActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityHostBinding.inflate(layoutInflater)
    }

     private val viewModel: CoinViewModel by lazy {
         ViewModelProvider(this, viewModelFactory)[CoinViewModel::class.java]
     }


    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as CryptoApplication).component
            .getActivityComponentFactory()
            .create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        setContentView(binding.root)
        viewModel.selectedCoinInfo.observe(this) {
            if (isOnePaneMode())
                    launchDetailActivity(it.fromSymbol)
                else
                    launchDetailFragment(it.fromSymbol)
        }
    }

    private fun isOnePaneMode() = binding.fragmentContainer == null

    private fun launchDetailActivity(fromSymbol: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_list_container, CoinDetailFragment.newInstance(fromSymbol))
            .addToBackStack(null)
            .commit()
    }

    private fun launchDetailFragment(fromSymbol: String) {
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, CoinDetailFragment.newInstance(fromSymbol))
            .addToBackStack(null)
            .commit()
    }

}