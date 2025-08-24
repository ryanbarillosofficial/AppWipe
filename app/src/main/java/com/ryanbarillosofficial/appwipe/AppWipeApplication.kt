package com.ryanbarillosofficial.appwipe

import android.app.Application
import com.ryanbarillosofficial.appwipe.data.AppContainer
import com.ryanbarillosofficial.appwipe.data.AppDataContainer
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppWipeApplication: Application() {
    /**
     * appContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)

    }
}