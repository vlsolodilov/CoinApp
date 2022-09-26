package com.solodilov.coinapp.presentation.coinlist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.solodilov.coinapp.App
import com.solodilov.coinapp.R
import com.solodilov.coinapp.databinding.FragmentCoinListBinding
import com.solodilov.coinapp.domain.entity.Currency
import javax.inject.Inject

class CoinListFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: CoinListViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[CoinListViewModel::class.java]
    }

    private var _binding: FragmentCoinListBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentCoinListBinding is null")

    private var coinAdapter: CoinAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCoinListBinding.inflate(layoutInflater, container, false)

        initViews()
        observeViewModel()

        return binding.root
    }

    private fun initViews() {
        coinAdapter = CoinAdapter { coin ->
            startCoinDetailFragment(
                coinId = coin.id,
                coinName = coin.name
            )
        }
        binding.coinList.adapter = coinAdapter

        binding.errorLayout.tryButton.setOnClickListener { viewModel.loadCoinList() }

        Currency.values().iterator().forEach { addChip(it) }

        binding.swipeContainer.setOnRefreshListener {
            viewModel.loadCoinList()
            binding.swipeContainer.isRefreshing = false
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner, ::processState)
        viewModel.currency.observe(viewLifecycleOwner) { currency ->
            checkChipCurrency(currency)
        }
    }

    private fun checkChipCurrency(currency: Currency) {
        binding.groupChipsCurrency.children.iterator().forEach {
            val chip = it as Chip
            chip.isChecked = chip.text == currency.name
        }
    }


    private fun processState(state: CoinListScreenState) {
        when (state) {
            is CoinListScreenState.Loading -> renderLoadingState()
            is CoinListScreenState.Content -> renderContentState(state)
            is CoinListScreenState.Error -> renderErrorState()
        }
    }

    private fun renderLoadingState() {
        toggleState(
            isLoading = true,
            isContent = false,
        )
    }

    private fun renderContentState(contentState: CoinListScreenState.Content) {
        coinAdapter?.submitList(contentState.content)
        toggleState(
            isLoading = false,
            isContent = true,
        )
    }

    private fun renderErrorState() {
        toggleState(
            isLoading = false,
            isContent = false,
        )
        coinAdapter?.let { coinAdapter ->
            if (coinAdapter.currentList.isNotEmpty()) {
                binding.coinList.isVisible = true
                showErrorSnackBar()
            } else {
                binding.errorLayout.root.isVisible = true
            }
        }
    }

    private fun addChip(currency: Currency) {
        val chip = layoutInflater.inflate(
            R.layout.single_chip_layout,
            binding.groupChipsCurrency,
            false
        ) as Chip
        chip.apply {
            text = currency.name
            setOnClickListener {
                viewModel.setCurrency(currency)
                viewModel.loadCoinList()
            }
            binding.groupChipsCurrency.addView(chip)
        }
    }

    private fun showErrorSnackBar() {
        Snackbar.make(binding.root, R.string.error_occurred, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(requireActivity(), R.color.red))
            .show()
    }

    private fun toggleState(isLoading: Boolean, isContent: Boolean) {
        binding.progressBar.isVisible = isLoading
        binding.coinList.isVisible = isContent
        binding.errorLayout.root.isVisible = false
    }

    private fun startCoinDetailFragment(coinId: String, coinName: String) {
        val action = CoinListFragmentDirections
            .actionCoinListFragmentToCoinDetailFragment(coinId, coinName)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        coinAdapter = null
        _binding = null
        super.onDestroyView()
    }
}