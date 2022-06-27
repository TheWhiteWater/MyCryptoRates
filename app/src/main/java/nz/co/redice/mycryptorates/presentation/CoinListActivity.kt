package nz.co.redice.mycryptorates.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import nz.co.redice.mycryptorates.R
import nz.co.redice.mycryptorates.databinding.ActivityCoinListBinding
import nz.co.redice.mycryptorates.domain.CoinInfo
import nz.co.redice.mycryptorates.presentation.adapters.CoinInfoAdapter

class CoinListActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel
    private val binding by lazy { ActivityCoinListBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        val adapter = CoinInfoAdapter(this.baseContext)
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClicked(coinInfo: CoinInfo) {
                if (isOnePaneMode())
                    launchDetailActivity(coinInfo.fromSymbol)
                else
                    launchDetailFragment(coinInfo.fromSymbol)
            }
        }
        binding.recyclerViewCoinPriceList.adapter = adapter
        binding.recyclerViewCoinPriceList.itemAnimator = null
        viewModel.coinInfoList().observe(this) {
            adapter.submitList(it)
        }
    }

    private fun isOnePaneMode() = binding.fragmentContainer == null

    private fun launchDetailActivity(fromSymbol: String) {
        val intent = CoinDetailActivity.newIntent(this@CoinListActivity, fromSymbol)
        startActivity(intent)
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