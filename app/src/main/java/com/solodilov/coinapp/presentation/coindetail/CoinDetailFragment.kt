package com.solodilov.coinapp.presentation.coindetail

import android.content.Context
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.solodilov.coinapp.App
import com.solodilov.coinapp.databinding.FragmentCoinDetailBinding
import javax.inject.Inject

class CoinDetailFragment  : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: CoinDetailViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[CoinDetailViewModel::class.java]
    }

    private var _binding: FragmentCoinDetailBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentCoinDetailBinding is null")

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCoinDetailBinding.inflate(layoutInflater, container, false)

        initViews()
        observeViewModel()

        return binding.root
    }

    private fun initViews() {
        arguments?.let {
            val args = CoinDetailFragmentArgs.fromBundle(it)
            val coinId = args.coinId
            binding.coinDetailToolbar.title = args.coinName
            viewModel.loadCoinDetail(coinId)
            binding.errorLayout.tryButton.setOnClickListener { viewModel.loadCoinDetail(coinId) }
        }
        binding.coinDetailToolbar.setNavigationOnClickListener { findNavController().popBackStack() }
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner, ::processState)
    }

    private fun processState(state: CoinDetailScreenState) {
        when (state) {
            is CoinDetailScreenState.Loading -> renderLoadingState()
            is CoinDetailScreenState.Content -> renderContentState(state)
            is CoinDetailScreenState.Error   -> renderErrorState()
        }
    }

    private fun renderLoadingState() {
        toggleState(
            isLoading = true,
            isContent = false,
            isError = false,
        )
    }

    private fun renderContentState(contentState: CoinDetailScreenState.Content) {
        binding.apply {
            coinDetailToolbar.title = contentState.content.name
            description.text = Html.fromHtml(
                contentState.content.description,
                Html.FROM_HTML_MODE_LEGACY
            )
            category.text = contentState.content.category
        }
        Glide
            .with(this)
            .load(contentState.content.image)
            .into(binding.coinLogo)
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
        binding.coinDetail.isVisible = isContent
        binding.errorLayout.root.isVisible = isError
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}