package nz.co.redice.mycryptorates

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_coin_detail.*
import kotlinx.android.synthetic.main.item_coin_info.*

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
        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL)
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]

        fromSymbol?.let {
            viewModel.getDetailInfo(it).observe(this, {
                tvPriceValue.text = it.price.toString()
                tvMinPriceValue.text = it.lowDay.toString()
                tvMaxPriceValue.text = it.highDay.toString()
                tvLastDealValue.text = it.lastMarket
                tvUpdatedAtValue.text = it.getFormattedTime()
                tvFromSymbol.text = it.fromSymbol
                tvToCurrency.text = it.toSymbol
                Picasso.get().load(it.getFullImageUrl()).into(ivLogoCoinDetail)
            })
        }
    }




    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"

        fun newIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            return intent
        }
    }
}