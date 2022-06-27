package nz.co.redice.mycryptorates.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_coin_price_list.*
import nz.co.redice.mycryptorates.R
import nz.co.redice.mycryptorates.presentation.adapters.CoinInfoAdapter
import nz.co.redice.mycryptorates.data.network.model.CoinInfoDto
import nz.co.redice.mycryptorates.domain.CoinInfo

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_price_list)
        val adapter = CoinInfoAdapter(this.baseContext)
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClicked(coinPriceInfo: CoinInfo) {
                val intent = CoinDetailActivity.newIntent(
                    this@CoinPriceListActivity,
                    coinPriceInfo.fromSymbol
                )
                startActivity(intent)
            }
        }

        recycler_view_coin_price_list.adapter = adapter
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]

        viewModel.coinInfoList().observe(this) {
            adapter.coinInfoList = it
        }
    }


}