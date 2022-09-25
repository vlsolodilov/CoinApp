package com.solodilov.coinapp.di

import com.solodilov.coinapp.data.datasource.CoinDataSource
import com.solodilov.coinapp.data.datasource.CoinDataSourceImpl
import com.solodilov.coinapp.data.repository.CoinRepositoryImpl
import com.solodilov.coinapp.domain.repository.CoinRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface DataModule {

	@Singleton
	@Binds
	fun bindCoinDataSource(impl: CoinDataSourceImpl): CoinDataSource

	@Singleton
	@Binds
	fun bindCoinRepository(impl: CoinRepositoryImpl): CoinRepository

}