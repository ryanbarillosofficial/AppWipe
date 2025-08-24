package com.ryanbarillosofficial.appwipe.data.local.database

import kotlinx.coroutines.flow.Flow

interface AppInfoRepositoryInterface {
    suspend fun insert(appInfoEntity: AppInfoEntity)
    suspend fun delete(appInfoEntity: AppInfoEntity): Int
    fun getAll(): Flow<List<AppInfoEntity>>
    suspend fun getCount(): Int
    fun getPackageNames(): Flow<List<String>>


}