package nz.co.redice.mycryptorates.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import nz.co.redice.mycryptorates.R
import nz.co.redice.mycryptorates.databinding.ActivityHostBinding

@AndroidEntryPoint
class HostActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityHostBinding.inflate(layoutInflater)
    }

    private val viewModel: SharedViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("TAAG", "onCreate: ")
        super.onCreate(savedInstanceState)
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