package com.lamnguyen.zalo.repositories

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lamnguyen.zalo.entities.Cookie
import com.lamnguyen.zalo.entities.Message
import com.lamnguyen.zalo.utils.converters.DataTypeConverter

@Database(entities = [Cookie::class, Message::class], version = 1)
@TypeConverters(DataTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cookieRepository(): CookieRepository
    abstract fun messageRepository(): MessageRepository

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