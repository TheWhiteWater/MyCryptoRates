package nz.co.redice.mycryptorates.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import nz.co.redice.mycryptorates.databinding.FragmentCoinListBinding
import nz.co.redice.mycryptorates.domain.CoinInfo
import nz.co.redice.mycryptorates.presentation.adapters.CoinInfoAdapter

class CoinListFragment : Fragment() {

    private var _binding: FragmentCoinListBinding? = null
    private val binding: FragmentCoinListBinding
        get() = _binding ?: throw RuntimeException("FragmentCoinListBinding is null")

    val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CoinInfoAdapter(requireContext())
        binding.recyclerViewCoinList.adapter = adapter
        binding.recyclerViewCoinList.itemAnimator = null
        viewModel.coinInfoList().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClicked(coinInfo: CoinInfo) {
                viewModel.selectedCoinInfo(coinInfo)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}