package nz.co.redice.mycryptorates.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_coin_detail.*
import nz.co.redice.mycryptorates.R

class CoinDetailActivity : AppCompatActivity() {
    private lateinit var viewModel: CoinViewModel
    private val TAG = this::class.java.simpleName.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detail)
        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL) ?: EMPTY_STRING
        viewModel.getDetailInfo(fromSymbol).observe(this) {
            tvPriceValue.text = it.price.toString()
            tvMinPriceValue.text = it.lowDay.toString()
            tvMaxPriceValue.text = it.highDay.toString()
            tvLastDealValue.text = it.lastMarket
            tvUpdatedAtValue.text = it.lastUpdate
            tvFromSymbol.text = it.fromSymbol
            tvToCurrency.text = it.toSymbol
            Picasso.get().load(it.imageUrl).into(ivLogoCoinDetail)
        }
    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"
        private const val EMPTY_STRING = ""

        fun newIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            return intent
        }
    }
}