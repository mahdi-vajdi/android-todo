package com.mahdivajdi.simpletodo.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mahdivajdi.simpletodo.data.local.dao.TaskDao
import com.mahdivajdi.simpletodo.data.local.entity.CategoryEntity
import com.mahdivajdi.simpletodo.data.local.entity.TaskEntity

@Database(entities = [TaskEntity::class, CategoryEntity::class],
    version = 1,
    exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
    abstract fun categoryDao(): TaskDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database")
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}