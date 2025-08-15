package com.project.mypersonalassistant.roomDB

import android.content.Context
import kotlin.jvm.Volatile
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.project.mypersonalassistant.roomDB.dao.UserDao
import com.project.mypersonalassistant.roomDB.entity.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "dash_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
