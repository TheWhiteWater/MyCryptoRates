package nz.co.redice.mycryptorates.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.github.mikephil.charting.data.LineData
import dagger.hilt.android.AndroidEntryPoint
import nz.co.redice.mycryptorates.*
import nz.co.redice.mycryptorates.databinding.FragmentCoinDetailBinding
import javax.inject.Inject

@AndroidEntryPoint
class CoinDetailFragment : Fragment() {

    private var _binding: FragmentCoinDetailBinding? = null
    private val binding: FragmentCoinDetailBinding
        get() = _binding ?: throw RuntimeException("FragmentCoinDetailBinding is null")

    private val viewModel: SharedViewModel by activityViewModels()

    @Inject
    lateinit var sparkLineStyle: SparkLineStyle


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fromSymbol = getSymbol()
        viewModel.getDetailInfo(fromSymbol).observe(viewLifecycleOwner) {
            with(binding) {
                tvPrice.text = it.price.toString()
                textViewLowPrice.text = it.lowDay.toString()
                textViewHighPrice.text = it.highDay.toString()
                tvLastMarket.text = it.lastMarket
                tvLastUpdate.text = it.lastUpdate
                tvFromSymbol.text = it.fromSymbol
//                tvToSymbol.text = it.toSymbol
//                Picasso.get().load(it.imageUrl).into(ivLogoCoin)
            }
        }
        viewModel.lineDataSet.observe(viewLifecycleOwner) {
            if (it.label == getSymbol()) {
                sparkLineStyle.styleDataSet(requireContext(), it)
                with(binding.chart) {
                    sparkLineStyle.styleLineChart(this)
                    data = LineData(it)
                    invalidate()
                }
            } else {
                binding.oneDay.isChecked = true
            }
        }
        binding.toggleButtonGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.oneDay -> viewModel.makeHistoryRequest(getSymbol(), TWENTY_FOUR_HOURS)
                    R.id.oneHour -> viewModel.makeHistoryRequest(getSymbol(), ONE_HOUR)
                    R.id.oneWeek -> viewModel.makeHistoryRequest(getSymbol(), SEVEN_DAYS)
                    R.id.oneMonth -> viewModel.makeHistoryRequest(getSymbol(), ONE_MONTH)
                    R.id.threeMonth -> viewModel.makeHistoryRequest(getSymbol(), THREE_MONTHS)
                    R.id.oneYear -> viewModel.makeHistoryRequest(getSymbol(), ONE_YEAR)
                    R.id.allPeriod -> viewModel.makeHistoryRequest(getSymbol(), ALL_DATA)
                }
            }
        }
    }

    private fun getSymbol(): String {
        return requireArguments().getString(EXTRA_FROM_SYMBOL) ?: EMPTY_SYMBOL
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"
        private const val EMPTY_SYMBOL = ""

        fun newInstance(fromSymbol: String): Fragment {
            return CoinDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_FROM_SYMBOL, fromSymbol)
                }
            }
        }
    }
}