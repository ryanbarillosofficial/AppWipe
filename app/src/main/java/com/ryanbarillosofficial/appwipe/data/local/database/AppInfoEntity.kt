package com.ryanbarillosofficial.appwipe.data.local.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = APP_INFO_TABLE_NAME)
data class AppInfoEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = APP_INFO_PACKAGE_NAME_COLUMN_NAME)
    val packageName: String,

    @ColumnInfo(name = APP_INFO_LABEL_COLUMN_NAME)
    val label: String,

    @ColumnInfo(name = APP_INFO_IS_SYSTEM_APP_COLUMN_NAME, defaultValue = "FALSE")
    val isSystemApp: Boolean = false,
)