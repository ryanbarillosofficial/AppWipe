package com.ryanbarillosofficial.appwipe

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppWipeApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}