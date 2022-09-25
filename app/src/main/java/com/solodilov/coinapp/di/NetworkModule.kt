package com.solodilov.coinapp.di

import com.solodilov.coinapp.data.network.CoinGeckoApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

	private companion object {

		const val BASE_URL = "https://api.coingecko.com/api/"
	}

	@Provides
	fun provideGsonConverterFactory(): GsonConverterFactory =
		GsonConverterFactory.create()

	@Provides
	@Singleton
	fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory,
	): Retrofit =
		Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addConverterFactory(gsonConverterFactory)
			.build()

	@Provides
	@Singleton
	fun provideCoinGeckoApi(
		retrofit: Retrofit,
	): CoinGeckoApi =
		retrofit.create(CoinGeckoApi::class.java)
}