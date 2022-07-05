package nz.co.redice.mycryptorates.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import nz.co.redice.mycryptorates.databinding.FragmentCoinListBinding
import nz.co.redice.mycryptorates.di.CryptoApplication
import nz.co.redice.mycryptorates.domain.CoinInfo
import nz.co.redice.mycryptorates.presentation.adapters.CoinInfoAdapter
import javax.inject.Inject

class CoinListFragment : Fragment() {

    private var _binding: FragmentCoinListBinding? = null
    private val binding: FragmentCoinListBinding
        get() = _binding ?: throw RuntimeException("FragmentCoinListBinding is null")

    private val component by lazy {
        (requireActivity().application as CryptoApplication).component

    }

    private lateinit var viewModel: CoinViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

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
        viewModel = ViewModelProvider(this, viewModelFactory)[CoinViewModel::class.java]

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