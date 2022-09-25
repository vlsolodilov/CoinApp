package com.solodilov.coinapp.di

import android.app.Application
import com.solodilov.coinapp.presentation.MainActivity
import com.solodilov.coinapp.presentation.coindetail.CoinDetailFragment
import com.solodilov.coinapp.presentation.coinlist.CoinListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
	DataModule::class,
	NetworkModule::class,
	ViewModelModule::class,
])
interface ApplicationComponent {

	fun inject(activity: MainActivity)
	fun inject(fragment: CoinListFragment)
	fun inject(fragment: CoinDetailFragment)

	@Component.Factory
	interface Factory {
		fun create(
			@BindsInstance application: Application,
		): ApplicationComponent
	}
}