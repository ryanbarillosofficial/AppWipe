package com.ryanbarillosofficial.appwipe.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AppInfoEntity::class], version = 1)
abstract class AppInfoDatabase: RoomDatabase() {
    abstract fun appInfoDao(): AppInfoDao
    companion object {
        @Volatile
        private var Instance: AppInfoDatabase? = null

        fun getDatabase(context: Context): AppInfoDatabase {
            /**
             * Objective
             * - Provide a migration schema
             * - https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fandroid-basics-compose-unit-6-pathway-2%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fbasic-android-kotlin-compose-persisting-data-room#6
             * - https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
             */
            return Instance ?: synchronized(this) {
                Room
                    .databaseBuilder(
                        context = context,
                        klass = AppInfoDatabase::class.java,
                        name = APP_INFO_DATABASE_NAME
                    )
                    .fallbackToDestructiveMigration(false)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}