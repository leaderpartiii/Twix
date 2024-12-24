package com.example.twix.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [PersonEntity::class], version = 1)
@TypeConverters(PostConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun commandsDao(): CommandsDao

    companion object {
        private const val DATABASE_NAME = "PersonsDB"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}