package com.solodilov.coinapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.solodilov.coinapp.presentation.ViewModelFactory
import com.solodilov.coinapp.presentation.coindetail.CoinDetailViewModel
import com.solodilov.coinapp.presentation.coinlist.CoinListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CoinListViewModel::class)
    fun bindCoinListViewModel(viewModel: CoinListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CoinDetailViewModel::class)
    fun bindCoinDetailViewModel(viewModel: CoinDetailViewModel): ViewModel
}