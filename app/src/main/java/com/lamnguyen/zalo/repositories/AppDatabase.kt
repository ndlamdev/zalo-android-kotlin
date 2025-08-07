package com.lamnguyen.zalo.repositories

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lamnguyen.zalo.entities.Cookie

@Database(entities = [Cookie::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cookieRepository(): CookieRepository

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "zalo_cookie" // Database name
                )
                    .fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}