package com.solodilov.coinapp

import android.app.Application
import com.solodilov.coinapp.di.DaggerApplicationComponent

class App : Application() {

    val appComponent = DaggerApplicationComponent.factory().create(this)
}