package com.ryanbarillosofficial.appwipe.data

import android.content.Context
import com.ryanbarillosofficial.appwipe.data.local.database.AppInfoDatabase
import com.ryanbarillosofficial.appwipe.data.local.database.AppInfoRepositoryInterface
import com.ryanbarillosofficial.appwipe.data.local.database.AppInfoRepository

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val appInfoRepository: AppInfoRepositoryInterface
}

/**
 * [AppContainer] implementation that provides instance of [AppInfoRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [AppInfoRepositoryInterface]
     */
    override val appInfoRepository: AppInfoRepositoryInterface by lazy {
        AppInfoRepository(AppInfoDatabase.getDatabase(context).appInfoDao())
    }
}