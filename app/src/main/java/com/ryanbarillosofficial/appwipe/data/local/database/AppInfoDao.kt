package com.ryanbarillosofficial.appwipe.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.ryanbarillosofficial.appwipe.ui.page.select_apps.SelectAppsViewModel
import com.ryanbarillosofficial.appwipe.ui.page.view_list.ViewListViewModel

@Dao
interface AppInfoDao {
    @Insert
    suspend fun insert(appInfoEntity: AppInfoEntity)
    @Delete
    suspend fun delete(appInfoEntity: AppInfoEntity): Int
    @Query("SELECT * FROM $APP_INFO_TABLE_NAME ORDER BY $APP_INFO_LABEL_COLUMN_NAME ASC")
    fun getAll(): Flow<List<AppInfoEntity>>
    @Query("SELECT COUNT(*) FROM $APP_INFO_TABLE_NAME")
    suspend fun getCount(): Int
    @Query("DELETE FROM $APP_INFO_TABLE_NAME")
    suspend fun deleteAll(): Int
    /**
     * Get a list of package names from the database
     * - This is used as a filter by [SelectAppsViewModel] when gathering the list of installed apps
     * - As well as [ViewListViewModel] when displaying & modifying the list of installed apps
     */
    @Query("SELECT $APP_INFO_PACKAGE_NAME_COLUMN_NAME FROM $APP_INFO_TABLE_NAME")
    fun getPackageNames(): Flow<List<String>>
}