package ru.aeyu.catapitestapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TheCatApiApp : Application() {
//    private val cicerone = Cicerone.create()
//    val router get() = cicerone.router
//    val navigatorHolder get() = cicerone.getNavigatorHolder()

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        internal lateinit var INSTANCE: TheCatApiApp
            private set
    }
}