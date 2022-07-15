package nz.co.redice.mycryptorates.presentation

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import nz.co.redice.mycryptorates.R
import nz.co.redice.mycryptorates.data.network.broadcast.MyReceiver
import nz.co.redice.mycryptorates.databinding.ActivityHostBinding
import nz.co.redice.mycryptorates.di.CryptoApplication
import javax.inject.Inject

class HostActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityHostBinding.inflate(layoutInflater)
    }

     private val viewModel: CoinViewModel by viewModels {
         viewModelFactory
     }
    @Inject
    lateinit var receiver: MyReceiver

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as CryptoApplication).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("TAAG", "onCreate: ")
        super.onCreate(savedInstanceState)
        component.inject(this)
        setContentView(binding.root)

        val intentFilter = IntentFilter(MyReceiver.ACTION_VIEW_MODEL_CLEARED)
        registerReceiver(receiver, intentFilter)
        viewModel.selectedCoinInfo.observe(this) {
            if (isOnePaneMode())
                    launchDetailActivity(it.fromSymbol)
                else
                    launchDetailFragment(it.fromSymbol)
        }

        viewModel.isViewModelOnCleared.observe(this) {
            Log.d("TAAG", "isViewModelOnCleared: called")
            sendBroadcast(Intent(MyReceiver.ACTION_VIEW_MODEL_CLEARED))
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


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}