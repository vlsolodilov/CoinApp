package com.solodilov.coinapp.presentation.coinlist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.solodilov.coinapp.App
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

        binding.chipUsd.text = Currency.USD.name
        binding.chipEur.text = Currency.EUR.name

        binding.groupChipsCurrency.setOnCheckedStateChangeListener { group, checkedIds ->
            checkedIds.firstOrNull()?.let { id ->
                Log.d("TAG", "initViews: $id")
                val currency = group.findViewById<Chip>(id).text
                viewModel.setCurrency(currency.toString())
                viewModel.loadCoinList()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner, ::processState)
        viewModel.currency.observe(viewLifecycleOwner) { currency ->
            checkChipCurrency(currency)
        }
    }

    private fun checkChipCurrency(currency: Currency) {
        when (currency) {
            Currency.USD -> binding.chipUsd.isChecked = true
            Currency.EUR -> binding.chipEur.isChecked = true
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
            isError = false,
        )
    }

    private fun renderContentState(contentState: CoinListScreenState.Content) {
        coinAdapter?.submitList(contentState.content)
        toggleState(
            isLoading = false,
            isContent = true,
            isError = false,
        )
    }

    private fun renderErrorState() {
        toggleState(
            isLoading = false,
            isContent = false,
            isError = true,
        )
    }

    private fun toggleState(isLoading: Boolean, isContent: Boolean, isError: Boolean) {
        binding.progressBar.isVisible = isLoading
        binding.coinList.isVisible = isContent
        binding.errorLayout.root.isVisible = isError
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